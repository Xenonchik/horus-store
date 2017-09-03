CREATE VIEW bigr.summary_brands AS SELECT
                         (CASE WHEN (ant_name IS NULL OR ant_name = '') THEN roz_name ELSE ant_name END) AS name,
                         (CASE WHEN (ant_sku IS NULL OR ant_sku = '') THEN roz_sku ELSE ant_sku END) AS sku,
                         (CASE WHEN (ant_brand IS NULL OR ant_brand = '') THEN roz_brand ELSE ant_brand END) AS brand,
                         ant_price AS ANTOSHKA, ant_url AS ANT_URL, roz_price AS ROZETKA, roz_url AS ROZ_URL
                       FROM (
                              SELECT ant.name as ant_name, ant.price as ant_price, ant.sku as ant_sku, ant.url as ant_url, ant.brand as ant_brand,
                                     roz.name as roz_name, roz.price as roz_price, roz.sku as roz_sku, roz.url as roz_url, roz.brand as ant_brand
                              FROM
                                (SELECT p.name, p.price, p.store, p.url, p.brand, s.sku FROM bigr.products AS p
                                  JOIN bigr.skus AS s ON p.name=s.name AND p.store=s.store
                                WHERE p.store = 'ANTOSHKA' AND s.sku != '') as ant
                                FULL OUTER JOIN
                                (SELECT p.name, p.price, p.store, p.url, p.brand, s.sku FROM bigr.products AS p
                                  JOIN bigr.skus AS s ON p.name=s.name AND p.store=s.store
                                WHERE p.store = 'ROZETKA' AND s.sku != '') as roz
                                  ON ant.sku=roz.sku
                            ) as r;
COPY (SELECT * FROM bigr.summary_brands) TO '/opt/data/with_brands.csv' DELIMITER ';' CSV HEADER;