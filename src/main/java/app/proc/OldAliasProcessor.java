package app.proc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Alias;
import domain.Export;
import domain.OldGood;
import domain.Store;
import persistence.sql.AliasSqlDAO;
import persistence.sql.ExportSqlDAO;
import persistence.sql.OldGoodsSqlDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Many goods in different stores have
 * different names
 */
@Deprecated
public class OldAliasProcessor {

  final static Logger log = LoggerFactory.getLogger(OldAliasProcessor.class);

  protected final ExportSqlDAO exportDAO = new ExportSqlDAO();
  protected final OldGoodsSqlDAO exemplarDAO = new OldGoodsSqlDAO();
  protected AliasSqlDAO aliasDAO = new AliasSqlDAO();


  public void process() throws IOException {

    List<OldGood> goods = exemplarDAO.getGoods();
    List<Store> stores = new StoreSqlDAO().getStores();

    int i = 0;
    for (OldGood good : goods) {

      for (Store store : stores) {
        List<Export> exports;
        if(good.getCategory().getId().equals(0l)) {
          exports = exportDAO.getExportByStore(store);
        } else {
          exports = exportDAO.getExportByCategory(store, good.getCategory());
        }

        for (Export exp : exports) {
          if (isAlias(good, exp)) {
            // log.info("Alias for " + exemplarGood.getModel() + " : " + exp.getFullName());
            good.getAliases().put(store.getName(), exp);
            good.incStoreCount();
            break;
          }
        }

        if (good.getAliases().get(store.getName()) == null) {
          // add export with empty name
          good.getAliases().put(store.getName(), new Export());
        }
      }

      saveAlias(good);

      i++;
      if(i%100 == 0) {
        log.info(i + " goods processed");
      }
    }

    log.info("Aliaces setting finished. Goods processed: " + goods.size());

  }


  private void saveAlias(OldGood eg) {
    List<Alias> aliases = new ArrayList<>();

    for (Map.Entry<String, Export> kv : eg.getAliases().entrySet()) {
      if (kv.getValue().getId() != null) {
        Alias alias = new Alias();
        alias.setGood(eg.getId());
        alias.setStore(kv.getKey());
        alias.setAlias(kv.getValue().getFullName());
        aliases.add(alias);
      }
    }
    aliasDAO.updateSmart(aliases);

  }

  private boolean isAlias(OldGood good, Export exp) {

    String emirName = good.getModel();
    String exportName = exp.getModel();

    //1. Clean all whitespaces
    emirName = emirName.replaceAll("\\s+", "").toUpperCase();
    exportName = exportName.replaceAll("\\s+", "").toUpperCase();

    // 2. Check equality
    if (emirName.equals(exportName)) {
      if (good.getBrand().toUpperCase().equals(exp.getBrand()))
        return true;
    }

    //3. Check is one subpart of another
//    if (emirName.contains(exportName)) {
//      if (good.getBrand().toUpperCase().equals(exp.getBrand()))
//        return true;
//    }
//    if (exportName.contains(emirName)) {
//      if (good.getBrand().toUpperCase().equals(exp.getBrand()))
//        return true;
//    }

    return false;
  }

}
