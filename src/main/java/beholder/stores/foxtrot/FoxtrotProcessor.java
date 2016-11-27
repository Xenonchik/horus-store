package beholder.stores.foxtrot;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;
import beholder.stores.StoreManager;

/**
 * Done
 */
public class FoxtrotProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new FoxtrotParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new FoxtrotUrlPool(catUrl);
  }
}
