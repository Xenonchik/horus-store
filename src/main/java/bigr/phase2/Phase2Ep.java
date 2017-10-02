package bigr.phase2;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import domain.CatStore;
import domain.Product;
import domain.Store;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.hotline.HotlineProcessor;

/**
 * Blahblahblah
 */
public class Phase2Ep {

  final public static String DATA_FOLDER = "/opt/data/bigr/";

  final static Logger log = LoggerFactory.getLogger(Phase2Ep.class);

  private Long interval = 5000l;

  public Phase2Ep(String arg) {
    if(arg != null && !arg.equals(""))
      interval = Long.valueOf(arg);
  }

  public Phase2Ep() {}

  public static void main(String[] args) throws Exception {
    List<HotlineUrl> urls = new HotlineUrlsJson().readUrls();
    Phase2Ep phase = new Phase2Ep(args[0]);
    urls.forEach(
        phase::processURL
    );
  }

  public void processURL(HotlineUrl url) {
    // init
    Store store = new Store();
    store.setName("HOTLINE");
    StoreManager sm = new HotlineProcessor();
    sm.setDelay(interval);
    log.info("Parser delay: {}", sm.getDelay());

    CatStore testCat = new CatStore();
    testCat.setStore(store);
    testCat.setUrl(url.getUrl());

    // parsing
    List<Product> products = Lists.newArrayList();
    try {
      products = new CategoryProcessor().process(testCat, sm);
    } catch (InterruptedException e) {
      log.error("Something wrong with parsing");
      e.printStackTrace();
    }
    log.info("Products gathered. CatUrl: {} Products count {}", url.getUrl(), products.size());

    // write to json
    ProductsJson pj = new ProductsJson();
    try {
      pj.write(products, createCatJsonPath(url));
    } catch (IOException e) {
      log.error("Something wrong with writing");
      e.printStackTrace();
    }
  }

  private String createCatJsonPath(HotlineUrl url) {
    return Phase2Ep.DATA_FOLDER + "products/" + HotlineUrl.getName(url.getUrl()) + ".json";
  }

}
