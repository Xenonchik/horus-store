package conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by serge on 5/11/16.
 */
public class StoreRunner implements Runnable {
  final static Logger log = LoggerFactory.getLogger(StoreRunner.class);

  private Thread t;
  private String threadName;
  private StoreConf storeConf;

  public StoreRunner(StoreConf storeConf, String storeName){
    this.storeConf = storeConf;
    threadName = storeName;
  }

  public void run() {
    log.info("Running " + threadName);
    try {
      for(String catUrl : storeConf.getCatUrls()) {
        storeConf.getStoreProcessor().processCategory(catUrl);
      }
    } catch (InterruptedException e) {
      log.info("Thread " + threadName + " interrupted.");
    }
    log.info("Thread " + threadName + " exiting.");
  }

  public void start ()
  {
    if (t == null)
    {
      t = new Thread (this, threadName);
      t.start ();
    }
  }

}
