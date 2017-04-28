package bigr;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVRecord;

import com.google.common.collect.Lists;

import domain.Product;
import persistence.csv.CsvDataProvider;

/**
 * Blahblahblah
 */
public class BiProductCsvDao {
  CsvDataProvider dataProvider;

  public BiProductCsvDao(String path) {
    List<String> header = Lists.newArrayList("Name", "SKU", "Price", "Url");
    String filePath = path;
    dataProvider = new CsvDataProvider(filePath, header);
  }

  public void saveProducts(List<Product> products) throws Exception {
    for(Product product : products) {
      dataProvider.printRecord(Lists.newArrayList(product.getName(), getSKU(product.getName()),
          product.getPrice(), product.getProductUrl()));
    }
    dataProvider.flush();
  }

  public void saveAntoshkaProducts(List<BiProduct> products) throws Exception {
    for(BiProduct product : products) {
      dataProvider.printRecord(Lists.newArrayList(product.getName(), product.getSKU(),
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

  public List<BiProduct> getProducts() throws IOException {
    List<BiProduct> result = Lists.newArrayList();
    for(CSVRecord record : dataProvider.getAllRecords()) {
      BiProduct product = new BiProduct();
      product.setName(record.get(0));
      product.setSKU(record.get(1));
      product.setPrice(record.get(2));
      product.setUrl(record.get(3));
      result.add(product);
    }
    return result;
  }
}
