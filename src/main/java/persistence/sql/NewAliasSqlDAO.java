package persistence.sql;

import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.NewAlias;
import persistence.export.EntityManager;

/**
 * Created by serge on 6/27/16.
 */
public class NewAliasSqlDAO extends SqlDAO implements EntityManager<NewAlias> {

  final static Logger log = LoggerFactory.getLogger(NewAliasSqlDAO.class);

  public void insert(NewAlias alias) {
    beginTransaction();
    getSession().saveOrUpdate(alias);
    endTransaction();
  }

  public void insert(List<NewAlias> aliases) {
    beginTransaction();
    int i = 0;
    for(NewAlias alias : aliases) {
      try {
        getSession().save(alias);
        i++;
        if(i%1000 == 0) {
          log.info(i + " rows saved");
        }
      }
      catch (NonUniqueObjectException e) {
        e.printStackTrace();
      }
    }
    endTransaction();
  }

  @Override
  public List<NewAlias> getEntities() {
    beginTransaction();

    Query query = getSession().createQuery("from NewAlias");
    List<NewAlias> list = query.list();

    endTransaction();

    return list;
  }


  @Override
  public void saveEntitites(List<NewAlias> entities) {
    beginTransaction();
    entities.forEach(
        alias -> getSession().saveOrUpdate(alias)
    );
    endTransaction();
  }
}
