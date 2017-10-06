DROP TABLE IF EXISTS testdb.customer;

CREATE TABLE testdb.customer(
   id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(20) NOT NULL,
   lastname VARCHAR(20) NOT NULL,
   PRIMARY KEY (id)
);