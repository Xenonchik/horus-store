package app.proc;

import static util.CSVUtils.CSV_FORMAT;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;

import domain.Category;
import domain.OldGood;
import persistence.sql.OldGoodsSqlDAO;

/**
 * Created by serge on 5/30/16.
 */
public class EmirProcessor {

  public void process() throws IOException {

    List<OldGood> goods = new ArrayList<>();
    OldGoodsSqlDAO goodsDao = new OldGoodsSqlDAO();
    Map<String, Long> currentGoods = goodsDao.getIndexedGoods();

    Reader in = new FileReader("/opt/data/aliases.csv");
    Iterable<CSVRecord> records = CSV_FORMAT.parse(in);
    for (CSVRecord record : records) {
      if(currentGoods.get(record.get("Марка") + " " + record.get("Название товара")) == null) {
        try {
          OldGood good = new OldGood();
          good.setBrand(record.get("Марка"));
          good.setT1(record.get("ТипТовара 1"));
          good.setT2(record.get("ТипТовара 2"));
          good.setT3(record.get("ТипТовара 3"));
          good.setT4(record.get("ТипТовара 4"));
          good.setModel(record.get("Название товара"));
          Category category = new Category();
          category.setId(0l);
          good.setCategory(category);
          goods.add(good);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }


    goodsDao.insert(goods);

  }
}
