package stores.vstroyka;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

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
