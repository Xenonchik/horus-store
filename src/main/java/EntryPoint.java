import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import conf.Config;
import conf.StoreConf;
import conf.StoreRunner;
import domain.Store;
import persistence.ExportDAO;
import persistence.StoreDAO;

/**
 * Created by serge on 3/27/16.
 */
public class EntryPoint {

  public static void main(String[] args) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    Config config = mapper.readValue(EntryPoint.class.getResourceAsStream("config.json"), Config.class);

    Options options = new Options();
    options.addOption("e", false, "export");
    options.addOption("a", false, "parse all");
    options.addOption("s", true, "parse store");

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);


    StoreDAO sd = new StoreDAO();
    Map<String, Store> stores = sd.getStoresAsMap();


    for(Map.Entry<String, StoreConf> kv : config.getStoreConfigs().entrySet()) {
      if(stores.containsKey(kv.getKey())) {
        kv.getValue().getStoreProcessor().setStore(stores.get(kv.getKey()));
      } else {
        config.getStoreConfigs().remove(kv.getKey());
      }
    }

    /*
    StoreProcessor - по сути контроллер
    processStore должна инкапсулировать в себя всю логику процессинга конкретного магазина
     */



    if (cmd.hasOption("a")) {
      processAll(config);
    } else if (cmd.hasOption("s")) {
      processStore(config, cmd.getOptionValue("s"));
    }

    if(cmd.hasOption("e")) {
      export(config);
    }

  }

  private static void export(Config config) {

    ExportDAO exportDAO = new ExportDAO(config.getBrands());
    exportDAO.export();
  }

  private static void processAll(Config config) throws InterruptedException {
    for (Map.Entry<String, StoreConf> kv : config.getStoreConfigs().entrySet()) {
      kv.getValue().getStoreProcessor().setUseragents(config.getUseragents()); // move useragents to database

      StoreRunner sr = new StoreRunner(kv.getValue(), kv.getKey());
      sr.start();
    }
  }

  private static void processStore(Config config, String name) throws InterruptedException {
    StoreConf sc = config.getStoreConfigs().get(name);
    sc.getStoreProcessor().setUseragents(config.getUseragents());
    StoreRunner sr = new StoreRunner(sc, name);
    sr.start();
  }

  private static void processUrl(Config config, String name, String catUrl) throws InterruptedException {
    config.getStoreConfigs().get(name).getStoreProcessor().setUseragents(config.getUseragents()).processCategory(catUrl);
  }

}
