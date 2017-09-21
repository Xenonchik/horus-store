package persistence.export;

import java.util.List;

import domain.Entity;

/**
 * Blahblahblah
 */
public class CsvSqlExport {

  private final EntityManager csvEm;
  private final EntityManager sqlEm;
  private final EntityWrapper wrapper;

  public CsvSqlExport(EntityManager csvEm, EntityManager sqlEm, EntityWrapper wrapper) {
    this.csvEm = csvEm;
    this.sqlEm = sqlEm;
    this.wrapper = wrapper;
  }

  public void csvToSql() {
    List<Entity> csvs = csvEm.getEntities();
    List<Entity> sqls = wrapper.wrapEntities(csvs);
    sqlEm.saveEntitites(sqls); //todo
  }

  public void sqlToCsv() {
    List<Entity> csvs = csvEm.getEntities();
    List<Entity> sqls = wrapper.wrapEntities(csvs);
    sqlEm.saveEntitites(sqls);
  }

}
