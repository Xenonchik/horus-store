package beholder.stores.mobilluck;

import beholder.parser.HtmlProductParser;

import beholder.stores.StoreManager;
import beholder.parser.UrlPool;

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
