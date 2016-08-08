package persistence.sql;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO
 */
abstract public class SqlDAO {
  final static Logger log = LoggerFactory.getLogger(SqlDAO.class);

  protected Session getSession() {
    return HibernateUtils.getSessionFactory().getCurrentSession();
  }

  protected void beginTransaction() {
    HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
  }

  protected void endTransaction() {
    HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
  }

  public void closeSessionFactory() {
    HibernateUtils.getSessionFactory().close();
  }
}
