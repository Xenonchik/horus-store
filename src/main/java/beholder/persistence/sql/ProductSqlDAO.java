package beholder.persistence.sql;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import beholder.domain.CategoryCheck;
import beholder.domain.Product;
import beholder.persistence.SqlDAO;

/**
 * Blahblahblah
 */
@Component
public class ProductSqlDAO extends SqlDAO {

    final static Logger log = LoggerFactory.getLogger(ProductSqlDAO.class);

    public void insert(List<Product> entities) {
        beginTransaction();
        for (Product product : entities) {
            getSession().save(product);
        }

        endTransaction();
    }

    public void moveToHistory() {
        beginTransaction();
        Query query = getSession().createSQLQuery("INSERT INTO products_history SELECT * FROM products");
        query.list();
        endTransaction();
    }


}
