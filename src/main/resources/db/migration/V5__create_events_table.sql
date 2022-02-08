CREATE TABLE IF NOT EXISTS events
(
    id int(11) NOT NULL auto_increment,
    eventname varchar(250)  NOT NULL,
    media_name varchar(250)  NOT NULL,
    media_url varchar(1500)  NOT NULL,
    user_id int(11),
    PRIMARY KEY  (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);