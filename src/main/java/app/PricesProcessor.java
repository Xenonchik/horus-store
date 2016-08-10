package app;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import domain.Alias;
import domain.Export;
import domain.Good;
import persistence.sql.ExportSqlDAO;
import persistence.sql.GoodsSqlDAO;

/**
 * Blahblahblah
 */
public class PricesProcessor {

  protected String filename = "src/main/resources/data/prices.csv";
  private GoodsSqlDAO exemplarDAO = new GoodsSqlDAO();
  private ExportSqlDAO exportSqlDAO = new ExportSqlDAO();

  public void process() throws IOException {
    //init();

    // 1. выгребаем все продукты эмира
    List<Good> goods = exemplarDAO.getGoods();
    // 2. для каждого продукта получаем список алиасов

    for(Good good : goods) {
      for(Alias alias : good.getStoredAliases()) {
        if(alias.getAlias().trim().length() > 0){
          Export export = exportSqlDAO.getExportForAlias(alias);
          if(export == null) {

          }
        }
      }
    }

    // 3. для каждого алиаса ищем соответствующую цену
  }

}
