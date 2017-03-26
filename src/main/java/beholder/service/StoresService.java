package beholder.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import beholder.app.proc.StoreProcessor;
import beholder.app.proc.StoreProcessorFactory;
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
  private ApplicationContext appContext;

  @Autowired
  private StoreSqlDAO storeSqlDAO;

  @Autowired @Qualifier("storeProcessorFactory")
  private StoreProcessorFactory factory;

  @Transactional
  public Set<StoreProcessor> getProcessors() {
    Map<String, Store> stores = storeSqlDAO.getStoresAsMap();
    Set<StoreProcessor> processors = Sets.newHashSet();


    for (Map.Entry<String, StoreManager> kv : config.getStoreConfigs().entrySet()) {
      if (stores.containsKey(kv.getKey())) {
        StoreProcessor sp = factory.getStoreProcessor();
        sp.setStoreManager(kv.getValue());
        sp.setStore(stores.get(kv.getKey()));
        processors.add(sp);
      }
    }

    return processors;
  }
}
