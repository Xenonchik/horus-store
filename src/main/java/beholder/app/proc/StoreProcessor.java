package beholder.app.proc;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import beholder.domain.CatStore;
import beholder.domain.Product;
import beholder.domain.Store;
import beholder.parser.CategoryProcessor;
import beholder.persistence.sql.ProductSqlDAO;
import beholder.stores.StoreManager;

/**
 * Created by serge on 4/26/16.
 */
@Component
@Scope("prototype")
public class StoreProcessor implements Serializable {

  final static Logger log = LoggerFactory.getLogger(StoreProcessor.class);

  @Autowired
  private ProductSqlDAO dao;
  @Autowired
  private CategoryProcessor categoryProcessor;

  private Store store;
  private StoreManager storeManager;

  @Transactional
  public void process() throws InterruptedException {
    if(store == null) {
      log.warn("No store set!");
      return;
    }

    for(CatStore cat : store.getCategories()) {
      List<Product> products = categoryProcessor.process(cat, storeManager);
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


  public StoreManager getStoreManager() {
    return storeManager;
  }

  public void setStoreManager(StoreManager storeManager) {
    this.storeManager = storeManager;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StoreProcessor that = (StoreProcessor) o;

    if (store != null ? !store.equals(that.store) : that.store != null) return false;
    if (storeManager != null ? !storeManager.equals(that.storeManager) : that.storeManager != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = store != null ? store.hashCode() : 0;
    result = 31 * result + (storeManager != null ? storeManager.hashCode() : 0);
    return result;
  }
}
