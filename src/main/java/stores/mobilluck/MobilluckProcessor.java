package stores.mobilluck;

import parser.HtmlProductParser;

import stores.StoreManager;
import parser.UrlPool;

/**
 * Done
 */
public class MobilluckProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new MobilluckParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new MobilluckUrlPool(catUrl);
  }
}
