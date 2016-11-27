package beholder.stores.tehnohata;

import beholder.parser.CategoryUrl;
import beholder.parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class TehnohataUrlPool extends CategoryUrlPool {

  private int page = 1;

  public TehnohataUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "?on_page=100";
    }
    else tail = String.format("?page=%d&on_page=100", page);
    page++;

    return tail;
  }

  @Override
  protected CategoryUrl getUrl(String urlHead) {
    return new TehnohataUrl(urlHead);
  }
}
