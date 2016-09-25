package conf;

import java.util.LinkedHashMap;

import app.StoreProcessor;

/**
 * Created by serge on 4/26/16.
 */
public class Config {
  private LinkedHashMap<String, StoreProcessor> storeConfigs;

  public LinkedHashMap<String, StoreProcessor> getStoreConfigs() {
    return storeConfigs;
  }

  public void setStoreConfigs(LinkedHashMap<String, StoreProcessor> storeConfigs) {
    this.storeConfigs = storeConfigs;
  }
}
