SELECT * FROM customers;
SELECT * FROM employees;
SELECT * FROM finances;
SELECT * FROM inventory;
SELECT * FROM orders;
INSERT INTO Customers VALUES ('19', 'Estella', 'Chen');
INSERT INTO Employees VALUES ('6', 'Dee', 'Doe', '15', 'Cashier', '08/01/2022', 'false');
INSERT INTO Finances VALUES ('24', 'true', 'false', 'Week 10 Day 1 Orders', '3000');
INSERT INTO Inventory VALUES ('26', 'Icecream cup', 'Utensils', '01/01/3000', 'false', '5000', 'number')
INSERT INTO Orders VALUES ('700', '700', '17.13', '12/4/2022', '342978', '228951', 'True', 'Chick-fil-A Nuggets  Combo 8 Pc.')
DELETE FROM Customers WHERE firstName='Estella';
DELETE FROM Employees WHERE firstName='Dee';
DELETE FROM Finances WHERE details='Week 10 Day 1 Orders';
DELETE FROM Inventory WHERE name='Icecream cup';
DELETE FROM Orders WHERE saleDate='12/4/2022'