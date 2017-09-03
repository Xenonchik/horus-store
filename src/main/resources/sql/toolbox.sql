-- Get products by store
SELECT name, cc, store FROM stores
  LEFT JOIN (
              SELECT count(*) as cc, store
              FROM public.products
              GROUP BY store )
    as c ON c.store = id
ORDER BY cc DESC;

-- Export without alias
COPY (SELECT * FROM public.without_alias) TO '/opt/data/without_alias.csv' DELIMITER ';' CSV HEADER;

-- Export prices summary
COPY (SELECT * FROM public.prices_summary) TO '/opt/data/prices_summary.csv' DELIMITER ';' CSV HEADER;