package stores.v590;

import parser.Parser;
import parser.StoreProcessor;
import parser.UrlPool;

/**
 * Done
 */
public class V590Processor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new V590Parser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new V590UrlPool(catUrl);
  }
}
