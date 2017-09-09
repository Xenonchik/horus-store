package bigr;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import bigr.phase2.HotlineUrl;
import bigr.phase2.Phase2Ep;
import bigr.phase2.ProductsJson;
import domain.Product;

/**
 * Blahblahblah
 */
public class TestBigr2 {
  private Phase2Ep phase2Ep = new Phase2Ep();

  @Test
  public void testProcessor() {

    HotlineUrl url = new HotlineUrl();
    url.setUrl("http://hotline.ua/deti/lyzhi-dlya-samyh-malenkih/");
    url.setCount(23);
    url.setCategory("Прогулянка і активний відпочинок");

    phase2Ep.processURL(url);
  }

  @Test
  public void testWriteProductsJson() throws IOException {

    Product product0 = new Product();
    product0.setUrl("http:/url");
    product0.setName("Prodname1");
    product0.setCategory(1l);
    product0.setPrice(1233l);
    product0.setProductUrl("http://produrl");

    Product product1 = new Product();
    product1.setUrl("http:/url2");
    product1.setName("Prodname2");
    product1.setCategory(2l);
    product1.setPrice(6633l);
    product1.setProductUrl("http://produrl22");
    List<Product> products = Lists.newArrayList(product0, product1);


    ProductsJson pj = new ProductsJson();
    pj.write(products, Phase2Ep.DATA_FOLDER + "test.json");
  }
}
