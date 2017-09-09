package stores;

import parser.HtmlProductParser;
import parser.UrlPool;

/**
 * Blahblahblah
 */
public interface StoreManager {

  public HtmlProductParser getParcer();

  public UrlPool getUrlPool(String catUrl);

  public default Long getDelay() {
    return 4500l;
  }

  public default void setDelay(Long delay) {}
}
