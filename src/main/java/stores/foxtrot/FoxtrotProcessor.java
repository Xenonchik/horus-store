package stores.foxtrot;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class FoxtrotProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new FoxtrotParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new FoxtrotUrlPool(catUrl);
  }
}
