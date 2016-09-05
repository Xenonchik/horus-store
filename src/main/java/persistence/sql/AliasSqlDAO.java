package persistence.sql;

import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Alias;
import persistence.export.EntityManager;

/**
 * Created by serge on 6/27/16.
 */
public class AliasSqlDAO extends SqlDAO implements EntityManager<Alias> {

  final static Logger log = LoggerFactory.getLogger(AliasSqlDAO.class);

  public void insert(Alias alias) {
    beginTransaction();
    getSession().save(alias);
    endTransaction();
  }

  public void insert(List<Alias> aliases) {
    beginTransaction();
    int i = 0;
    for(Alias alias : aliases) {
      try {
        getSession().save(alias);
        i++;
        if(i%1000 == 0) {
          log.info(i + " rows saved");
        }
      }
      catch (NonUniqueObjectException e) {
        log.error(" " + alias.getGood() + " " + alias.getStore() + " " + i);
      }
    }
    endTransaction();
  }

  @Override
  public List<Alias> getEntities() {
    beginTransaction();

    Query query = getSession().createQuery("from Alias");
    List<Alias> list = query.list();

    endTransaction();

    return list;
  }

  public void updateSmart(List<Alias> aliases) {
    beginTransaction();
    int i = 0;
    for(Alias alias : aliases) {
      try {
        Alias exemplar = get(alias);
        if(exemplar.getAlias().trim().length() == 0) {
          exemplar.setAlias(alias.getAlias());
          getSession().update(exemplar);
          log.info("Updated " + alias.getGood() + " " + alias.getStore());
        }
      }
      catch (NonUniqueObjectException e) {
        log.error(" " + alias.getGood() + " " + alias.getStore() + " " + i);
      }
    }
    endTransaction();
  }

  public Alias get(Alias alias) {
    Query query = getSession().createQuery("from Alias WHERE good = :good AND store = :store");
    query.setParameter("good", alias.getGood());
    query.setParameter("store", alias.getStore());

    return (Alias) query.list().get(0);
  }

  @Override
  public void saveEntitites(List<Alias> entities) {
    insert(entities);
  }
}
