package stores.v590;

import parser.HtmlProductParser;

import stores.StoreManager;
import parser.UrlPool;

/**
 * Done
 */
public class V590Processor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new V590Parser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new V590UrlPool(catUrl);
  }
}
