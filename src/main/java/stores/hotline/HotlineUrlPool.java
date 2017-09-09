package stores.hotline;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class HotlineUrlPool extends CategoryUrlPool {

  private int page = 0;

  public HotlineUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 0) {
      tail = "";
    }
    else tail = String.format("?p=%d&catmode=tiles", page);
    page++;

    return tail;
  }
}
