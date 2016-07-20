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
import domain.ExemplarGood;
import domain.Export;
import domain.Store;
import persistence.AliasDAO;
import persistence.ExemplarDAO;
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
  protected final ExemplarDAO exemplarDAO = new ExemplarDAO();
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

  private List<ExemplarGood> goodsAliaces() throws IOException {

    List<ExemplarGood> exemplarGoods = exemplarDAO.getGoods();

    for (ExemplarGood exemplarGood : exemplarGoods) {

      for (Store store : stores) {

        for (Export exp : exportDAO.getExport(store, exemplarGood.getCategory())) {
          if (isAlias(exemplarGood, exp)) {
            log.info("Alias for " + exemplarGood.getModel() + " : " + exp.getFullName());
            exemplarGood.getAliases().put(store.getName(), exp);
            break;
          }
        }
        if (exemplarGood.getAliases().get(store.getName()) == null) {
          // add export with empty name
          exemplarGood.getAliases().put(store.getName(), new Export());
        }
      }
      printAlias(exemplarGood);
      //saveAlias(emirGood);
    }

    aliasDAO.closeSessionFactory();
    return exemplarGoods;
  }

  private void printAlias(ExemplarGood eg) throws IOException {
    List dataRecord = new ArrayList();

    dataRecord.add(eg.getT1());
    dataRecord.add(eg.getT2());
    dataRecord.add(eg.getT3());
    dataRecord.add(eg.getT4());
    dataRecord.add(eg.getBrand());
    dataRecord.add(eg.getModel());
    for (int i = 6; i < fileHeader.size(); i++) {
      // dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getPrice());
      dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getFullName());
    }
    csvFilePrinter.printRecord(dataRecord);
  }

  private void saveAlias(ExemplarGood eg) {
    List<Alias> aliases = new ArrayList<>();

    for (Map.Entry<String, Export> kv : eg.getAliases().entrySet()) {
      if (kv.getValue().getId() != null) {
        Alias alias = new Alias();
        alias.setGood(eg.getId());
        alias.setStore(kv.getKey());
        alias.setAlias(kv.getValue().getFullName());
        aliases.add(alias);
      }
    }

    aliasDAO.insert(aliases);
  }

  private boolean isAlias(ExemplarGood exemplarGood, Export exp) {

    String emirName = exemplarGood.getModel();
    String exportName = exp.getModel();

    //1. Clean all whitespaces
    emirName = emirName.replaceAll("\\s+", "").toUpperCase();
    exportName = exportName.replaceAll("\\s+", "").toUpperCase();

    // 2. Check equality
    if (emirName.equals(exportName)) {
      if (exemplarGood.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }

    //3. Check is one subpart of another
    if (emirName.contains(exportName)) {
      if (exemplarGood.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }
    if (exportName.contains(emirName)) {
      if (exemplarGood.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }

    return false;
  }

}
