package bigr;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Created by serge on 4/26/17.
 */
public class TestAntoshkaSKu {

  @Test
  public void testGetSKU() {
    AntoshkaProductParser antoshkaProductParser = new AntoshkaProductParser();
    antoshkaProductParser.parseUrl("http://antoshkaParse.ua/nabor-oruzhija-ser-cherepashki-nindzja-mik.html");
  }

  @Test
  public void testDao() throws Exception {
    BiProductCsvDao dao = new BiProductCsvDao("/opt/data/bi-antoshkaParse.csv");
    BiProductCsvDao dao2 = new BiProductCsvDao("/opt/data/bi-antoshka2.csv");
    List<BiProduct> products = Lists.newArrayList();
    for(BiProduct product : dao.getProducts()) {
      AntoshkaProductParser antoshkaProductParser = new AntoshkaProductParser();
      product.setSKU(antoshkaProductParser.parseUrl(product.getUrl()));
      Thread.sleep(1500l);
      products.add(product);
    }

    dao2.saveAntoshkaProducts(products);

  }
}
