package persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Category;
import domain.Export;
import domain.Product;
import domain.Store;

/**
 * Blahblahblah
 */
public class ExportDAO extends DAO {

  final static Logger log = LoggerFactory.getLogger(ExportDAO.class);

  private final SupportDAO supportDAO = new SupportDAO();
  private final StoreDAO storeDAO = new StoreDAO();
  List<Product> products;
  Map<Long, Store> stores;
  Set<String> brands;

  // TODO push this to processor
  public void export() {
    beginTransaction();

    try {

      //1. get all today products
      Query select = getSession().createSQLQuery(
             "SELECT * from Products p where p.date >= (NOW() - INTERVAL '1 DAYS')")
//              "SELECT * from Products p where store = 3 AND p.date >= (NOW() - INTERVAL '15 DAYS')")
              .addEntity(Product.class);
      products = select.list();

      endTransaction();

      //1.1 get stores
      stores = storeDAO.getStoresAsMapById();
      brands = supportDAO.getBrandsSet();


      beginTransaction();

      //2. create exports
      for (Product product : products) {
        Export export = new Export();
        export.setDate(product.getDate());
        export.setPrice(product.getPrice());

        Store currentStore = stores.get(product.getStore().longValue());
        export.setStore(currentStore.getName());

        for (Category category : currentStore.getCategories()) {
          if (product.getSanitizedUrl().contains(category.getUrl().replace(".html", ""))) {
            export.setUrl(category.getUrl());
            export.setCategory(category.getCategory());
          }
        }
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

  public List<Export> getExport(Export export) {
    beginTransaction();

    Query query = getSession().createQuery("FROM Export WHERE category = :category AND store = :store");
    query.setParameter("category", export.getCategory());
    query.setParameter("store", export.getStore());
    List<Export> list = query.list();

    endTransaction();

    return list;
  }

}
