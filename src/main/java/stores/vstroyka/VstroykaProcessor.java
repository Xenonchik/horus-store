package stores.vstroyka;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class VstroykaProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new VstroykaParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new VstroykaUrlPool(catUrl);
  }
}
