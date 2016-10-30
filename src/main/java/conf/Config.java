package conf;

import java.util.LinkedHashMap;

import stores.StoreManager;

/**
 * Created by serge on 4/26/16.
 */
public class Config {
  private LinkedHashMap<String, StoreManager> storeConfigs;

  public LinkedHashMap<String, StoreManager> getStoreConfigs() {
    return storeConfigs;
  }

  public void setStoreConfigs(LinkedHashMap<String, StoreManager> storeConfigs) {
    this.storeConfigs = storeConfigs;
  }
}
