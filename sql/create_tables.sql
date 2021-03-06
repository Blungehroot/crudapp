CREATE DATABASE IF NOT EXISTS crudapp;

CREATE TABLE IF NOT EXISTS users
(
    id int(11) NOT NULL auto_increment,
    name varchar(250)  NOT NULL,
    password varchar(250)  NOT NULL,
    role varchar(10)  NOT NULL,
    PRIMARY KEY  (id)
);

CREATE TABLE IF NOT EXISTS media
(
    id int(11) NOT NULL auto_increment,
    name varchar(250)  NOT NULL,
    url varchar(1500)  NOT NULL,
    PRIMARY KEY  (id)
);

CREATE TABLE IF NOT EXISTS events
(
    id int(11) NOT NULL auto_increment,
    eventname varchar(250)  NOT NULL,
    media_id int(11),
    user_id int(11),
    PRIMARY KEY  (id),
    FOREIGN KEY (media_id) REFERENCES media (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id) REFERENCES media (id) ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE
);