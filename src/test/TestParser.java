import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CatStore;
import domain.Product;
import domain.Store;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.fotos.FotosProcessor;

/**
 * Blahblahblah
 */
public class TestParser {

  final static Logger log = LoggerFactory.getLogger(TestParser.class);

  @Test
  public void testFotos() throws InterruptedException {
    Store store = new Store();
    store.setName("FOTOS");
    StoreManager sm = new FotosProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("https://f.ua/shop/smesiteli-dlya-mojki/");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for(Product product : products.subList(0, 10)) {
      log.info(product.getName() + "  " + product.getPrice() + " " + product.getHtml());
    }

  }
}
