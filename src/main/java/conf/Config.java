package conf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by serge on 4/26/16.
 */
public class Config {
  final private LinkedHashMap<String, StoreConf> storeConfigs = new LinkedHashMap<>();

  final private List<String> brands = new ArrayList<>();

  public LinkedHashMap<String, StoreConf> getStoreConfigs() {
    return storeConfigs;
  }


  public List<String> getBrands() {
    return brands;
  }
}
