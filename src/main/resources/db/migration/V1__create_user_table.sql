CREATE TABLE IF NOT EXISTS users
(
    id int(11) NOT NULL auto_increment,
    name varchar(250)  NOT NULL,
    password varchar(250)  NOT NULL,
    role varchar(10)  NOT NULL,
    PRIMARY KEY  (id)
    );