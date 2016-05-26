package stores.palladium;

import parser.CategoryUrl;
import parser.CategoryUrlPool;

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
      tail = "";
    }
    else tail = String.format("?page=%d", page);
    page++;

    return tail;
  }

  @Override
  protected CategoryUrl getUrl(String urlHead) {
    return new PalladiumUrl(urlHead);
  }
}
