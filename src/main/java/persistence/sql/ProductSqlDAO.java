package persistence.sql;

import java.util.List;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductSqlDAO extends SqlDAO {

    public void insert(List<Product> entities) {
        beginTransaction();
        for (Product product : entities) {
            getSession().save(product);
        }

        endTransaction();
    }


}
