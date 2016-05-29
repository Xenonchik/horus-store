package conf;

import java.util.LinkedHashMap;

/**
 * Created by serge on 4/26/16.
 */
public class Config {
  final private LinkedHashMap<String, StoreConf> storeConfigs = new LinkedHashMap<>();

  public LinkedHashMap<String, StoreConf> getStoreConfigs() {
    return storeConfigs;
  }

}
