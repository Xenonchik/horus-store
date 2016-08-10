package persistence.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import domain.Good;

/**
 * Created by serge on 5/30/16.
 */
public class GoodsSqlDAO extends SqlDAO {

  public void insert(List<Good> entities) {
    beginTransaction();
    for (Good good : entities) {

    }

    endTransaction();
  }

  public List<Good> getGoods() {
    beginTransaction();
    Query query = getSession().createQuery("from Good");
    List<Good> list = query.list();
    endTransaction();
    return list;
  }

  public Long getGoodId(String model, String brand) {
    beginTransaction();
    Query query = getSession().createQuery("from Good WHERE brand = :brand AND model = :model");
    query.setParameter("model", model.trim());
    query.setParameter("brand", brand);

    List<Good> list = query.list();
    endTransaction();
    return list.get(0).getId();
  }

  public Map<String, Long> getIndexedGoods() {
    Map<String, Long> result = new HashMap<>();

    for(Good eg : getGoods()) {
      result.put(eg.getBrand() + " " + eg.getModel().trim(), eg.getId());
    }

    return result;
  }
}
