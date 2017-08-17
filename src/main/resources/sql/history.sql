INSERT INTO archive.products_history (date, name, price, store, id, url, category, day, html)
  SELECT date, name, price, store, id, url, category, day, html FROM products

  WHERE date != (SELECT MAX(date) FROM products);
DELETE FROM public.products WHERE date != (SELECT MAX(date) FROM products);