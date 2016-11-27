package beholder.app.store;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beholder.app.proc.StoreProcessor;

/**
 * Created by serge on 5/11/16.
 */
public class StoreRunner implements Runnable {
  final static Logger log = LoggerFactory.getLogger(StoreRunner.class);

  private final String threadName;
  private final StoreProcessor processor;
  private final CountDownLatch latch;

  public StoreRunner(StoreProcessor processor, CountDownLatch latch){
    this.processor = processor;
    this.latch = latch;
    threadName = processor.getName();
  }

  public void run() {
    log.info("Running " + threadName);
    try {
      processor.process();
      latch.countDown();
    } catch (InterruptedException e) {
      log.info("Thread " + threadName + " interrupted.");
    }
    log.info("Thread " + threadName + " exiting.");
  }
}
