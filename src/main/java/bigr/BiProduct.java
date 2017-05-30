package bigr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import domain.Product;

/**
 * Created by serge on 4/26/17.
 */
@DatabaseTable(tableName = "products")
public class BiProduct {
  @DatabaseField
  private String name;
  private String SKU;
  @DatabaseField
  private Long price;
  @DatabaseField
  private String url;
  @DatabaseField
  private String store;

  public static BiProduct fromProduct(Product product) {
    BiProduct biProduct = new BiProduct();
    biProduct.setName(product.getName());
    biProduct.setPrice(product.getPrice());
    biProduct.setUrl(product.getProductUrl());
    return biProduct;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSKU() {
    return SKU;
  }

  public void setSKU(String SKU) {
    this.SKU = SKU;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }
}
