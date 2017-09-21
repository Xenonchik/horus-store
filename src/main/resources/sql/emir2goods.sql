COPY eg_tmp FROM '/opt/data/emir-tmp.csv' DELIMITER ',' CSV HEADER;

INSERT INTO goods (brand, emir, emir_id)
SELECT e.brand, CONCAT_WS(' ', t2, t3, model), e.id FROM goods as g
RIGHT join  emir_goods as e
ON e.brand=g.brand AND g.emir LIKE concat('%', e.model)
WHERE g.id IS NULL;

DELETE FROM goods
WHERE id IN(
SELECT g.id FROM goods as g
LEFT join  emir_goods as e
ON e.brand=g.brand AND g.emir LIKE concat('%', e.model)
WHERE e.id IS NULL
);


SELECT exp.brand, exp.model, exp.store,
       exp.old_name, emir
  FROM public.export as exp
JOIN

(SELECT  g.brand, emir, rozetka, model
  FROM public.goods as g
JOIN emir_goods as e
ON g.brand=e.brand
and emir like concat('%',model)
  WHERE rozetka IS NULL) as eg

ON exp.brand=eg.brand AND replace(exp.model, ' ', '') = replace(eg.model, ' ', '')

  WHERE store = 'ROZETKA';

