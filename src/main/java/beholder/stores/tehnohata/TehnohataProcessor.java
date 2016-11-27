package beholder.stores.tehnohata;

import beholder.parser.HtmlProductParser;

import beholder.stores.StoreManager;
import beholder.parser.UrlPool;
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
