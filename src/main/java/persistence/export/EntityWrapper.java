package persistence.export;

import java.util.List;

/**
 * Blahblahblah
 */
public interface EntityWrapper<K, V> {
  List<V> wrapEntities(List<K> entities);
}
