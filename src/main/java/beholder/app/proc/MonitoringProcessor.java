package beholder.app.proc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import beholder.service.ProductMonitoringService;

/**
 * Blahblahblah
 */
@Component
public class MonitoringProcessor {

  @Autowired
  private ProductMonitoringService productMonitoringService;
  public void process() {
    productMonitoringService.monitor();
    productMonitoringService.moveToHistory();
  }
}
