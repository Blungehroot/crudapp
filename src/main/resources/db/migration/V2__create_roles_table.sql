CREATE TABLE IF NOT EXISTS roles
(
    id   int(11)      NOT NULL auto_increment,
    name varchar(250) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_USER');