package persistence;

import domain.Entity;

import java.util.List;

/**
 * Blahblahblah
 */
public interface EntityDAO<T extends Entity> {

    public void insert(List<T> entities);

}
