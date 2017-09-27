package app.proc;

import java.io.IOException;
import java.util.List;

import domain.OldGood;
import persistence.csv.AliasCsvDAO;
import persistence.csv.AliasWrapper;
import persistence.export.AliacCsvEM;
import persistence.export.CsvSqlExport;
import persistence.export.EntityManager;
import persistence.export.EntityWrapper;
import persistence.sql.AliasSqlDAO;
import persistence.sql.OldGoodsSqlDAO;
import persistence.sql.StoreSqlDAO;

/**
 * Blahblahblah
 */
public class ExportAliacesProcessor {

  StoreSqlDAO storeSqlDAO = new StoreSqlDAO();

  public void csv2sql() {
    EntityManager csvEM = new AliacCsvEM(storeSqlDAO.getStoreNames());
    EntityManager sqlEM = new AliasSqlDAO();
    EntityWrapper wrapper = new AliasWrapper();
    CsvSqlExport export = new CsvSqlExport(csvEM, sqlEM, wrapper);
    export.csvToSql();

    storeSqlDAO.closeSessionFactory();
  }

  public void sql2csv() throws IOException {
    OldGoodsSqlDAO goodsSqlDAO = new OldGoodsSqlDAO();
    List<OldGood> goods = goodsSqlDAO.getGoods();
    AliasCsvDAO aliasCsvDAO = new AliasCsvDAO(storeSqlDAO.getStores());
    for (OldGood good : goods) {
      aliasCsvDAO.saveAlias(good);
    }

    storeSqlDAO.closeSessionFactory();
  }

}
