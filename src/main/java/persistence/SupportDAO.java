package persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Brand;
import domain.Useragent;

/**
 * DAO for support entities - brands, useragents, etc
 */
public class SupportDAO extends DAO {

  final static Logger log = LoggerFactory.getLogger(SupportDAO.class);

  public List<Useragent> getUseragents() {
    beginTransaction();

    //log.info(session.toString());

    Query query = getSession().createQuery("from Useragent");
    List<Useragent> list = query.list();

    endTransaction();

    return list;
  }

  public List<Brand> getBrands() {
    beginTransaction();

    Query query = getSession().createQuery("from Brand");
    
    List<Brand> list = query.list();

    endTransaction();

    return list;
  }
}
