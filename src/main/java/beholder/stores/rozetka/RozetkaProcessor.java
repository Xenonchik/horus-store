package beholder.stores.rozetka;

import beholder.parser.HtmlProductParser;

import beholder.stores.StoreManager;
import beholder.parser.UrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class RozetkaProcessor implements StoreManager {
  @Override
  public HtmlProductParser getParcer() {
    return new RozetkaParser();
  }

  @Override
  public UrlPool getUrlPool(String catUrl) {
    return new RozetkaUrlPool(catUrl);
  }

  @Override
  public Long getDelay() {
    return 5000l;
  }
}
