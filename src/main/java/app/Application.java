package app;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import conf.Config;
import conf.StoreConf;
import domain.Store;
import parser.StoreProcessor;
import persistence.ExportDAO;
import persistence.StoreDAO;

/**
 * Encapsulate application run logic
 */
public class Application {

  private final Options options = new Options();
  private final CommandLineParser parser = new DefaultParser();
  private CommandLine cmd;

  public Application() {
    setCmdOptions();
  }

  private void setCmdOptions() {
    options.addOption("e", false, "export");
    options.addOption("a", false, "parse all");
    options.addOption("s", true, "parse store");
  }

  private Config getConfig() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper.readValue(new File("src/main/resources/config.json"), Config.class);
  }

  public void go(String[] args) throws Exception {

    cmd = parser.parse(options, args);

    Set<StoreProcessor> processors = getProcessors(getConfig());

    if (cmd.hasOption("a")) {
      processAll(processors);
    } else if (cmd.hasOption("s")) {
      processStore(processors, cmd.getOptionValue("s"));
    }

    if (cmd.hasOption("e")) {
      export();
    }
  }

  private void export() {
    ExportDAO exportDAO = new ExportDAO();
    exportDAO.export();
  }

  private void processAll(Set<StoreProcessor> processors) throws InterruptedException {
    for (StoreProcessor processor : processors) {
      StoreRunner sr = new StoreRunner(processor);
      sr.start();
    }
  }

  private void processStore(Set<StoreProcessor> processors, String name) throws InterruptedException {
    for (StoreProcessor processor : processors) {
      if(processor.getName().equals(name)) {
        StoreRunner sr = new StoreRunner(processor);
        sr.start();
      }
    }
  }

  private Set<StoreProcessor> getProcessors(Config config) {
    Map<String, Store> stores = new StoreDAO().getStoresAsMap();
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
