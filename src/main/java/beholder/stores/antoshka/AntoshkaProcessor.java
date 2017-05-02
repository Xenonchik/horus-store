package beholder.stores.antoshka;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;
import beholder.stores.StoreManager;

/**
 * Done
 */
public class AntoshkaProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new AntoshkaParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new AntoshkaUrlPool(catUrl);
  }

  @Override
  public Long getDelay() {
    return 5000l;
  }
}
