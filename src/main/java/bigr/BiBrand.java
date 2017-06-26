package bigr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import domain.Product;

/**
 * Created by serge on 4/26/17.
 */
@DatabaseTable(tableName = "brands")
public class BiBrand {

  public BiBrand() {}
  public BiBrand(String brand) {
    this.brand = brand;
  }

  @DatabaseField
  private String brand;

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }
}
