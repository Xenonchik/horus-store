package app.proc;

import persistence.sql.StoreSqlDAO;

/**
 * Created by serge on 20.09.17.
 */
public class NewGoodsProcessor {
  private StoreSqlDAO storeSqlDAO = new StoreSqlDAO();
  public void process() {
    storeSqlDAO.getStoresAsMap().keySet().forEach(
            store -> {




            }
    );
  }
}
