package persistence;

import java.util.List;

import org.hibernate.Query;

import domain.Brand;
import domain.Useragent;

/**
 * DAO for support entities - brands, useragents, etc
 */
public class SupportDAO extends DAO {

  public List<Useragent> getUseragents() {
    beginTransaction();

    Query query = session.createQuery("from Useragent");
    List<Useragent> list = query.list();

    endTransaction();

    return list;
  }

  public List<Brand> getBrands() {
    beginTransaction();

    Query query = session.createQuery("from Brand");
    
    List<Brand> list = query.list();

    endTransaction();

    return list;
  }
}
