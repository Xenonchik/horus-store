package persistence;

import java.util.List;

import org.hibernate.Query;

import domain.ExemplarGood;

/**
 * Created by serge on 5/30/16.
 */
public class ExemplarDAO extends DAO {

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
}
