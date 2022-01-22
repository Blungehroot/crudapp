CREATE TABLE IF NOT EXISTS users
(
    id int(11) NOT NULL auto_increment,
    name varchar(250)  NOT NULL,
    password varchar(250)  NOT NULL,
    status varchar(25) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY  (id)
    );