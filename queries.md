SELECT * FROM customers;
SELECT * FROM employees;
SELECT * FROM finances;
SELECT * FROM inventory;
SELECT * FROM orders;
INSERT INTO customers VALUES ('19', 'Estella', 'Chen');
INSERT INTO employees VALUES ('6', 'Dee', 'Doe', '15', 'Cashier', '08/01/2022', 'false');
INSERT INTO finances VALUES ('24', 'true', 'false', 'Week 10 Day 1 Orders', '3000');
INSERT INTO inventory VALUES ('26', 'Icecream cup', 'Utensils', '01/01/3000', 'false', '5000', 'number');
INSERT INTO orders VALUES ('700', '700', '17.13', '12/4/2022', '342978', '228951', 'True', 'Chick-fil-A Nuggets  Combo 8 Pc.');
DELETE FROM customers WHERE firstName='Estella';
DELETE FROM employees WHERE firstName='Dee';
DELETE FROM finances WHERE details='Week 10 Day 1 Orders';
DELETE FROM inventory WHERE name='Icecream cup';
DELETE FROM orders WHERE saleDate='12/4/2022';
SELECT * FROM finances WHERE Amount>3000;
SELECT * FROM orders WHERE saledate BETWEEN
'2022-10-01 00:00:00' AND '2022-11-10 12:00:00';