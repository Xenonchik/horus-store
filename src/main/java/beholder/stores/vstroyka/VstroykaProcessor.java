package beholder.stores.vstroyka;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;
import beholder.stores.StoreManager;

/**
 * Done
 */
public class VstroykaProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new VstroykaParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new VstroykaUrlPool(catUrl);
  }
}
