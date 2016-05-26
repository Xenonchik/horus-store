package persistence;

import java.util.List;

import org.hibernate.Session;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductDAO {

    private static Session session;

    public void insert(List<Product> entities) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        for (Product product : entities) {
            session.save(product);
        }

        session.getTransaction().commit();
        session.close();
    }


}
