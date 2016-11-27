package beholder.app.proc;

import java.io.IOException;
import java.util.List;

import beholder.domain.Good;
import beholder.persistence.csv.AliasCsvDAO;
import beholder.persistence.csv.AliasWrapper;
import beholder.persistence.export.AliacCsvEM;
import beholder.persistence.export.CsvSqlExport;
import beholder.persistence.export.EntityManager;
import beholder.persistence.export.EntityWrapper;
import beholder.persistence.sql.AliasSqlDAO;
import beholder.persistence.sql.GoodsSqlDAO;
import beholder.persistence.sql.StoreSqlDAO;

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
