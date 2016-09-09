package persistence.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Alias;
import domain.Export;
import domain.Good;
import domain.Store;

/**
 * Blahblahblah
 */
public class AliasCsvDAO {

  private final CsvDataProvider provider;

  public AliasCsvDAO(List<Store> stores) throws IOException {
    this(stores, "src/main/resources/data/aliases.csv");
  }

  public AliasCsvDAO(List<Store> stores, String filename) {
    List<String> fileHeader = new ArrayList<>(Arrays.asList("ТипТовара 1", "ТипТовара 2", "ТипТовара 3", "ТипТовара 4", "Марка", "Название товара"));
    for (Store store : stores) {
      fileHeader.add(store.getName().toUpperCase());
    }

    provider = new CsvDataProvider(filename, fileHeader);
  }

  public void saveAlias(Good good) throws IOException {

    List dataRecord = getDataRecord(good);

    Map<String, String> aliasMap = new HashMap<>();
    for(Alias alias : good.getStoredAliases()) {
      aliasMap.put(alias.getStore(), alias.getAlias());
    }
    for (int i = 6; i < provider.getFileHeader().size(); i++) {
      dataRecord.add(aliasMap.get(provider.getFileHeader().get(i)));
    }
    provider.printRecord(dataRecord);
  }

  public void savePrice(Good good) throws IOException {
    List dataRecord = getDataRecord(good);

    for (int i = 6; i < provider.getFileHeader().size(); i++) {
      String store = provider.getFileHeader().get(i);
      Export export = good.getAliases().get(store);
      dataRecord.add(export == null ? "" : export.getPrice());
    }
    provider.printRecord(dataRecord);
  }

  private List getDataRecord(Good good) {
    List dataRecord = new ArrayList();

    dataRecord.add(good.getT1());
    dataRecord.add(good.getT2());
    dataRecord.add(good.getT3());
    dataRecord.add(good.getT4());
    dataRecord.add(good.getBrand());
    dataRecord.add(good.getModel());

    return dataRecord;
  }
}
