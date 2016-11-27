package beholder.persistence.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import beholder.domain.csv.AliasCsv;
import beholder.persistence.csv.CsvDataProvider;

/**
 * Blahblahblah
 */
public class AliacCsvEM implements EntityManager<AliasCsv> {
  private final List<String> stores;

  public AliacCsvEM(List<String> stores) {
    this.stores = stores;
  }

  @Override
  public List<AliasCsv> getEntities() {

    Iterable<CSVRecord> records = null;
    List<AliasCsv> result = new ArrayList<>();
    try {
      CsvDataProvider csvDataProvider = new CsvDataProvider("src/main/resources/data/emir-aliases.csv", new ArrayList<String>());
      records = csvDataProvider.getAllRecords();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (CSVRecord record : records) {
        try {
          AliasCsv aliasCsv = new AliasCsv();
          aliasCsv.setBrand(record.get("brand"));
          aliasCsv.setModel(record.get("model"));

          for(String store : stores) {
            aliasCsv.getStoreAliases().put(store, record.get(store));
          }
          result.add(aliasCsv);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    return result;
  }

  @Override
  public void saveEntitites(List<AliasCsv> entities) {

  }
}
