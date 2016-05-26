package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.StoreProcessor;

/**
 * Created by serge on 5/11/16.
 */
public class StoreRunner implements Runnable {
  final static Logger log = LoggerFactory.getLogger(StoreRunner.class);

  private Thread t;
  private final String threadName;
  private final StoreProcessor processor;

  public StoreRunner(StoreProcessor processor){
    this.processor = processor;
    threadName = processor.getName();
  }

  public void run() {
    log.info("Running " + threadName);
    try {
      processor.process();
    } catch (InterruptedException e) {
      log.info("Thread " + threadName + " interrupted.");
    }
    log.info("Thread " + threadName + " exiting.");
  }

  public void start()
  {
    if(t == null)
    {
      t = new Thread(this, threadName);
      t.start();
    }
  }

}
