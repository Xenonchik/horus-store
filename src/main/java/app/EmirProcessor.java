package app;

import static util.CSVUtils.CSV_FORMAT;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import domain.Category;
import domain.ExemplarGood;
import persistence.sql.ExemplarSqlDAO;

/**
 * Created by serge on 5/30/16.
 */
public class EmirProcessor {

  public void process() throws IOException {

    List<ExemplarGood> exemplarGoods = new ArrayList<>();

    Reader in = new FileReader("src/main/resources/emir.csv");
    Iterable<CSVRecord> records = CSV_FORMAT.parse(in);
    for (CSVRecord record : records) {
      if(!record.get("category").equals("")) {
        try {
          ExemplarGood exemplarGood = new ExemplarGood();
          exemplarGood.setBrand(record.get("brand"));
          exemplarGood.setT1(record.get("t1"));
          exemplarGood.setT2(record.get("t2"));
          exemplarGood.setT3(record.get("t3"));
          exemplarGood.setT4(record.get("t4"));
          exemplarGood.setModel(record.get("model"));
          Category category = new Category();
          category.setId(Long.parseLong(record.get("category")));
          exemplarGood.setCategory(category);
          exemplarGoods.add(exemplarGood);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }

    ExemplarSqlDAO exemplarDAO = new ExemplarSqlDAO();
    exemplarDAO.insert(exemplarGoods);

  }
}
