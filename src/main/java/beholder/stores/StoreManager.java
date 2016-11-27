package beholder.stores;

import beholder.parser.HtmlProductParser;
import beholder.parser.UrlPool;

/**
 * Blahblahblah
 */
public interface StoreManager {

  public HtmlProductParser getParcer();

  public UrlPool getUrlPool(String catUrl);

  public default Long getDelay() {
    return 4500l;
  }
}
