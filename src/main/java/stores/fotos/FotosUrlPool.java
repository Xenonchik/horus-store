package stores.fotos;

import parser.CategoryUrlPool;

/**
 * Created by serge on 4/26/16.
 */
public class FotosUrlPool extends CategoryUrlPool {

  private int page = 1;

  public FotosUrlPool(String catUrl) {
    super(catUrl);
  }

  @Override
  protected String calculateTail() {
    String tail;
    if (page == 1) {
      tail = "";
    }
    else tail = String.format("/%d/", page);
    page++;

    return tail;
  }
}
