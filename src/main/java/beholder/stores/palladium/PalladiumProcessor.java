package beholder.stores.palladium;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;
import beholder.stores.StoreManager;

/**
 * Done
 */
public class PalladiumProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new PalladiumParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new PalladiumUrlPool(catUrl);
  }
}
