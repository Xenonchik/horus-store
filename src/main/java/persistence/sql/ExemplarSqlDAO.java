package persistence.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import domain.ExemplarGood;

/**
 * Created by serge on 5/30/16.
 */
public class ExemplarSqlDAO extends SqlDAO {

  public void insert(List<ExemplarGood> entities) {
    beginTransaction();
    for (ExemplarGood exemplarGood : entities) {

    }

    endTransaction();
  }

  public List<ExemplarGood> getGoods() {
    beginTransaction();
    Query query = getSession().createQuery("from ExemplarGood");
    List<ExemplarGood> list = query.list();
    endTransaction();
    return list;
  }

  public Long getGoodId(String model, String brand) {
    beginTransaction();
    Query query = getSession().createQuery("from ExemplarGood WHERE brand = :brand AND model = :model");
    query.setParameter("model", model.trim());
    query.setParameter("brand", brand);

    List<ExemplarGood> list = query.list();
    endTransaction();
    return list.get(0).getId();
  }

  public Map<String, Long> getIndexedGoods() {
    Map<String, Long> result = new HashMap<>();

    for(ExemplarGood eg : getGoods()) {
      result.put(eg.getBrand() + " " + eg.getModel().trim(), eg.getId());
    }

    return result;
  }
}
