package app;

import static util.CSVUtils.CSV_FORMAT;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import domain.EmirGood;
import persistence.EmirDAO;

/**
 * Created by serge on 5/30/16.
 */
public class EmirProcessor {

  public void process() throws IOException {

    List<EmirGood> emirGoods = new ArrayList<>();

    Reader in = new FileReader("src/main/resources/emir.csv");
    Iterable<CSVRecord> records = CSV_FORMAT.parse(in);
    for (CSVRecord record : records) {
      if(!record.get("category").equals("")) {
        try {
          EmirGood emirGood = new EmirGood();
          emirGood.setBrand(record.get("brand"));
          emirGood.setT1(record.get("t1"));
          emirGood.setT2(record.get("t2"));
          emirGood.setT3(record.get("t3"));
          emirGood.setT4(record.get("t4"));
          emirGood.setModel(record.get("model"));
          emirGood.setCategory(Integer.parseInt(record.get("category")));
          emirGoods.add(emirGood);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }

    EmirDAO emirDAO = new EmirDAO();
    emirDAO.insert(emirGoods);

  }
}
