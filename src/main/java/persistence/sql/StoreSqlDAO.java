package persistence.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import domain.Store;

/**
 * Blahblahblah
 */
public class StoreSqlDAO extends SqlDAO {

    public List<Store> getStores() {
        beginTransaction();

        Query query = getSession().createQuery("from Store where status = 1");
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

    public Map<Long, Store> getStoresAsMapById() {
        Map<Long, Store> result = new HashMap<>();

        for(Store store : getStores()) {
            result.put(store.getId(), store);
        }

        return result;
    }

    public List<String> getStoreNames() {
        List<String> result = new ArrayList<>();
        for(Store store : getStores()) {
            result.add(store.getName().toUpperCase());
        }

        return result;
    }


}
