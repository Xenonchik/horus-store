package beholder.stores.palladium;

import beholder.parser.CategoryUrl;
import beholder.parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class PalladiumUrlPool extends CategoryUrlPool {

  private int page = 1;

  public PalladiumUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "?sort=price&direction=desc";
    }
    else tail = String.format("?sort=price&direction=desc&page=%d", page);
    page++;

    return tail;
  }

  @Override
  protected CategoryUrl getUrl(String urlHead) {
    return new PalladiumUrl(urlHead);
  }
}
