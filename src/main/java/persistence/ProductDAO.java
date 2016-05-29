package persistence;

import java.util.List;

import org.hibernate.Session;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductDAO extends DAO {

    public void insert(List<Product> entities) {
        beginTransaction();
        for (Product product : entities) {
            getSession().save(product);
        }

        endTransaction();
    }


}
