package app.proc;

import java.util.Map;

import domain.Store;
import persistence.sql.PricesDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Blahblahblah
 */
public class DirectPricesProcessor {

  PricesDAO pricesDAO = new PricesDAO();

  StoreSqlDAO storeSqlDAO = new StoreSqlDAO();

  public void process() {
    Map<Long, Store> stores = storeSqlDAO.getStoresAsMapById();

    pricesDAO.updatePrices(stores);

  }
}
