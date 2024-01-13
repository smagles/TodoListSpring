create TABLE notes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (255),
    content TEXT,
    id_user BIGINT NOT NULL,
    foreign key (id_user) references users
);