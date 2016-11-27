package beholder.stores.deshevle;

import beholder.parser.HtmlProductParser;

import beholder.stores.StoreManager;
import beholder.parser.UrlPool;
;

/**
 * Done
 */
public class DeshevleProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new DeshevleParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new DeshevleUrlPool(catUrl);
  }
}
