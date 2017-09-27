package app.proc;

import persistence.sql.StoreSqlDAO;
import persistence.sql.SupportSqlDAO;

/**
 * Created by serge on 20.09.17.
 */
public class NewGoodsProcessor {
  private StoreSqlDAO storeSqlDAO = new StoreSqlDAO();
  private SupportSqlDAO supportSqlDAO = new SupportSqlDAO();
  public void process() {
    storeSqlDAO.getStoresAsMap().keySet().forEach(
        supportSqlDAO::updateAliasesByStore
    );
  }
}
