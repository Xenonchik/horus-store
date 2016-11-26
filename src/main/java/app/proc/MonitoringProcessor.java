package app.proc;

import persistence.sql.ProductSqlDAO;

/**
 * Blahblahblah
 */
public class MonitoringProcessor {

  private final ProductSqlDAO dao = new ProductSqlDAO();
  public void process() {
    dao.monitor();
    dao.moveToHistory();
  }
}
