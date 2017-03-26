package beholder.app.proc;

import org.springframework.stereotype.Component;

/**
 * Blahblahblah
 */
@Component
public abstract class StoreProcessorFactory {
  public abstract StoreProcessor getStoreProcessor();
}
