package stores.foxtrot;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

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
