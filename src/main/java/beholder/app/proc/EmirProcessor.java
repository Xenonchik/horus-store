package beholder.app.proc;

import static beholder.util.CSVUtils.CSV_FORMAT;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import beholder.domain.Category;
import beholder.domain.Good;
import beholder.persistence.sql.GoodsSqlDAO;

/**
 * Created by serge on 5/30/16.
 */
public class EmirProcessor {

  public void process() throws IOException {

    List<Good> goods = new ArrayList<>();

    Reader in = new FileReader("src/main/resources/emir.csv");
    Iterable<CSVRecord> records = CSV_FORMAT.parse(in);
    for (CSVRecord record : records) {
      if(!record.get("category").equals("")) {
        try {
          Good good = new Good();
          good.setBrand(record.get("brand"));
          good.setT1(record.get("t1"));
          good.setT2(record.get("t2"));
          good.setT3(record.get("t3"));
          good.setT4(record.get("t4"));
          good.setModel(record.get("model"));
          Category category = new Category();
          category.setId(Long.parseLong(record.get("category")));
          good.setCategory(category);
          goods.add(good);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }

    GoodsSqlDAO exemplarDAO = new GoodsSqlDAO();
    exemplarDAO.insert(goods);

  }
}
