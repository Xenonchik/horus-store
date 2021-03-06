package app.proc;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CatStore;
import domain.Product;
import domain.Store;
import parser.CategoryProcessor;
import persistence.sql.ProductSqlDAO;
import stores.StoreManager;

/**
 * Created by serge on 4/26/16.
 */
public class StoreProcessor implements Serializable {

  final static Logger log = LoggerFactory.getLogger(StoreProcessor.class);

  private final StoreManager storeManager;
  private final ProductSqlDAO dao = new ProductSqlDAO();
  private Store store;

  public StoreProcessor(StoreManager storeManager) {
    this.storeManager = storeManager;
  }

  public void process() throws InterruptedException {
    if(store == null) {
      log.warn("No store set!");
      return;
    }

    for(CatStore cat : store.getCategories()) {
      List<Product> products = new CategoryProcessor().process(cat, storeManager);
      dao.insert(products);
    }

  }

  public String getName() {
    return store.getName();
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }


}
