package beholder.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import beholder.app.proc.StoreProcessor;
import beholder.conf.Config;
import beholder.domain.Store;
import beholder.persistence.sql.StoreSqlDAO;
import beholder.stores.StoreManager;

/**
 * Blahblahblah
 */
@Component
public class StoresService {

  @Autowired @Qualifier("storesConfig")
  private Config config;

  @Autowired
  private StoreSqlDAO storeSqlDAO;

  @Transactional
  public Set<StoreProcessor> getProcessors() {
    Map<String, Store> stores = storeSqlDAO.getStoresAsMap();
    Set<StoreProcessor> processors = new HashSet<>();

    for (Map.Entry<String, StoreManager> kv : config.getStoreConfigs().entrySet()) {
      if (stores.containsKey(kv.getKey())) {
        StoreProcessor sp = new StoreProcessor(kv.getValue());
        sp.setStore(stores.get(kv.getKey()));
        processors.add(sp);
      }
    }

    return processors;
  }
}