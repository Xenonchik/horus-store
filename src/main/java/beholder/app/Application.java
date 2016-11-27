package beholder.app;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import beholder.app.proc.AliasProcessor;
import beholder.app.proc.EmailProcessor;
import beholder.app.proc.EmirProcessor;
import beholder.app.proc.ExportAliacesProcessor;
import beholder.app.proc.ExportProcessor;
import beholder.app.proc.MonitoringProcessor;
import beholder.app.proc.PricesProcessor;
import beholder.app.proc.StoreProcessor;
import beholder.app.store.StoreParsersExecutor;
import beholder.service.ProductMonitoringService;
import beholder.service.StoresService;

/**
 * Encapsulate application run logic
 */
@Component
public class Application {

  final static Logger log = LoggerFactory.getLogger(Application.class);

  private final Options options = new Options();
  private final CommandLineParser parser = new DefaultParser();
  private CommandLine cmd;

  @Autowired
  private StoresService storesService;

  @Autowired
  private ProductMonitoringService productMonitoringService;

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
    options.addOption("extract", false, "parse emir");
    options.addOption("monitor", false, "parse emir");
  }


  public void go(String[] args) throws Exception {
    cmd = parser.parse(options, args);

    try {

      Set<StoreProcessor> processors = storesService.getProcessors();

      if (cmd.hasOption("parseall")) {
        new StoreParsersExecutor().parseAll(processors);
      }

      if (cmd.hasOption("parsestore")) {
        Set<StoreProcessor> aloneProcessor =
            processors.stream().filter(
                processor ->
                    processor.getName().toLowerCase().equals(cmd.getOptionValue("parsestore").toLowerCase())
            ).collect(Collectors.toSet());
        new StoreParsersExecutor().parseAll(aloneProcessor);
      }

      if (cmd.hasOption("emir")) {
        new EmirProcessor().process();
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

      if (cmd.hasOption("monitor")) {
        productMonitoringService.monitor();
        productMonitoringService.moveToHistory();
      }

      if (cmd.hasOption("total")) {
        new StoreParsersExecutor().parseAll(processors);
        log.info("Data gathered");
        new ExportProcessor().process();
        new AliasProcessor().process();
        new PricesProcessor().process();
        log.info("Prices in file");
        new EmailProcessor().process();
        log.info("Email sent");
      }

      if (cmd.hasOption("extract")) {
        new ExportProcessor().process();
        new AliasProcessor().process();
        new PricesProcessor().process();
        log.info("Prices in file");
        new EmailProcessor().process();
        log.info("Email sent");
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
//      HibernateUtils.getSessionFactory().close();
    }
  }


}
