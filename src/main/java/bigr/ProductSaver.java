package bigr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

import domain.Product;
import persistence.csv.CsvDataProvider;

/**
 * Blahblahblah
 */
public class ProductSaver {
  CsvDataProvider dataProvider;

  public ProductSaver(String path) {
    List<String> header = Lists.newArrayList("Name", "SKU", "Price", "Url");
    String filePath = path;
    dataProvider = new CsvDataProvider(filePath, header);
  }

  public void saveProducts(List<Product> products) throws Exception {
    for(Product product : products) {
      dataProvider.printRecord(Lists.newArrayList(product.getName(), getSKU(product.getName()),
          product.getPrice(), product.getUrl()));
    }
    dataProvider.flush();
  }

  public String getSKU(String name) {
    Pattern r = Pattern.compile("\\(.*?\\)");
    Matcher m = r.matcher(name);
    String s = "";
    while (m.find()) {
      s = m.group();
    }
    return s.replaceAll("\\(", "").replaceAll("\\)", "");
  }
}
