package app;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import domain.CatStore;
import domain.Product;
import domain.Store;
import domain.Useragent;
import parser.Parser;
import parser.UrlPool;
import parser.source.HtmlSourceBuilder;
import parser.source.ParseSource;
import parser.source.SourceBuilder;
import persistence.sql.ProductSqlDAO;
import persistence.sql.SupportSqlDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by serge on 4/26/16.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
abstract public class StoreProcessor implements Serializable {

  final static Logger log = LoggerFactory.getLogger(StoreProcessor.class);

  private final SupportSqlDAO supportDAO = new SupportSqlDAO();
  private final ProductSqlDAO dao = new ProductSqlDAO();

  private Store store;

  public void process() throws InterruptedException {
    if(store == null) {
      log.warn("No store set!");
      return;
    }

    for(CatStore cat : store.getCategories()) {
        processCategory(cat);
    }

  }


  /**
   * Process strategy
   * @param cat
   * @throws InterruptedException
   */
  public void processCategory(CatStore cat) throws InterruptedException {

    String catUrl = cat.getUrl();

    UrlPool pool = getUrlPool(catUrl);
    Parser parser = getParcer();
    parser.setCategory(cat.getCategory());

    List<Product> products = new ArrayList<>();

    int pageCount = 0;
    while (parser.processCategory()){
      String url = pool.getNextUrl().toString();
      log.info(url);
      ParseSource ps = getSourceBuilder().getSource(url);
      pageCount ++;
      List<Product> productList = parser.parse(ps);


      if((productList.size() > 0 && products.size() > 0)) {

        if (productList.get(productList.size()-1).getName().equals(products.get(products.size() - 1).getName()))
          break;
      }
      products.addAll(productList);

      Long delay = ThreadLocalRandom.current().nextLong(getDelay(), Math.round(getDelay()*1.4));
      Thread.sleep(delay);

    }

    if(products.size() == 0) {
      log.warn("Parsed url: " + catUrl + " NO PRODUCTS FOUND! Pages: " + pageCount);
    } else {
      log.info("Parsed url: " + catUrl + " Products found: " + products.size() + " Pages: " + pageCount);
    }

    dao.insert(products);

  }


  protected SourceBuilder getSourceBuilder() {
    HtmlSourceBuilder hsb = new HtmlSourceBuilder();
    List<Useragent> useragents = supportDAO.getUseragents();
    hsb.setUserAgent(useragents.get(ThreadLocalRandom.current().nextInt(useragents.size())).getName());
    return hsb;
  }

  protected abstract UrlPool getUrlPool(String catUrl);

  public String getName() {
    return store.getName();
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  protected Long getDelay() {
    return 3500l;
  }

  protected abstract Parser getParcer();


}
