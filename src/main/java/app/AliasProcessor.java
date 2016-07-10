package app;

import static util.CSVUtils.CSV_FORMAT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Alias;
import domain.EmirGood;
import domain.Export;
import domain.Store;
import persistence.AliasDAO;
import persistence.EmirDAO;
import persistence.ExportDAO;
import persistence.StoreDAO;

/**
 * Many goods in different stores have
 * different names
 */
public class AliasProcessor {

  final static Logger log = LoggerFactory.getLogger(AliasProcessor.class);

  protected final ExportDAO exportDAO = new ExportDAO();
  protected final List<Store> stores = new StoreDAO().getStores();
  protected final EmirDAO emirDAO = new EmirDAO();
  protected ArrayList<String> fileHeader;
  protected CSVPrinter csvFilePrinter = null;
  protected AliasDAO aliasDAO = new AliasDAO();
  protected FileWriter fileWriter;
  protected String filename = "src/main/resources/data/prices.csv";

  public void process() throws IOException {

    init();

    goodsAliaces();

  }

  protected void init() throws IOException {

    fileHeader = new ArrayList<>(Arrays.asList("ТипТовара 1", "ТипТовара 2", "ТипТовара 3", "ТипТовара 4", "Марка", "Название товара"));
    for (Store store : stores) {
      fileHeader.add(store.getName().toUpperCase());
    }
    fileWriter = new FileWriter(filename);
    csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT);
    csvFilePrinter.printRecord(fileHeader);
  }

  private List<EmirGood> goodsAliaces() throws IOException {

    List<EmirGood> emirGoods = emirDAO.getGoods();

    for (EmirGood emirGood : emirGoods) {
      //filter object
      Export filterExport = new Export();
      filterExport.setCategory(emirGood.getCategory().longValue());

      for (Store store : stores) {
        filterExport.setStore(store.getName());
        for (Export exp : exportDAO.getExport(filterExport)) {
          if (isAlias(emirGood, exp)) {
            log.info("Alias for " + emirGood.getModel() + " : " + exp.getFullName());
            emirGood.getAliases().put(store.getName(), exp);
            break;
          }
        }
        if (emirGood.getAliases().get(store.getName()) == null) {
          // add export with empty name
          emirGood.getAliases().put(store.getName(), filterExport);
        }
      }
      printAlias(emirGood);
      //saveAlias(emirGood);
    }

    aliasDAO.closeSessionFactory();
    return emirGoods;
  }

  private void printAlias(EmirGood eg) throws IOException {
    List dataRecord = new ArrayList();

    dataRecord.add(eg.getT1());
    dataRecord.add(eg.getT2());
    dataRecord.add(eg.getT3());
    dataRecord.add(eg.getT4());
    dataRecord.add(eg.getBrand());
    dataRecord.add(eg.getModel());
    for (int i = 6; i < fileHeader.size(); i++) {
      dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getPrice());
    }
    csvFilePrinter.printRecord(dataRecord);
  }

  private void saveAlias(EmirGood eg) {
    List<Alias> aliases = new ArrayList<>();

    for (Map.Entry<String, Export> kv : eg.getAliases().entrySet()) {
      if(kv.getValue().getId() != null) {
        Alias alias = new Alias();
        alias.setGood(eg.getId());
        alias.setStore(kv.getKey());
        alias.setAlias(kv.getValue().getFullName());
        aliases.add(alias);
      }
    }

    aliasDAO.insert(aliases);
  }

  private boolean isAlias(EmirGood emirGood, Export exp) {

    String emirName = emirGood.getModel();
    String exportName = exp.getModel();

    //1. Clean all whitespaces
    emirName = emirName.replaceAll("\\s+", "").toUpperCase();
    exportName = exportName.replaceAll("\\s+", "").toUpperCase();

    // 2. Check equality
    if (emirName.equals(exportName)) {
      if(emirGood.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }

    //3. Check is one subpart of another
    if (emirName.contains(exportName)) {
      if(emirGood.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }
    if (exportName.contains(emirName)) {
      if(emirGood.getBrand().toUpperCase().equals(exp.getBrand()))
      return true;
    }

    return false;
  }

}
