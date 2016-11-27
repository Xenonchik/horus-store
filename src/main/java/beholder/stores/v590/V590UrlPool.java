package beholder.stores.v590;

import beholder.parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class V590UrlPool extends CategoryUrlPool {

  private int page = 1;

  public V590UrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "";
    }
    else tail = String.format("/page/%d", page);
    page++;

    return tail;
  }
}
