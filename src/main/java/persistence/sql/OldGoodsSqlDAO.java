package persistence.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import domain.OldGood;

/**
 * Created by serge on 5/30/16.
 */
public class OldGoodsSqlDAO extends SqlDAO {

  public void insert(List<OldGood> entities) {
    beginTransaction();
    for (OldGood good : entities) {
      getSession().saveOrUpdate(good);
    }

    endTransaction();
  }

  public List<OldGood> getGoods() {
    beginTransaction();
    Query query = getSession().createQuery("from Good");
    List<OldGood> list = query.list();
    endTransaction();
    return list;
  }

  public Long getGoodId(String model, String brand) {
    beginTransaction();
    Query query = getSession().createQuery("from Good WHERE brand = :brand AND model = :model");
    query.setParameter("model", model.trim());
    query.setParameter("brand", brand);

    List<OldGood> list = query.list();
    endTransaction();
    return list.get(0).getId();
  }

  public Map<String, Long> getIndexedGoods() {
    Map<String, Long> result = new HashMap<>();

    for(OldGood eg : getGoods()) {
      result.put(eg.getBrand().trim() + " " + eg.getModel().trim(), eg.getId());
    }

    return result;
  }
}
