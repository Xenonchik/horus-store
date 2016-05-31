package stores.palladium;

import parser.Parser;
import app.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class PalladiumProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new PalladiumParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new PalladiumUrlPool(catUrl);
  }
}
