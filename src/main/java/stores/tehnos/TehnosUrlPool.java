package stores.tehnos;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class TehnosUrlPool extends CategoryUrlPool {

  private int page = 1;

  public TehnosUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "";
    }
    else tail = String.format("/%d0", page-1);
    page++;

    return tail;
  }
}
