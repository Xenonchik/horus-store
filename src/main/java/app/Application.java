package app;

import java.io.File;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import conf.Config;
import conf.StoreConf;
import domain.Store;
import persistence.sql.HibernateUtils;
import persistence.sql.StoreSqlDAO;

/**
 * Encapsulate application run logic
 */
public class Application {

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
  }

  private Config getConfig() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper.readValue(new File("src/main/resources/config.json"), Config.class);
//    return mapper.readValue(new File("/tmp/config.json"), Config.class);
//    return mapper.readValue(Application.class.getResourceAsStream("config.json"), Config.class);
  }

  public void go(String[] args) throws Exception {
    cmd = parser.parse(options, args);

    Set<StoreProcessor> processors = getProcessors(getConfig());

    if (cmd.hasOption("parseall")) {
      processAll(processors);
    }

    if (cmd.hasOption("parsestore")) {
      processStore(processors, cmd.getOptionValue("s"));
    }

    if(cmd.hasOption("emir")) {
      EmirProcessor ep = new EmirProcessor();
      ep.process();
    }

    if(cmd.hasOption("aliases")) {
      AliasProcessor ap = new AliasProcessor();
      ap.process();
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
  }

  private void processAll(Set<StoreProcessor> processors) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(processors.size());
    ExecutorService executor = Executors.newFixedThreadPool(processors.size());

    for (StoreProcessor processor : processors) {
      executor.submit(new StoreRunner(processor, latch));
    }

    latch.await();

    HibernateUtils.getSessionFactory().close();
    System.out.println("Completed.");
  }


  private void processStore(Set<StoreProcessor> processors, String store) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    ExecutorService executor = Executors.newFixedThreadPool(processors.size());
    for (StoreProcessor processor : processors) {
      if(processor.getName().toLowerCase().equals(store)) {
        executor.submit(new StoreRunner(processor, latch));;
      }
    }
    latch.await();

    HibernateUtils.getSessionFactory().close();
    System.out.println("Completed.");
  }


  private Set<StoreProcessor> getProcessors(Config config) {
    Map<String, Store> stores = new StoreSqlDAO().getStoresAsMap();
    Set<StoreProcessor> processors = new HashSet<>();

    for (Map.Entry<String, StoreConf> kv : config.getStoreConfigs().entrySet()) {
      if (stores.containsKey(kv.getKey())) {
        kv.getValue().getStoreProcessor().setStore(stores.get(kv.getKey()));
        processors.add(kv.getValue().getStoreProcessor());
      }
    }

    return processors;
  }
}
