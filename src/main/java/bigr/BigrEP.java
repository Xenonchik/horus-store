package bigr;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
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

  public static final String DB_URL = "jdbc:postgresql://localhost/eye?user=postgres&password=postgrespass&currentSchema=bigr";

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

  // rozetkaParse();
//    antoshka();
    setSku();

    connectionSource.close();
  }

  static void rozetkaParse() throws Exception {
    List<BiProduct> productsTotal = Lists.newArrayList();
    Store store = new Store();
    StoreManager sm = new RozetkaProcessor();
    List<String> urls = Lists.newArrayList("http://rozetkaParse.com.ua/igrushechnoe-orujie/c104013/");
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
//    QueryBuilder<BiProduct, Integer> statementBuilder = productDao.queryBuilder();
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
        }
      }

    }
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
