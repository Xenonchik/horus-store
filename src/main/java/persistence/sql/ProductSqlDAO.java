package persistence.sql;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CategoryCheck;
import domain.Product;

/**
 * Blahblahblah
 */
public class ProductSqlDAO extends SqlDAO {

    final static Logger log = LoggerFactory.getLogger(ProductSqlDAO.class);

    public void insert(List<Product> entities) {
        beginTransaction();
        for (Product product : entities) {
            getSession().save(product);
        }

        endTransaction();
    }

    public void monitor() {
        beginTransaction();

        Query query = getSession().createSQLQuery("SELECT p.*, phcnt, CAST(pcnt AS FLOAT)/phcnt AS diff FROM (" +
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
            "  ON ps = phs AND pc = phc")
//            .addScalar("pc", StandardBasicTypes.LONG)
            .setResultTransformer(new AliasToBeanResultTransformer(CategoryCheck.class))
            ;
        List<CategoryCheck> result = query.list();
        for(CategoryCheck categoryCheck : result) {
            if(categoryCheck.getDiff() != 1F) {
                log.info(categoryCheck.toString());
            }
        }
        endTransaction();
    }

    public void moveToHistory() {
        beginTransaction();
        Query query = getSession().createSQLQuery("INSERT INTO archive.products_history SELECT * FROM products");
        query.list();
        endTransaction();
    }


}
