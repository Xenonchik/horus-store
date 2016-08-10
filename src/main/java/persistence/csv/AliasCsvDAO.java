package persistence.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.Good;
import domain.Store;

/**
 * Blahblahblah
 */
public class AliasCsvDAO {

  private final CsvDataProvider provider;

  public AliasCsvDAO(List<Store> stores) throws IOException {
    String filename = "src/main/resources/data/aliases2.csv";
    List<String> fileHeader = new ArrayList<>(Arrays.asList("ТипТовара 1", "ТипТовара 2", "ТипТовара 3", "ТипТовара 4", "Марка", "Название товара"));
    for (Store store : stores) {
      fileHeader.add(store.getName().toUpperCase());
    }

    provider = new CsvDataProvider(filename, fileHeader);
  }

  public void save(Good good) throws IOException {
    List dataRecord = new ArrayList();

    dataRecord.add(good.getT1());
    dataRecord.add(good.getT2());
    dataRecord.add(good.getT3());
    dataRecord.add(good.getT4());
    dataRecord.add(good.getBrand());
    dataRecord.add(good.getModel());
    for (int i = 6; i < provider.getFileHeader().size(); i++) {
      // dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getPrice());
      dataRecord.add(good.getAliases().get(provider.getFileHeader().get(i)).getFullName());
    }
    provider.printRecord(dataRecord);
  }
}
