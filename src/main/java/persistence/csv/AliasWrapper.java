package persistence.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import domain.Alias;
import domain.csv.AliasCsv;
import persistence.export.EntityWrapper;
import persistence.sql.ExemplarSqlDAO;

/**
 * Blahblahblah
 */
public class AliasWrapper implements EntityWrapper<AliasCsv, Alias> {
  ExemplarSqlDAO exemplarSqlDAO = new ExemplarSqlDAO();

  @Override
  public List<Alias> wrapEntities(List<AliasCsv> entities) {
    List<Alias> result = new ArrayList<>();
    Map<String, Long> goods = exemplarSqlDAO.getIndexedGoods();

    for(AliasCsv aliasCsv : entities) {
      for(String store : aliasCsv.getStoreAliases().keySet()) {
        Alias alias = new Alias();
        alias.setAlias(aliasCsv.getStoreAliases().get(store));
        alias.setStore(store);
        alias.setGood(goods.get(aliasCsv.getBrand() + " " + aliasCsv.getModel().trim()));
        result.add(alias);
      }
    }

    return result;
  }
}
