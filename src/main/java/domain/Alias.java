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
public class Alias implements Entity {

  @Id
  @Column(name = "good")
  private Long good;

  @Id
  @Column(name = "store")
  private String store;


  @Column(name = "alias")
  private String alias;

  private Integer source;

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

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
  }


  public static class AliasPK implements Serializable {

    private String store;
    private Long good;

    public AliasPK() {}

    public AliasPK(String store, Long good) {
      this.setStore(store);
      this.setGood(good);
    }

    public String getStore() {
      return store;
    }

    public void setStore(String store) {
      this.store = store;
    }

    public Long getGood() {
      return good;
    }

    public void setGood(Long good) {
      this.good = good;
    }
  }
}
