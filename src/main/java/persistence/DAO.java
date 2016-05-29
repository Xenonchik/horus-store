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

  //protected static Session session;

  protected Session getSession() {
    return HibernateUtils.getSessionFactory().getCurrentSession();
  }

  protected void beginTransaction() {
  //  session = HibernateUtils.getSessionFactory().getCurrentSession();
    Transaction tx = HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
   // log.info("Begin " + Thread.currentThread().toString() + " " + tx.toString() + " " + this.toString());
  }

  protected void endTransaction() {
    //Transaction tx = session.getTransaction();
   // log.info("End" + Thread.currentThread().toString() + " " + tx.toString() + " " + this.toString());
    HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
  }
}
