package beholder.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import beholder.domain.CatStore;
import beholder.domain.Product;
import beholder.domain.Useragent;
import beholder.parser.source.HtmlSourceBuilder;
import beholder.parser.source.ParseSource;
import beholder.parser.source.SourceBuilder;
import beholder.persistence.sql.SupportSqlDAO;
import beholder.stores.StoreManager;

/**
 * Blahblahblah
 */
@Component
public class CategoryProcessor {

  final static Logger log = LoggerFactory.getLogger(CategoryProcessor.class);

  @Autowired
  private SupportSqlDAO supportSqlDAO;

  @Transactional
  public List<Product> process(CatStore cat, StoreManager storeManager) throws InterruptedException {

    List<Useragent> useragents = supportSqlDAO.getUseragents();

    String catUrl = cat.getUrl();
    UrlPool pool = storeManager.getUrlPool(catUrl);
    HtmlProductParser parser = storeManager.getParcer();
    parser.setCategory(cat.getCategory());
    List<Product> productsTotal = new ArrayList<>();
    int pageCount = 0;

    while (parser.processCategory()){
      String url = pool.getNextUrl().toString();
      log.info(url);
      ParseSource ps = getSourceBuilder(useragents).getSource(url);

      List<Product> productList = parser.parse(ps);

      if(checkContinueParsing(productList, productsTotal)) {
          break;
      }

      productsTotal.addAll(productList);

      pageCount ++;
      Long delay = ThreadLocalRandom.current().nextLong(storeManager.getDelay(), Math.round(storeManager.getDelay()*1.4));
      Thread.sleep(delay);
      break;
    }

    if(productsTotal.size() == 0) {
      log.warn("Parsed url: " + catUrl + " NO PRODUCTS FOUND! Pages: " + pageCount);
    } else {
      log.info("Parsed url: " + catUrl + " Products found: " + productsTotal.size() + " Pages: " + pageCount);
    }

    return productsTotal;
  }

  private boolean checkContinueParsing(List<Product> productList, List<Product> productsTotal) {
    if((productList.size() > 0 && productsTotal.size() > 0)) {
      if (productList.get(productList.size()-1).getName().equals(productsTotal.get(productsTotal.size() - 1).getName()))
        return true;
    }
    return false;
  }

  private SourceBuilder getSourceBuilder(List<Useragent> useragents) {
    HtmlSourceBuilder hsb = new HtmlSourceBuilder();
    hsb.setUserAgent(useragents.get(ThreadLocalRandom.current().nextInt(useragents.size())).getName());
    return hsb;
  }
}
