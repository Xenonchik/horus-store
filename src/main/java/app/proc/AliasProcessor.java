package app.proc;

import java.util.List;

import domain.Export;
import domain.NewAlias;
import persistence.sql.ExportSqlDAO;
import persistence.sql.NewAliasSqlDAO;
import persistence.sql.OldGoodsSqlDAO;

/**
 * Blahblahblah
 */
public class AliasProcessor {

  private NewAliasSqlDAO aliasSqlDAO = new NewAliasSqlDAO();
  private ExportSqlDAO exportSqlDAO = new ExportSqlDAO();

  public void process() {

    List<NewAlias> goods = aliasSqlDAO.getEntities();
    goods.forEach(good -> {
          List<Export> exports = exportSqlDAO.getExportForGood(good);
        }
    );

  }
}
