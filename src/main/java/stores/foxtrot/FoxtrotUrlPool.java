package stores.foxtrot;

import parser.CategoryUrl;
import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class FoxtrotUrlPool extends CategoryUrlPool {

  private int page = 1;

  public FoxtrotUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "?size=60";
    }
    else tail = String.format("?size=60&page=%d", page);
    page++;

    return tail;
  }

  @Override
  protected CategoryUrl getUrl(String urlHead) {
    return new FoxtrotUrl(urlHead);
  }
}
