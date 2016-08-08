package app;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import conf.Config;
import conf.StoreConf;
import domain.Store;
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
    options.addOption("e", false, "export");
    options.addOption("a", false, "parse all");
    options.addOption("s", true, "parse store");
    options.addOption("emir", false, "parse emir");
    options.addOption("alias", false, "parse emir");
    options.addOption("ea", false, "parse emir");
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

    if (cmd.hasOption("a")) {
      processAll(processors);
    }

    if (cmd.hasOption("s")) {
      processStore(processors, cmd.getOptionValue("s"));
    }

    if(cmd.hasOption("emir")) {
      EmirProcessor ep = new EmirProcessor();
      ep.process();
    }

    if(cmd.hasOption("alias")) {
      AliasProcessor ap = new AliasProcessor();
      ap.process();
    }

    if (cmd.hasOption("e")) {
      new ExportProcessor().process();
    }

    if (cmd.hasOption("ea")) {
      new ExportAliacesProcessor().process();
    }
  }

  private void processAll(Set<StoreProcessor> processors) throws InterruptedException {
    for (StoreProcessor processor : processors) {
      StoreRunner sr = new StoreRunner(processor);
      sr.start();
    }
  }

  private void processStore(Set<StoreProcessor> processors, String store) {
    for (StoreProcessor processor : processors) {
      if(processor.getName().toLowerCase().equals(store)) {
        StoreRunner sr = new StoreRunner(processor);
        sr.start();
      }
    }
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
