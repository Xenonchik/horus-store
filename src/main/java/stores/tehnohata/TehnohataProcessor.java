package stores.tehnohata;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class TehnohataProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new TehnohataParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new TehnohataUrlPool(catUrl);
  }
}
