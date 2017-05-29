package bigr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Blahblahblah
 */
@DatabaseTable(tableName = "skus")
public class Sku {
  @DatabaseField
  private String name;
  @DatabaseField
  private String store;
  @DatabaseField
  private String sku;

  public static Sku fromProduct(BiProduct product) {
    Sku sku = new Sku();
    sku.setName(product.getName());
    sku.setStore(product.getStore());
    return sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
}
