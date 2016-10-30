package stores.tehnos;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

/**
 * Done
 */
public class TehnosProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new TehnosParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new TehnosUrlPool(catUrl);
  }

  @Override
  public Long getDelay() {
    return 1100l;
  }
}
