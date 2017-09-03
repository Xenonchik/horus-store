package persistence.sql;

import java.math.BigInteger;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Store;

/**
 * Blahblahblah
 */
public class PricesDAO extends SqlDAO {
  final static Logger log = LoggerFactory.getLogger(PricesDAO.class);
  public void updatePrices(Map<Long, Store> stores) {
    beginTransaction();
    stores.keySet().forEach(key -> {
              getSession().createSQLQuery("UPDATE prices " +
                      "  SET " + stores.get(key).getName() + "=p.price" +
                      "  FROM (SELECT g.id, price" +
                      "  FROM public.goods as g" +
                      "  JOIN products as p ON p.name = g." + stores.get(key).getName() +
                      "  AND p.store = " + key + ") as p" +
                      "  WHERE p.id=prices.id").executeUpdate();
              Query q = getSession().createSQLQuery("SELECT count(*) as c" +
                      "  FROM public.goods as g" +
                      "  JOIN products as p ON p.name = g." + stores.get(key).getName() +
                      "  AND p.store = " + key);
              q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
              BigInteger count = ((Map<String, BigInteger>) q.list().iterator().next()).get("c");
              log.info("Aliases found for " + stores.get(key).getName() + ": " + count.intValue());

            }
    );
    endTransaction();

  }
}
