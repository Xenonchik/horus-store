package beholder.persistence.sql;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import beholder.domain.Brand;
import beholder.domain.Useragent;
import beholder.persistence.SqlDAO;

/**
 * DAO for support entities - brands, useragents, etc
 */
@Component
public class SupportSqlDAO extends SqlDAO {

  final static Logger log = LoggerFactory.getLogger(SupportSqlDAO.class);

  public List<Useragent> getUseragents() {
    Query query = getSession().createQuery("from Useragent");
    List<Useragent> list = query.list();
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
