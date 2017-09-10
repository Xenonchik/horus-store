package persistence.sql;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Alias;
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

      // delete all from export
      getSession().createSQLQuery("truncate table export").executeUpdate();

      //1. get all today products
      Query select = getSession().createSQLQuery(
          "SELECT * from Products p where p.day = (SELECT MAX(distinct(p2.day)) FROM Products p2)")
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
        export.setOldName(product.getName());

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


  public List<Export> getExportByCategory(Store store, Category category) {

    beginTransaction();

    Query query = getSession().createQuery("FROM Export WHERE (category = :category OR category = :parent) AND store = :store");
    query.setParameter("category", category.getId());
    query.setParameter("parent", category.getParent());
    query.setParameter("store", store.getName());
//    query.setParameter("date", new Date());
    List<Export> list = query.list();

    endTransaction();

    return list;
  }

  public List<Export> getExportByStore(Store store) {

    beginTransaction();

    Query query = getSession().createQuery("FROM Export WHERE store = :store");
    query.setParameter("store", store.getName());
    List<Export> list = query.list();

    endTransaction();

    return list;
  }

  public Export getExportForAlias(Alias alias) {

    beginTransaction();

    Query query = getSession().createQuery("FROM Export WHERE " +
        "UPPER(:alias) LIKE(UPPER(CONCAT('%', model))) AND UPPER(:alias) LIKE(UPPER(CONCAT('%', brand, '%'))) AND store = :store ORDER BY date DESC");
    query.setParameter("alias", alias.getAlias());
    query.setParameter("store", alias.getStore());
    List<Export> list = query.list();

    endTransaction();
    if(list.size() == 0) return null;

    return list.get(0);
  }
}
