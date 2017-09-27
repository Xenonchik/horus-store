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
and emir like concat('%',e.model)
  WHERE rozetka IS NULL) as eg

ON exp.brand=eg.brand AND replace(exp.model, ' ', '') = replace(eg.model, ' ', '')

  WHERE store = 'ROZETKA';

  SELECT  exp.brand, exp.model as export, e.model as emir_model, exp.store,
       exp.old_name, emir
  FROM public.goods as g
JOIN emir_goods as e
ON g.emir like concat('%', e.model) AND g.brand=e.brand
JOIN public.export as exp

ON exp.brand=e.brand AND replace(exp.model, ' ', '') = replace(e.model, ' ', '')

  WHERE store = 'ROZETKA'
AND rozetka IS NULL;

  UPDATE public.goods as g
SET rozetka=old_name
FROM emir_goods as e
JOIN public.export as exp

ON exp.brand=e.brand AND replace(exp.model, ' ', '') = replace(e.model, ' ', '')

  WHERE store = 'ROZETKA'
AND rozetka IS NULL AND g.emir like concat('%', e.model) AND g.brand=e.brand;

update public.goods as g
SET emir_id = e.id
  FROM emir_goods as e
WHERE g.emir like concat('%', e.model) AND g.brand=e.brand
AND e.id <> emir_id


