INSERT INTO products_export(
            date, name, price, store_name)
    (
    SELECT p.date, p.name, p.price, s.name FROM products AS p 
    JOIN stores AS s ON s.id=p.store 
    WHERE p.date >= now()::date AND store = 9
    );

