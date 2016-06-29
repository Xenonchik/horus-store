package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="aliases")
@IdClass(Alias.AliasPK.class)
public class Alias {


  @Column(name = "good")
  private Long good;

  @Id
  @Column(name = "store")
  private String store;

  @Id
  @Column(name = "alias")
  private String alias;

  public Long getGood() {
    return good;
  }

  public void setGood(Long good) {
    this.good = good;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }


  public static class AliasPK implements Serializable {

    private String store;
    private String alias;

    public AliasPK() {}

    public AliasPK(String store, String alias) {
      this.setStore(store);
      this.setAlias(alias);
    }

    public String getStore() {
      return store;
    }

    public void setStore(String store) {
      this.store = store;
    }

    public String getAlias() {
      return alias;
    }

    public void setAlias(String alias) {
      this.alias = alias;
    }
  }
}
