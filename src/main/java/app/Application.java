package app;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import conf.Config;
import domain.Store;
import persistence.sql.HibernateUtils;
import persistence.sql.StoreSqlDAO;

/**
 * Encapsulate application run logic
 */
public class Application {

  final static Logger log = LoggerFactory.getLogger(Application.class);

  private final Options options = new Options();
  private final CommandLineParser parser = new DefaultParser();
  private CommandLine cmd;

  public Application() {
    setCmdOptions();

    Properties props = System.getProperties();
    props.setProperty("com.sun.net.ssl.checkRevocation", "false");
  }

  private void setCmdOptions() {
    options.addOption("export", false, "export");
    options.addOption("parseall", false, "parse all");
    options.addOption("parsestore", true, "parse store");
    options.addOption("emir", false, "parse emir");
    options.addOption("aliases", false, "parse emir");
    options.addOption("csv2sql", false, "parse emir");
    options.addOption("prices", false, "parse emir");
    options.addOption("sql2csv", false, "parse emir");
    options.addOption("email", false, "parse emir");
    options.addOption("total", false, "parse emir");
  }

  private Config getConfig() throws IOException {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("spring.xml");
    return (Config) context.getBean("config");
  }

  public void go(String[] args) throws Exception {
    cmd = parser.parse(options, args);

    try {

      Set<StoreProcessor> processors = getProcessors(getConfig());

      if (cmd.hasOption("parseall")) {
        processAll(processors);
      }

      if (cmd.hasOption("parsestore")) {
        processStore(processors, cmd.getOptionValue("s"));
      }

      if (cmd.hasOption("emir")) {
        EmirProcessor ep = new EmirProcessor();
        ep.process();
      }

      if (cmd.hasOption("aliases")) {
        new AliasProcessor().process();
      }

      if (cmd.hasOption("export")) {
        new ExportProcessor().process();
      }

      if (cmd.hasOption("csv2sql")) {
        new ExportAliacesProcessor().process();
      }

      if (cmd.hasOption("sql2csv")) {
        new ExportAliacesProcessor().process2();
      }

      if (cmd.hasOption("prices")) {
        new PricesProcessor().process();
      }

      if (cmd.hasOption("email")) {
        new EmailProcessor().process();
      }

      if (cmd.hasOption("total")) {
//        processAll(processors);
//        log.info("Data gathered");
//        new ExportProcessor().process();
//        new AliasProcessor().process();
        new PricesProcessor().process();
        log.info("Prices in file");
        new EmailProcessor().process();
        log.info("Email sent");
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      HibernateUtils.getSessionFactory().close();
    }
  }

  private void processAll(Set<StoreProcessor> processors) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(processors.size());
    ExecutorService executor = Executors.newFixedThreadPool(processors.size());

    for (StoreProcessor processor : processors) {
      executor.submit(new StoreRunner(processor, latch));
    }

    latch.await();
    log.info("Parsing completed.");
  }


  private void processStore(Set<StoreProcessor> processors, String store) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    ExecutorService executor = Executors.newFixedThreadPool(processors.size());
    for (StoreProcessor processor : processors) {
      if (processor.getName().toLowerCase().equals(store)) {
        executor.submit(new StoreRunner(processor, latch));
        ;
      }
    }
    latch.await();
    log.info("Parsing completed.");
  }


  private Set<StoreProcessor> getProcessors(Config config) {
    Map<String, Store> stores = new StoreSqlDAO().getStoresAsMap();
    Set<StoreProcessor> processors = new HashSet<>();

    for (Map.Entry<String, StoreProcessor> kv : config.getStoreConfigs().entrySet()) {
      if (stores.containsKey(kv.getKey())) {
        kv.getValue().setStore(stores.get(kv.getKey()));
        processors.add(kv.getValue());
      }
    }

    return processors;
  }
}
