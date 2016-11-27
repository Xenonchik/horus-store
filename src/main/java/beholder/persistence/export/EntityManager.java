package beholder.persistence.export;

import java.util.List;

/**
 * Blahblahblah
 */
public interface EntityManager<T> {
  List<T> getEntities();

  void saveEntitites(List<T> entities);
}
