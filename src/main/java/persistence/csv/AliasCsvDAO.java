package persistence.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.ExemplarGood;
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

  public void save(ExemplarGood exemplarGood) throws IOException {
    List dataRecord = new ArrayList();

    dataRecord.add(exemplarGood.getT1());
    dataRecord.add(exemplarGood.getT2());
    dataRecord.add(exemplarGood.getT3());
    dataRecord.add(exemplarGood.getT4());
    dataRecord.add(exemplarGood.getBrand());
    dataRecord.add(exemplarGood.getModel());
    for (int i = 6; i < provider.getFileHeader().size(); i++) {
      // dataRecord.add(eg.getAliases().get(fileHeader.get(i)).getPrice());
      dataRecord.add(exemplarGood.getAliases().get(provider.getFileHeader().get(i)).getFullName());
    }
    provider.printRecord(dataRecord);
  }
}
