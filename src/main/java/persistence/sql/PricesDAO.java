package persistence.sql;

import java.util.Map;

import domain.Store;

/**
 * Blahblahblah
 */
public class PricesDAO extends SqlDAO {

  public void updatePrices(Map<Long, Store> stores) {
    beginTransaction();
    stores.keySet().forEach(key ->
            getSession().createSQLQuery("UPDATE prices " +
                "  SET " + stores.get(key).getName() + "=p.price" +
                "  FROM (SELECT g.id, price" +
                "  FROM public.goods as g" +
                "  JOIN products as p ON p.name = g." + stores.get(key).getName() +
                "  AND p.store = " + key + ") as p" +
                "  WHERE p.id=prices.id").executeUpdate()
    );
    endTransaction();

  }
}
