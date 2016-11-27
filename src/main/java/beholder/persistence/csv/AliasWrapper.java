package beholder.persistence.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import beholder.domain.Alias;
import beholder.domain.csv.AliasCsv;
import beholder.persistence.export.EntityWrapper;
import beholder.persistence.sql.GoodsSqlDAO;

/**
 * Blahblahblah
 */
public class AliasWrapper implements EntityWrapper<AliasCsv, Alias> {
  GoodsSqlDAO goodsSqlDAO = new GoodsSqlDAO();

  @Override
  public List<Alias> wrapEntities(List<AliasCsv> entities) {
    List<Alias> result = new ArrayList<>();
    Map<String, Long> goods = goodsSqlDAO.getIndexedGoods();

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
