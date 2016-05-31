package app;

import java.util.List;

import domain.EmirGood;
import domain.Export;
import persistence.EmirDAO;
import persistence.ExportDAO;

/**
 * Created by serge on 5/30/16.
 */
public class AliasProcessor {

  private final ExportDAO exportDAO = new ExportDAO();
  private final EmirDAO emirDAO = new EmirDAO();

  public void process() {
    List<Export> exports = exportDAO.getExports();
    List<EmirGood> emirGoods = emirDAO.getGoods();


  }
}
