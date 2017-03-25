package beholder.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Hibernate DAO
 */
@Repository
abstract public class SqlDAO {
  final static Logger log = LoggerFactory.getLogger(SqlDAO.class);

  @Autowired @Qualifier("sessionFactory")
  private SessionFactory sessionFactory;

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  protected void beginTransaction() {
    Session session = sessionFactory.getCurrentSession();
    log.info(session.toString());
    session.beginTransaction();
  }

  protected void endTransaction() {
    sessionFactory.getCurrentSession().getTransaction().commit();
  }

  public void closeSessionFactory() {
    sessionFactory.close();
  }

  public <T> List<T> customSQLQueryList(String sql, Class<T> clazz) {
    Query query = getSession().createSQLQuery(sql)
        .setResultTransformer(new AliasToBeanResultTransformer(clazz));

    return query.list();
  }

  public void customSQLQuery(String sql) {
    Query query = getSession().createSQLQuery(sql);
    query.executeUpdate();
  }
}
