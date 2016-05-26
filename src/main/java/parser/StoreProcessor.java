package parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import domain.Product;
import domain.Store;
import parser.source.HtmlSourceBuilder;
import parser.source.ParseSource;
import parser.source.SourceBuilder;
import persistence.ProductDAO;

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
  
  private List<String> useragents;

  private String name;

  private Store store;


  /**
   * Process strategy
   * @param catUrl
   * @throws InterruptedException
   */
  public void processCategory(String catUrl) throws InterruptedException {

    UrlPool pool = getUrlPool(catUrl);
    Parser parser = getParcer();
    List<Product> products = new ArrayList<>();

    int pageCount = 0;
    while (parser.processCategory()){
      String url = pool.getNextUrl().toString();
      ParseSource ps = getSourceBuilder().getSource(url);
      pageCount ++;
      List<Product> productList = parser.parse(ps);


      if((productList.size() > 0 && products.size() > 0) && productList.get(0).getName().equals(products.get(0).getName()))
        break;
      else
        products.addAll(productList);
      Thread.sleep(getDelay());


    }

    log.info("Parsed url: " + catUrl + " Products found: " + products.size() + " Pages: " + pageCount);
    ProductDAO dao = new ProductDAO();
    dao.insert(products);

  }

  protected Long getDelay() {
    return 3500l;
  }

  protected abstract Parser getParcer();

  protected SourceBuilder getSourceBuilder() {
    HtmlSourceBuilder hsb = new HtmlSourceBuilder();
    hsb.setUserAgent(useragents.get(ThreadLocalRandom.current().nextInt(useragents.size())));
    return hsb;
  }

  protected abstract UrlPool getUrlPool(String catUrl);

  public StoreProcessor setUseragents(List<String> useragents) {
    this.useragents = useragents;
    return this;
  }


  public String getName() {
    return name;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }
}
