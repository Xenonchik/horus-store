package stores.deshevle;

import parser.HtmlProductParser;

import stores.StoreManager;
import parser.UrlPool;
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
