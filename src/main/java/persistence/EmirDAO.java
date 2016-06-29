package persistence;

import java.util.List;

import org.hibernate.Query;

import domain.EmirGood;

/**
 * Created by serge on 5/30/16.
 */
public class EmirDAO extends DAO {

  public void insert(List<EmirGood> entities) {
    beginTransaction();
    for (EmirGood emirGood : entities) {

    }

    endTransaction();
  }

  public List<EmirGood> getGoods() {
    beginTransaction();
    Query query = getSession().createQuery("from EmirGood");
    List<EmirGood> list = query.list();
    endTransaction();
    return list;
  }
}
