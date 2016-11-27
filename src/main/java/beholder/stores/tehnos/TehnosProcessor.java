package beholder.stores.tehnos;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;
import beholder.stores.StoreManager;

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
