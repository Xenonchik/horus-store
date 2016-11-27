package beholder.stores.deshevle;

import beholder.parser.CategoryUrl;
import beholder.parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class DeshevleUrlPool extends CategoryUrlPool {

  private int page = 1;

  public DeshevleUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "/rows-30.html";
    }
    else tail = String.format("//rows-30/page-%d.html/", page);
    page++;

    return tail;
  }

  @Override
  protected CategoryUrl getUrl(String urlHead) {
    return new DeshevleUrl(urlHead);
  }
}
