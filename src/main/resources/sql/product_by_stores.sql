SELECT c, stores.name FROM (
                             SELECT
                               COUNT(*) AS c,
                               store
                             FROM products
                             WHERE day = (SELECT MAX(DISTINCT (day))
                                          FROM products)
                             GROUP BY store
                             ORDER BY store ASC
                           ) as count
  JOIN stores
    ON stores.id = count.store