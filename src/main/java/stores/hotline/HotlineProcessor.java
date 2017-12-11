package stores.hotline;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

/**
 * Done
 */
public class HotlineProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new HotlineParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new HotlineUrlPool(catUrl);
  }

  @Override
  public Long getDelay() {
    return 1500l;
  }
}
