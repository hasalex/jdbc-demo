CREATE TABLE person (
    key INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL ,
    forname VARCHAR(100) NOT NULL
);

INSERT INTO person (name, forname) VALUES ('Doe', 'John');