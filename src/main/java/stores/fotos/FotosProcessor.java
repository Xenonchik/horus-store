package stores.fotos;

import parser.Parser;
import app.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class FotosProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new FotosParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new FotosUrlPool(catUrl);
  }

  @Override
  protected Long getDelay() {
    return 5000l;
  }
}
