import org.junit.Test;

import app.proc.MonitoringProcessor;
import persistence.sql.ProductSqlDAO;

/**
 * Blahblahblah
 */
public class TestMonitor {

  @Test
  public void testMonitorDao() {
    ProductSqlDAO productSqlDAO = new ProductSqlDAO();
    productSqlDAO.monitor();
  }

  @Test
  public void testProcessor() {
    MonitoringProcessor mp = new MonitoringProcessor();
    mp.process();
  }
}
