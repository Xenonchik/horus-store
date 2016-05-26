package stores.deshevle;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class DeshevleProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new DeshevleParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new DeshevleUrlPool(catUrl);
  }
}
