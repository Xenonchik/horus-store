package stores.mobilluck;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class MobilluckProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new MobilluckParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new MobilluckUrlPool(catUrl);
  }
}
