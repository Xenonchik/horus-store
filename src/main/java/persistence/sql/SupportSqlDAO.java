package persistence.sql;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Brand;
import domain.Useragent;

/**
 * DAO for support entities - brands, useragents, etc
 */
public class SupportSqlDAO extends SqlDAO {

  final static Logger log = LoggerFactory.getLogger(SupportSqlDAO.class);

  public List<Useragent> getUseragents() {
    beginTransaction();

    //log.info(session.toString());

    Query query = getSession().createQuery("from Useragent");
    List<Useragent> list = query.list();

    endTransaction();

    return list;
  }

  public List<Brand> getBrands() {
    beginTransaction();

    Query query = getSession().createQuery("from Brand");
    
    List<Brand> list = query.list();

    endTransaction();

    return list;
  }

  public void updateAliasesByStore(String store) {
    beginTransaction();

    String selectQuery = "SELECT  exp.brand, exp.model as export, e.model as emir_model, exp.store,\n" +
            "       exp.old_name, emir\n" +
            "  FROM public.goods as g\n" +
            "JOIN emir_goods as e\n" +
            "ON g.emir like concat('%', e.model) AND g.brand=e.brand\n" +
            "JOIN public.export as exp\n" +
            "\n" +
            "ON exp.brand=e.brand AND replace(exp.model, ' ', '') = replace(e.model, ' ', '')\n" +
            "\n" +
            "  WHERE store = '"+store.toUpperCase()+"'\n" +
            "AND "+store.toLowerCase()+" IS NULL;\n";
    String updateQuery = "UPDATE public.goods as g\n" +
            " SET " + store.toLowerCase() + "=old_name\n" +
            " FROM emir_goods as e\n" +
            " JOIN public.export as exp\n" +
            " ON exp.brand=e.brand AND replace(exp.model, ' ', '') = replace(e.model, ' ', '')\n" +
            "  WHERE store = '"+store.toUpperCase()+"'" +
            " AND "+store.toLowerCase()+" IS NULL AND g.emir like concat('%', e.model) AND g.brand=e.brand";
    Integer size = getSession().createSQLQuery(selectQuery).list().size();
    log.info("For {} {} aliases", store, size);
    getSession().createSQLQuery(updateQuery).executeUpdate();
    endTransaction();

  }
}
