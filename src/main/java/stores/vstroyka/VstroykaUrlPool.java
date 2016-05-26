package stores.vstroyka;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class VstroykaUrlPool extends CategoryUrlPool {

  private int page = 1;

  public VstroykaUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "/perPage/50";
    }
    else tail = String.format("/page/%d", page);
    page++;

    return tail;
  }
}
