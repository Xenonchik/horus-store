import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CatStore;
import domain.Product;
import domain.Store;
import junit.framework.TestCase;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.fotos.FotosProcessor;
import stores.foxtrot.FoxtrotProcessor;
import stores.palladium.PalladiumProcessor;
import stores.rozetka.RozetkaProcessor;
import stores.tehnohata.TehnohataProcessor;
import stores.v590.V590Processor;

/**
 * Blahblahblah
 */
public class TestParser extends TestCase {

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
    for (Product product : products.subList(0, 10)) {
      log.info(product.getName() + "  " + product.getPrice() + " " + product.getHtml());
    }
  }

  @Test
  public void testPalladium() throws InterruptedException {
    Store store = new Store();
    store.setName("PALLADIUM");
    StoreManager sm = new PalladiumProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://palladium.ua/vytjazhki.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
  }

  @Test
  public void testFoxtrot() throws InterruptedException {
    Store store = new Store();
    store.setName("FOXTROT");
    StoreManager sm = new FoxtrotProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://www.foxtrot.com.ua/ru/shop/vytyagki_bosch.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
  }

  @Test
  public void test590() throws InterruptedException {
    Store store = new Store();
    store.setName("V590");
    StoreManager sm = new V590Processor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://vt.590.ua/hobs");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
  }

  @Test
  public void testTehnohata() throws InterruptedException {
    Store store = new Store();
    store.setName("TEHNOHATA");
    StoreManager sm = new TehnohataProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://tehnohata.ua/f_1852_Bosch_built-in-microwave.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
  }

  @Test
  public void testRozetka() throws InterruptedException {
    Store store = new Store();
    store.setName("ROZETKA");
    StoreManager sm = new RozetkaProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://bt.rozetka.com.ua/extractor_fans/bosch/c80140/v148/");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
  }


}