package bigr;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

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

  final static Logger log = LoggerFactory.getLogger(BigrEP.class);

  public static final String DB_URL = "jdbc:postgresql://localhost/eye?user=pg_tmp&password=postgrespass&currentSchema=bigr";

  private static Dao<BiProduct, Integer> productDao = null;
  private static Dao<Sku, Integer> skuDao = null;
  private static ConnectionSource connectionSource;

  static {
    try {
      connectionSource = new JdbcConnectionSource(DB_URL);


    productDao =
        DaoManager.createDao(connectionSource, BiProduct.class);
      skuDao =
          DaoManager.createDao(connectionSource, Sku.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {

    rozetkaParse();
    antoshkaParse();
    setSku();

    connectionSource.close();
  }

  static void rozetkaParse() throws Exception {
    List<BiProduct> productsTotal = Lists.newArrayList();
    Store store = new Store();
    StoreManager sm = new RozetkaProcessor();
    List<String> urls = rozCats;
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      products.forEach(product -> {
            BiProduct biProduct = BiProduct.fromProduct(product);
            biProduct.setStore("ROZETKA");
            productsTotal.add(biProduct);
          }
      );

    }
    saveProducts(productsTotal);

  }

  public static void saveProducts(List<BiProduct> productsTotal) {
    productsTotal.forEach(product -> {
          try {
            productDao.create(product);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
    );
  }

  public static void setSku() throws SQLException {
    List<BiProduct> products = productDao.queryForAll();
    List<Sku> skus = skuDao.queryForAll();

    for(BiProduct product : products) {
      boolean hasSku = false;
      for(Sku sku : skus) {
        if(product.getStore().equals(sku.getStore())
            && product.getName().equals(sku.getName())
            ) {
          hasSku = true;
        }
      }
      if(!hasSku) {
        log.info("No SKU for product: " + product.getName() + "[" + product.getStore() + "]");
        switch (product.getStore()) {
          case "ROZETKA": {
            Sku sku = new RozetkaSkuBuilder().buildSku(product);
            skuDao.create(sku);
          }
          case "ANTOSHKA": {
            Sku sku = new AntoshkaSkuBuilder().buildSku(product);
            skuDao.create(sku);
          }
        }
      }

    }
  }

  static void antoshkaParse() throws Exception {
    List<BiProduct> productsTotal = Lists.newArrayList();
    Store store = new Store();
    StoreManager sm = new AntoshkaProcessor();
    List<String> urls = antCats;
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      products.forEach(product -> {
            BiProduct biProduct = BiProduct.fromProduct(product);
            biProduct.setStore("ANTOSHKA");
            productsTotal.add(biProduct);
          }
      );

    }
    saveProducts(productsTotal);
  }

  public static List<String> rozCats = Lists.asList(
      "http://rozetka.com.ua/building_kits/c97420/",
      new String[]{"http://rozetka.com.ua/nastoljnye-igry-i-golovolomki/c98280/",
  "http://rozetka.com.ua/avtomobilnye-treki/c102308/",
  "http://rozetka.com.ua/igrushechnoe-orujie/c104013/",
  "http://rozetka.com.ua/kollektsionnye-modelki/c103338/",
  "http://rozetka.com.ua/igrushechnye-mashinki-i-tehnika/c102003/",
  "http://rozetka.com.ua/rc_toys/c97422/",
  "http://rozetka.com.ua/dolls/c99392/",
  "http://rozetka.com.ua/pups/c100313/",
  "http://rozetka.com.ua/soft_toys/c99051/",
  "http://rozetka.com.ua/figures/c99253/",
  "http://rozetka.com.ua/igrushki-dlya-malyshey/c100193/",
  "http://rozetka.com.ua/s_centers/c103303/",
  "http://rozetka.com.ua/interactive_toys/c98159/",
  "http://rozetka.com.ua/tvorchestvo/c102912/",
  "http://rozetka.com.ua/rattles/c103323/",
  "http://rozetka.com.ua/chudomobili-hodunki-kachalki/c104034/",
  "http://rozetka.com.ua/gaming_kits/c99674/filter/",
  "http://rozetka.com.ua/aksessuary-dlya-kukol-i-pupsov/c102829/",
  "http://rozetka.com.ua/nabory-dlya-nauchnyh-issledovaniy/c102843/",
  "http://rozetka.com.ua/puzzles/c102848/",
  "http://rozetka.com.ua/railroad/c99364/"}
  );

  public static List<String> antCats = Lists.asList(
  "http://antoshka.ua/igrushki/lego.html",
      new String[]{
  "http://antoshka.ua/igrushki/aktivnyy-otdyh.html",
  "http://antoshka.ua/igrushki/dlya-malenkih-detey.html",
  "http://antoshka.ua/igrushki/mashinki-i-tehnika.html",
  "http://antoshka.ua/igrushki/myagkie-igrushki.html",
  "http://antoshka.ua/igrushki/konstruktor.html",
  "http://antoshka.ua/igrushki/kukly-i-aksessuary.html",
  "http://antoshka.ua/igrushki/nastolnye-igry.html",
  "http://antoshka.ua/igrushki/tvorchestvo.html",
  "http://antoshka.ua/igrushki/roboty-i-figurki.html",
  "http://antoshka.ua/igrushki/igrushechnoe-oruzhie.html",
  "http://antoshka.ua/igrushki/pazzly.html",
  "http://antoshka.ua/igrushki/tematicheskie-igrovye-nabory.html",
  "http://antoshka.ua/igrushki/muzykalnye-instrumenty.html",
  "http://antoshka.ua/igrushki/knigi.html",
  "http://antoshka.ua/igrushki/dekorativnaya-detskaya-kosmetika-i-aksessuary.html",
  "http://antoshka.ua/igrushki/vse-dlya-prazdnika.html",
  "http://antoshka.ua/igrushki/elementy-pitaniya-i-fonari.html",
  "http://antoshka.ua/igrushki/kancelyarskie-tovary.html",
  "http://antoshka.ua/igrushki/krupnogabaritnye-igrushki.html",
  "http://antoshka.ua/igrushki/optika.html"});
}
