package persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Export;
import domain.Product;
import domain.Store;

/**
 * Blahblahblah
 */
public class ExportDAO {

  final static Logger log = LoggerFactory.getLogger(ExportDAO.class);

  private Session session;
  private final List<String> brands;

  public ExportDAO(List<String> brands) {
    this.brands = brands;
  }

  public void export() {
    List<Product> products;
    Map<Long, String> stores = new HashMap<>();

    session = HibernateUtils.getSessionFactory().openSession();
    session.beginTransaction();

    try {

    //1. get all today products
    Query select = session.createSQLQuery(
        "SELECT * from Products p where p.date >= (NOW() - INTERVAL '1 DAYS')")
        .addEntity(Product.class);
    products = select.list();

    //1.1 get stores
    Query storeSelect = session.createSQLQuery(
        "SELECT * from Stores")
        .addEntity(Store.class);
    for (Store store : (List<Store>) storeSelect.list()) {
      stores.put(store.getId(), store.getName());
    }

    //2. create exports
    for (Product product : products) {
      Export export = new Export();
      export.setDate(product.getDate());
      export.setPrice(product.getPrice());
      export.setName(product.getName(), brands);
      export.setStore(stores.get(product.getStore().longValue()));
      export.setUrl(product.getUrl());

      //3. save exports
      try {
        session.save(export);
      } catch (DataException de) {
        log.error("Some troublz with: " + product.getName());
        throw de;
      }

    }
    } catch (Exception e) {
      e.printStackTrace();
    }


    session.getTransaction().commit();
    session.close();
    log.info("Hibernate session closed");
  }
}
