package domain.csv;

import java.util.HashMap;
import java.util.Map;

import domain.Entity;

/**
 * Blahblahblah
 */
public class AliasCsv implements Entity {

  private String brand;

  private String model;

  private Map<String, String> storeAliases = new HashMap<>();

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Map<String, String> getStoreAliases() {
    return storeAliases;
  }

  public void setStoreAliases(Map<String, String> storeAliases) {
    this.storeAliases = storeAliases;
  }
}
