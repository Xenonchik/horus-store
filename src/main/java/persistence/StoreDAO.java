package persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import domain.Product;
import domain.Store;

/**
 * Blahblahblah
 */
public class StoreDAO extends DAO {

    private static Session session;

    public List<Store> getStores() {
        beginTransaction();

        Query query = session.createQuery("from Store where status = 1");
        List<Store> list = query.list();

        endTransaction();

        return list;
    }

    public Map<String, Store> getStoresAsMap() {
        Map<String, Store> result = new HashMap<>();

        for(Store store : getStores()) {
            result.put(store.getName().toLowerCase(), store);
        }

        return result;
    }


}
