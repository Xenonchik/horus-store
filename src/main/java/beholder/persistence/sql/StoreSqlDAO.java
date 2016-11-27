package beholder.persistence.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import beholder.domain.Store;
import beholder.persistence.SqlDAO;

/**
 * Blahblahblah
 */
@Component
public class StoreSqlDAO extends SqlDAO {

    public List<Store> getStores() {
        Query query = getSession().createQuery("from Store where status = 1");
        List<Store> list = query.list();
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
