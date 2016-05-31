package persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO
 */
abstract public class DAO {
  final static Logger log = LoggerFactory.getLogger(DAO.class);

  protected Session getSession() {
    return HibernateUtils.getSessionFactory().getCurrentSession();
  }

  protected void beginTransaction() {
    HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
  }

  protected void endTransaction() {
    HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
  }
}
