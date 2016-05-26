package persistence;

import java.util.List;

import org.hibernate.Session;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductDAO implements EntityDAO<Product> {

    private static Session session;

    @Override
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
