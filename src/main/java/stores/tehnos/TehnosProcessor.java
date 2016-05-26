package stores.tehnos;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class TehnosProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new TehnosParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new TehnosUrlPool(catUrl);
  }

  @Override
  protected Long getDelay() {
    return 1100l;
  }
}
