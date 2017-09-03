package app.proc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Store;
import persistence.sql.PricesDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Blahblahblah
 */
public class DirectPricesProcessor {

  final static Logger log = LoggerFactory.getLogger(DirectPricesProcessor.class);

  PricesDAO pricesDAO = new PricesDAO();

  StoreSqlDAO storeSqlDAO = new StoreSqlDAO();

  public void process() {
    Map<Long, Store> stores = storeSqlDAO.getStoresAsMapById();

    log.info("Start updating prices");
    pricesDAO.updatePrices(stores);
    log.info("Finished updating prices");

  }
}
