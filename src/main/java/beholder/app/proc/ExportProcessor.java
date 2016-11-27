package beholder.app.proc;

import beholder.persistence.sql.ExportSqlDAO;

/**
 * Blahblahblah
 */
public class ExportProcessor {

  public void process() {
    ExportSqlDAO exportDAO = new ExportSqlDAO();
    exportDAO.export();
  }
}
