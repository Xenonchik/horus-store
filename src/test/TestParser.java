import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import domain.CatStore;
import domain.Product;
import domain.Store;
import junit.framework.TestCase;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.antoshka.AntoshkaProcessor;
import stores.deshevle.DeshevleProcessor;
import stores.fotos.FotosProcessor;
import stores.foxtrot.FoxtrotProcessor;
import stores.mobilluck.MobilluckProcessor;
import stores.palladium.PalladiumProcessor;
import stores.rozetka.RozetkaProcessor;
import stores.tehnohata.TehnohataProcessor;
import stores.v590.V590Processor;

/**
 * Blahblahblah
 */
public class TestParser extends TestCase {

  final static Logger log = LoggerFactory.getLogger(TestParser.class);

//  @Test
//  public void testFotos() throws InterruptedException {
//    Store store = new Store();
//    store.setName("FOTOS");
//    StoreManager sm = new FotosProcessor();
//
//
//    CatStore testCat = new CatStore();
//    testCat.setStore(store);
//    testCat.setUrl("https://f.ua/shop/mikrofony/");
//
//    List<Product> result = Lists.newArrayList();
//    for(int i = 0; i < 5; i++) {
//      List<Product> products = new CategoryProcessor().process(testCat, sm);
//      result.addAll(products);
//    }
//
//    for (Product product : result) {
//      log.info(product.getName() + "  " + product.getPrice());
//    }
//  }

  @Test
  public void testPalladium() throws InterruptedException {
    Store store = new Store();
    store.setName("PALLADIUM");
    StoreManager sm = new PalladiumProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("https://palladium.ua/holodilniki.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }

    assertTrue(products.size() > 0);
  }

  @Test
  public void testFoxtrot() throws InterruptedException {
    Store store = new Store();
    store.setName("FOXTROT");
    StoreManager sm = new FoxtrotProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://www.foxtrot.com.ua/ru/shop/vytyagki_electrolux.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
    assertTrue(products.size() > 0);
  }

  @Test
  public void test590() throws InterruptedException {
    Store store = new Store();
    store.setName("V590");
    StoreManager sm = new V590Processor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("https://590.ua/vt/drawing/brand/bosch");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
    assertTrue(products.size() > 0);
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
    assertTrue(products.size() > 0);
  }

  @Test
  public void testRozetka() throws InterruptedException {
    Store store = new Store();
    store.setName("ROZETKA");
    StoreManager sm = new RozetkaProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://bt.rozetka.com.ua/extractor_fans/c80140/filter/");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
    assertTrue(products.size() > 0);
  }

  @Test
  public void testDeshevle() throws InterruptedException {
    Store store = new Store();
    store.setName("DESHEVLE");
    StoreManager sm = new DeshevleProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://www.deshevle-net.com.ua/dir/kbt/vstraivaemaya-tehnika/dukhovye-shkafy_copy/bosch.html");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice() + " ");
    }
    assertTrue(products.size() > 0);
  }

  @Test
  public void testMobilluck() throws InterruptedException {
    Store store = new Store();
    store.setName("MOBILLUCK");
    StoreManager sm = new MobilluckProcessor();


    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl("http://www.mobilluck.com.ua/katalog/Fridge/Liebherr/");

    List<Product> products = new CategoryProcessor().process(testCat, sm);
    for (Product product : products) {
      log.info(product.getName() + "  " + product.getPrice());
    }
    assertTrue(products.size() > 0);
  }

//  @Test
//  public void testAntoshka() throws InterruptedException {
//    Store store = new Store();
//
//    StoreManager sm = new AntoshkaProcessor();
//
//
//    CatStore testCat = new CatStore();
//    testCat.setStore(store);
//    testCat.setUrl("http://antoshka.ua/igrushki/igrushechnoe-oruzhie.html");
//
//    List<Product> products = new CategoryProcessor().process(testCat, sm);
//    for (Product product : products) {
//      log.info(product.getName() + "  " + product.getPrice());
//    }
//  }


}