package bigr;

import java.util.List;

import com.google.common.collect.Lists;

import domain.CatStore;
import domain.Product;
import domain.Store;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.antoshka.AntoshkaProcessor;
import stores.rozetka.RozetkaProcessor;

/**
 * Blahblahblah
 */
public class BigrEP {

  public static void main(String[] args) throws Exception {

   rozetka();
//    antoshka();


  }

  static void rozetka() throws Exception {
    List<Product> productsTotal = Lists.newArrayList();
    Store store = new Store();
    StoreManager sm = new RozetkaProcessor();
    List<String> urls = Lists.newArrayList("http://rozetka.com.ua/igrushechnoe-orujie/c104013/", "http://rozetka.com.ua/building_kits/c97420/22581=8843/");
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      productsTotal.addAll(products);
    }
    BiProductCsvDao ps = new BiProductCsvDao("/opt/data/bi-rozetka.csv");
    ps.saveProducts(productsTotal);
  }

  static void antoshka() throws Exception {
    List<Product> productsTotal = Lists.newArrayList();
    Store store = new Store();
    StoreManager sm = new AntoshkaProcessor();
    List<String> urls = Lists.newArrayList("http://antoshka.ua/igrushki/igrushechnoe-oruzhie.html", "http://antoshka.ua/igrushki/lego/f/vid/lego-city.html");
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      productsTotal.addAll(products);
    }
    BiProductCsvDao ps = new BiProductCsvDao("/opt/data/bi-antoshka.csv");
    ps.saveProducts(productsTotal);
  }
}
