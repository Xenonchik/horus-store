package persistence.sql;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Brand;
import domain.Category;
import domain.Export;
import domain.Product;
import domain.Store;

/**
 * Blahblahblah
 */
public class ExportSqlDAO extends SqlDAO {

  final static Logger log = LoggerFactory.getLogger(ExportSqlDAO.class);

  private final SupportSqlDAO supportDAO = new SupportSqlDAO();
  private final StoreSqlDAO storeDAO = new StoreSqlDAO();
  List<Product> products;
  Map<Long, Store> stores;
  List<Brand> brands;

  // TODO push this to processor
  public void export() {
    beginTransaction();

    try {

      //1. get all today products
      Query select = getSession().createSQLQuery(
          "SELECT * from Products p where p.day = (SELECT MAX(distinct(p2.day)) FROM Products p2)")
//             "SELECT * from Products p where p.date >= (NOW() - INTERVAL '1 DAYS')")
//              "SELECT * from Products p where store = 3 AND p.date >= (NOW() - INTERVAL '15 DAYS')")
          .addEntity(Product.class);
      products = select.list();

      endTransaction();

      //1.1 get stores
      stores = storeDAO.getStoresAsMapById();
      brands = supportDAO.getBrands();


      beginTransaction();

      //2. create exports
      for (Product product : products) {
        Export export = new Export();
        export.setDate(product.getDate());
        export.setPrice(product.getPrice());

        Store currentStore = stores.get(product.getStore().longValue());
        export.setStore(currentStore.getName());
        export.setUrl(product.getUrl());
        export.setCategory(product.getCategory());

        if(export.getCategory() == null) {
          log.warn("No category for product: " + product.getName() + " and url:" + product.getUrl());
        }

        export.setName(product.getName(), brands);

        //3. save exports
        try {
          getSession().save(export);
        } catch (DataException de) {
          log.error("Some troublz with: " + product.getName());
          throw de;
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    endTransaction();
    closeSessionFactory();
    log.info("Export ends. Products exported: " + products.size());
  }

  public List<Export> getExports() {
    beginTransaction();

    //log.info(session.toString());

    Query query = getSession().createQuery("from Export");
    List<Export> list = query.list();

    endTransaction();

    return list;
  }


  public List<Export> getExport(Store store, Category category) {

    beginTransaction();

    Query query = getSession().createQuery("FROM Export WHERE (category = :category OR category = :parent) AND store = :store AND date = '2016-07-10'");
    query.setParameter("category", category.getId());
    query.setParameter("parent", category.getParent());
    query.setParameter("store", store.getName());
//    query.setParameter("date", new Date());
    List<Export> list = query.list();

    endTransaction();

    return list;
  }
}