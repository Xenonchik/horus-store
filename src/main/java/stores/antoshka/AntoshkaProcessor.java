package stores.antoshka;

import parser.HtmlProductParser;
import parser.UrlPool;
import stores.StoreManager;

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
    return 2000l;
  }
}
