package stores.mobilluck;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class MobilluckUrlPool extends CategoryUrlPool {

  private int page = 1;

  public MobilluckUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "";
    }
    else tail = String.format("pages_%d_15.html", page);
    page++;

    return tail;
  }
}
