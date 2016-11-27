package beholder.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import beholder.domain.CategoryCheck;
import beholder.persistence.sql.ProductSqlDAO;

/**
 * Blahblahblah
 */
@Component
public class ProductMonitoringService {

  final static Logger log = LoggerFactory.getLogger(ProductMonitoringService.class);

  @Autowired
  private ProductSqlDAO dao;

  @Transactional("transactionManager")
  public void monitor() {
    String sql = "SELECT p.*, phcnt, CAST(pcnt AS FLOAT)/phcnt AS diff FROM (" +
        "SELECT category AS pc, store AS ps, count(*) AS pcnt\n" +
        "  FROM public.products\n" +
        "\n" +
        "  GROUP BY pc, ps\n" +
        "  ) AS p\n" +
        "  JOIN\n" +
        "(SELECT category AS phc, store AS phs, count(*) AS phcnt\n" +
        "  FROM products_history\n" +
        "  WHERE day=(SELECT MAX(day) FROM products_history)\n" +
        "  GROUP BY phc, phs\n" +
        "  ORDER BY phs, phc ASC) AS ph\n" +
        "  ON ps = phs AND pc = phc";

    List<CategoryCheck> result = dao.customSQLQueryList(sql, CategoryCheck.class);
    for(CategoryCheck categoryCheck : result) {
      if(categoryCheck.getDiff() != 1F) {
        log.info(categoryCheck.toString());
      }
    }
  }

  @Transactional
  public void moveToHistory() {
    dao.customSQLQuery("INSERT INTO products_history SELECT * FROM products");
  }
}
