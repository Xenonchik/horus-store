package beholder.stores.v590;

import beholder.parser.HtmlProductParser;

import beholder.stores.StoreManager;
import beholder.parser.UrlPool;

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
