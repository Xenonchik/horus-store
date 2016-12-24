package beholder.app.proc;

import org.springframework.beans.factory.FactoryBean;

import beholder.stores.StoreManager;

/**
 * Blahblahblah
 */
public class ProcessorsFactory implements FactoryBean<StoreProcessor> {

  private StoreManager sm;

  @Override
  public StoreProcessor getObject() throws Exception {
    return null;
  }

  @Override
  public Class<?> getObjectType() {
    return StoreProcessor.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }
}
