create TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR (100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50)
);