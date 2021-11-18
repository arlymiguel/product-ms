DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS detail;

CREATE TABLE product (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  description VARCHAR(250) NOT NULL,
  price DECIMAL(10,2) NOT NULL
);

CREATE TABLE detail (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_id INT NOT NULL,
  key VARCHAR(250) NOT NULL,
  value VARCHAR(250) NOT NULL,
  CONSTRAINT details_profk_1 FOREIGN KEY (product_id) REFERENCES product (id)
);

