package persistence;

import java.util.List;

import domain.Alias;

/**
 * Created by serge on 6/27/16.
 */
public class AliasDAO extends DAO {

  public void insert(Alias alias) {
    beginTransaction();
    getSession().save(alias);
    endTransaction();
  }

  public void insert(List<Alias> aliases) {
    beginTransaction();
    for(Alias alias : aliases) {
      getSession().save(alias);
    }
    endTransaction();
  }

}
