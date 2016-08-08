package app;

import persistence.csv.AliasWrapper;
import persistence.export.AliacCsvEM;
import persistence.export.CsvSqlExport;
import persistence.export.EntityManager;
import persistence.export.EntityWrapper;
import persistence.sql.AliasSqlDAO;
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

}
