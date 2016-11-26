package domain;

import java.math.BigInteger;

/**
 * POJO for monitoring
 */
public class CategoryCheck {

  private Integer ps;
  private BigInteger pc;
  private BigInteger pcnt;
  private BigInteger phcnt;
  private Double diff;

  public Integer getPs() {
    return ps;
  }

  public void setPs(Integer ps) {
    this.ps = ps;
  }

  public BigInteger getPc() {
    return pc;
  }

  public void setPc(BigInteger pc) {
    this.pc = pc;
  }

  public BigInteger getPcnt() {
    return pcnt;
  }

  public void setPcnt(BigInteger pcnt) {
    this.pcnt = pcnt;
  }

  public BigInteger getPhcnt() {
    return phcnt;
  }

  public void setPhcnt(BigInteger phcnt) {
    this.phcnt = phcnt;
  }

  public Double getDiff() {
    return diff;
  }

  public void setDiff(Double diff) {
    this.diff = diff;
  }

  @Override
  public String toString() {
    return "CategoryCheck{" +
        "ps=" + ps +
        ", pc=" + pc +
        ", diff=" + diff +
        ", pcnt=" + pcnt +
        ", phcnt=" + phcnt +
        '}';
  }
}
