package stores.fotos;

import parser.Parser;
import parser.StoreProcessor;
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
}
