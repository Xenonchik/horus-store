package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.EmirGood;
import domain.Export;
import domain.Store;
import persistence.EmirDAO;
import persistence.ExportDAO;
import persistence.StoreDAO;

/**
 * Created by serge on 5/30/16.
 */
public class AliasProcessor {

  final static Logger log = LoggerFactory.getLogger(AliasProcessor.class);

  private final ExportDAO exportDAO = new ExportDAO();
  private final StoreDAO storeDAO = new StoreDAO();
  private final EmirDAO emirDAO = new EmirDAO();

  public void process() {
    List<EmirGood> emirGoods = emirDAO.getGoods();
    List<Store> stores = storeDAO.getStores();
    Map<EmirGood, List<StoreExpWrapper>> result = new HashMap<>();
    for(EmirGood emirGood : emirGoods) {
      List<StoreExpWrapper> sews = new ArrayList<>();
      Export export = new Export();
      export.setCategory(emirGood.getCategory().longValue());
      for(Store store : stores) {
        StoreExpWrapper sew = new StoreExpWrapper();
        export.setStore(store.getName());
        sew.store = store;
        for(Export exp : exportDAO.getExport(export)) {
          int levenstein = StringUtils.getLevenshteinDistance(exp.getModel(), emirGood.getModel());
          if(levenstein < 2) {
            log.info(emirGood.getModel() + " - " + exp.getModel() + " : " + levenstein);
          }
          if(levenstein < 2) {
            ExpLevWrapper elw = new ExpLevWrapper(exp, levenstein);
            sew.exports.add(elw);
          }
        }
        Collections.sort(sew.exports, new Comparator<ExpLevWrapper>() {
          @Override
          public int compare(ExpLevWrapper o1, ExpLevWrapper o2) {
            if (o1.levenstein < o2.levenstein) return -1;
            if (o1.levenstein > o2.levenstein) return 1;
            return 0;
          }
        });
        if(sew.exports.size() > 0) {
          sews.add(sew);
        }

      }
      result.put(emirGood, sews);
    }

    return;

  }


  class ExpLevWrapper{
    Export export;
    Integer levenstein;
    public ExpLevWrapper(Export export, Integer levenstein) {
      this.export = export;
      this.levenstein = levenstein;
    }
  }

  class StoreExpWrapper {
    Store store;
    List<ExpLevWrapper> exports = new ArrayList<>();
  }


}
