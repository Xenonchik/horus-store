CREATE VIEW bigr.summary AS SELECT
                         (CASE WHEN (ant_name IS NULL OR ant_name = '') THEN roz_name ELSE ant_name END) AS name,
                         (CASE WHEN (ant_sku IS NULL OR ant_sku = '') THEN roz_sku ELSE ant_sku END) AS sku,
                         ant_price AS ANTOSHKA, roz_price AS ROZETKA
                       FROM (
                              SELECT ant.name as ant_name, ant.price as ant_price, ant.sku as ant_sku,
                                     roz.name as roz_name, roz.price as roz_price, roz.sku as roz_sku
                              FROM
                                (SELECT p.name, p.price, p.store, s.sku FROM bigr.products AS p
                                  JOIN bigr.skus AS s ON p.name=s.name AND p.store=s.store
                                WHERE p.store = 'ANTOSHKA' AND s.sku != '') as ant
                                FULL OUTER JOIN
                                (SELECT p.name, p.price, p.store, s.sku FROM bigr.products AS p
                                  JOIN bigr.skus AS s ON p.name=s.name AND p.store=s.store
                                WHERE p.store = 'ROZETKA' AND s.sku != '') as roz
                                  ON ant.sku=roz.sku
                            ) as r