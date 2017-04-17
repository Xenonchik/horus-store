package stores.fotos;

import parser.HtmlProductParser;

import stores.StoreManager;
import parser.UrlPool;

/**
 * Done
 */
public class FotosProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new FotosParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new FotosUrlPool(catUrl);
  }

  @Override
  public Long getDelay() {
    return 20000l;
  }
}
