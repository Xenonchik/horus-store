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

import domain.EmirGood;
import domain.Export;
import domain.Store;
import persistence.EmirDAO;
import persistence.ExportDAO;
import persistence.StoreDAO;

/**
 * Created by serge on 5/30/16.
 */
public class AliasProcessor {

  final static Logger log = LoggerFactory.getLogger(AliasProcessor.class);

  private final ExportDAO exportDAO = new ExportDAO();
  private final List<Store> stores = new StoreDAO().getStores();
  private final EmirDAO emirDAO = new EmirDAO();
  ArrayList<String> fileHeader;
  CSVPrinter csvFilePrinter = null;

  public void process() throws IOException {

    FileWriter fileWriter = null;

    fileHeader = new ArrayList<>(Arrays.asList("Марка", "ТипТовара 1", "ТипТовара 2", "ТипТовара 3", "ТипТовара 4", "Название товара"));
    for(Store store : stores) {
      fileHeader.add(store.getName().toUpperCase());
    }

    fileWriter = new FileWriter("src/main/resources/data/aliaces.csv");

    //initialize CSVPrinter object
    csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT);

    //Create CSV file header
    csvFilePrinter.printRecord(fileHeader);

    goodsAliaces();

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
          if(isAlias(emirGood, exp)) {
            log.info("Alias for " + emirGood.getModel() + " : " +exp.getFullName());
            emirGood.getAliases().put(store.getName(), exp);
            break;
          }
        }
        if(emirGood.getAliases().get(store.getName()) == null) {
          // add export with empty name
          emirGood.getAliases().put(store.getName(), filterExport);
        }
      }
      printAlias(emirGood);
    }
    return emirGoods;
  }

  private void printAlias(EmirGood eg) throws IOException {
    List dataRecord = new ArrayList();

    dataRecord.add(eg.getBrand());
    dataRecord.add(eg.getT1());
    dataRecord.add(eg.getT2());
    dataRecord.add(eg.getT3());
    dataRecord.add(eg.getT4());
    dataRecord.add(eg.getModel());
    for(int i = 6; i < fileHeader.size(); i ++) {
      dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getFullName());
    }
    csvFilePrinter.printRecord(dataRecord);
  }

  private boolean isAlias(EmirGood emirGood, Export exp) {

    String emirName = emirGood.getModel();
    String exportName = exp.getModel();

    //1. Clean all whitespaces
    emirName = emirName.replaceAll("\\s+","").toUpperCase();
    exportName = exportName.replaceAll("\\s+","").toUpperCase();

    // 2. Check equality
    if(emirName.equals(exportName)) return true;

    //3. Check is one subpart of another
    if(emirName.contains(exportName)) {
      return true;
    }
    if(exportName.contains(emirName)) {
      return true;
    }

    return false;
  }

}
