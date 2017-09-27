package app.proc;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Alias;
import domain.Export;
import domain.OldGood;
import persistence.csv.AliasCsvDAO;
import persistence.sql.ExportSqlDAO;
import persistence.sql.OldGoodsSqlDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Blahblahblah
 */
public class PricesProcessor {

  final static Logger log = LoggerFactory.getLogger(PricesProcessor.class);

  protected String filename = "/opt/data/prices.csv";
  private OldGoodsSqlDAO exemplarDAO = new OldGoodsSqlDAO();
  private ExportSqlDAO exportSqlDAO = new ExportSqlDAO();
  private StoreSqlDAO storeDAO = new StoreSqlDAO();

  public void process() throws IOException {
    AliasCsvDAO aliasCsvDAO = new AliasCsvDAO(storeDAO.getStores(), filename);

    // 1. выгребаем все продукты эмира
    List<OldGood> goods = exemplarDAO.getGoods();
    // 2. для каждого продукта получаем список алиасов

    for(OldGood good : goods) {
      for(Alias alias : good.getStoredAliases()) {
        if(alias.getAlias().trim().length() > 0){
          Export export = exportSqlDAO.getExportForAlias(alias);
          if(export != null) {
            good.getAliases().put(alias.getStore(), export);
          } else {
           // log.warn("No export for good " + good.getId() + " and alias " + alias.getAlias());
          }
        }
      }
      aliasCsvDAO.savePrice(good);
    }
  }

}
