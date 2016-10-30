package stores.palladium;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

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
