package app.store;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.proc.StoreProcessor;

/**
 * Encapsulate logic of concurrent parsing
 */
public class StoreParsersExecutor {

  final static Logger log = LoggerFactory.getLogger(StoreParsersExecutor.class);

  public void parseAll(Set<StoreProcessor> processors) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(processors.size());
    ExecutorService executor = Executors.newFixedThreadPool(processors.size());

    for (StoreProcessor processor : processors) {
      executor.submit(new StoreRunner(processor, latch));
    }

    latch.await();
    log.info("Parsing completed.");
  }
}
