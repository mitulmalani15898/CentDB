CREATE TABLE customer (customer_id int PRIMARY KEY, name varchar(255), email varchar(255), address varchar(255);
INSERT INTO customer VALUES (2, meet, meet@gmail.com, 4565 Hey st), (11, manthan, moradia, quebec);
CREATE TABLE product (product_id int PRIMARY KEY, product_name varchar(10), product_price float, customer_id int REFERENCES customer(customer_id);
INSERT INTO product VALUES (1, chair, 85.65, 1), (2, table, 149.99, 2), (3, laptop, 999.99, 11);
