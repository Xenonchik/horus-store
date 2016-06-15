package app;

import static util.CSVUtils.CSV_FORMAT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
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
  private final StoreDAO storeDAO = new StoreDAO();
  private final EmirDAO emirDAO = new EmirDAO();

  public void process() throws IOException {
    Map<EmirGood, List<StoreExpWrapper>> aliaces = goodsAliaces();
    FileWriter fileWriter = null;
    CSVPrinter csvFilePrinter = null;
    String[] fileHeader = {"Марка", "ТипТовара 1", "ТипТовара 2", "ТипТовара 3", "ТипТовара 4", "Название товара", "DESHEVLE", "FOTOS", "FOXTROT", "MOBILLUCK", "PALLADIUM", "ROZETKA", "TEHNOHATA", "TEHNOS", "V590", "VSTROYKA"};


    fileWriter = new FileWriter("src/main/resources/aliaces.csv");

    //initialize CSVPrinter object
    csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT);

    //Create CSV file header
    csvFilePrinter.printRecord(fileHeader);

    //Write a new student object list to the CSV file
    for (Map.Entry<EmirGood, List<StoreExpWrapper>> kv : aliaces.entrySet()) {
      List dataRecord = new ArrayList();
      EmirGood eg = kv.getKey();
      dataRecord.add(eg.getBrand());
      dataRecord.add(eg.getT1());
      dataRecord.add(eg.getT2());
      dataRecord.add(eg.getT3());
      dataRecord.add(eg.getT4());
      dataRecord.add(eg.getModel());
      for(StoreExpWrapper sew : kv.getValue()) {
        dataRecord.add(sew.getAlias());
      }
      csvFilePrinter.printRecord(dataRecord);
    }

  }

  private Map<EmirGood, List<StoreExpWrapper>> goodsAliaces() {
    Map<EmirGood, List<StoreExpWrapper>> result = new HashMap<>();


    List<EmirGood> emirGoods = emirDAO.getGoods();
    List<Store> stores = storeDAO.getStores();

    for (EmirGood emirGood : emirGoods) {
      List<StoreExpWrapper> sews = new ArrayList<>();
      Export export = new Export();
      export.setCategory(emirGood.getCategory().longValue());
      for (Store store : stores) {
        StoreExpWrapper sew = new StoreExpWrapper();
        export.setStore(store.getName());
        sew.store = store;
        for (Export exp : exportDAO.getExport(export)) {
          int levenstein = StringUtils.getLevenshteinDistance(exp.getModel(), emirGood.getModel());
          if (levenstein < 2) {
            log.info(emirGood.getModel() + " - " + exp.getModel() + " : " + levenstein);
            ExpLevWrapper elw = new ExpLevWrapper(exp, levenstein);
            sew.exports.add(elw);
          }
        }
        Collections.sort(sew.exports, new Comparator<ExpLevWrapper>() {
          @Override
          public int compare(ExpLevWrapper o1, ExpLevWrapper o2) {
            if (o1.levenstein < o2.levenstein) return -1;
            if (o1.levenstein > o2.levenstein) return 1;
            return 0;
          }
        });
        sews.add(sew);

      }
      result.put(emirGood, sews);

    }
    return result;
  }


  class ExpLevWrapper {
    Export export;
    Integer levenstein;

    public ExpLevWrapper(Export export, Integer levenstein) {
      this.export = export;
      this.levenstein = levenstein;
    }
  }

  class StoreExpWrapper {
    Store store;
    List<ExpLevWrapper> exports = new ArrayList<>();

    public String getAlias() {
      if(exports.size() < 1) {
        return "";
      }
      Export export = exports.get(0).export;
      if(export == null) {
        return "";
      }
      return export.getFullName();
    }
  }


}
