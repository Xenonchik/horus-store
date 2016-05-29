package persistence;

import org.hibernate.Session;

/**
 * Hibernate DAO
 */
abstract public class DAO {
  protected static Session session;

  protected void beginTransaction() {
    session = HibernateUtils.getSessionFactory().getCurrentSession();
    session.beginTransaction();
  }

  protected void endTransaction() {
    session.getTransaction().commit();
  }
}
