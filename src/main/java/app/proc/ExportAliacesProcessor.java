package app.proc;

import java.io.IOException;
import java.util.List;

import domain.Good;
import persistence.csv.AliasCsvDAO;
import persistence.csv.AliasWrapper;
import persistence.export.AliacCsvEM;
import persistence.export.CsvSqlExport;
import persistence.export.EntityManager;
import persistence.export.EntityWrapper;
import persistence.sql.AliasSqlDAO;
import persistence.sql.GoodsSqlDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Blahblahblah
 */
public class ExportAliacesProcessor {

  StoreSqlDAO storeSqlDAO = new StoreSqlDAO();

  public void process() {
    EntityManager csvEM = new AliacCsvEM(storeSqlDAO.getStoreNames());
    EntityManager sqlEM = new AliasSqlDAO();
    EntityWrapper wrapper = new AliasWrapper();
    CsvSqlExport export = new CsvSqlExport(csvEM, sqlEM, wrapper);
    export.csvToSql();

    storeSqlDAO.closeSessionFactory();
  }

  public void process2() throws IOException {
    GoodsSqlDAO goodsSqlDAO = new GoodsSqlDAO();
    List<Good> goods = goodsSqlDAO.getGoods();
    AliasCsvDAO aliasCsvDAO = new AliasCsvDAO(storeSqlDAO.getStores());
    for (Good good : goods) {
      aliasCsvDAO.saveAlias(good);
    }

    storeSqlDAO.closeSessionFactory();
  }

}
