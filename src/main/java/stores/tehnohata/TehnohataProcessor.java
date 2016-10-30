package stores.tehnohata;

import parser.HtmlProductParser;

import stores.StoreManager;
import parser.UrlPool;
;

/**
 * Done
 */
public class TehnohataProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new TehnohataParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new TehnohataUrlPool(catUrl);
  }
}
