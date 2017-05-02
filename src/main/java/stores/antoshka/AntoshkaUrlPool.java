package stores.antoshka;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class AntoshkaUrlPool extends CategoryUrlPool {

  private int page = 1;

  public AntoshkaUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "";
    }
    else tail = String.format("?p=%d", page);
    page++;

    return tail;
  }
}
