CREATE TABLE IF NOT EXISTS events
(
    id int(11) NOT NULL auto_increment,
    eventname varchar(250)  NOT NULL,
    media_id int(11),
    user_id int(11),
    PRIMARY KEY  (id),
    FOREIGN KEY (media_id) REFERENCES media (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);