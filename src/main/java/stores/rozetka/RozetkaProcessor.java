package stores.rozetka;

import parser.Parser;
import app.StoreProcessor;
import parser.UrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class RozetkaProcessor extends StoreProcessor {
  @Override
  protected Parser getParcer() {
    return new RozetkaParser();
  }

  @Override
  protected UrlPool getUrlPool(String catUrl) {
    return new RozetkaUrlPool(catUrl);
  }

  @Override
  protected Long getDelay() {
    return 5000l;
  }
}
