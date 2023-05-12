USE lumen;

DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS view_status;
DROP TABLE IF EXISTS periods;

CREATE TABLE user (
    user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);

CREATE TABLE view_status (
    status VARCHAR(20) PRIMARY KEY
);

CREATE TABLE periods (
    hours INTEGER PRIMARY KEY
);

CREATE TABLE bookmark (
    bookmark_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    position INTEGER NOT NULL,
    date_added VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(500) UNIQUE NOT NULL,
    period_hours INTEGER,
    expiry_date VARCHAR(20),
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (period_hours)
        REFERENCES periods (hours)
        ON UPDATE CASCADE
);

CREATE SEQUENCE position_sequence START WITH 200 INCREMENT BY 200;

INSERT INTO hibernate_sequence (next_val) VALUES (1);

INSERT INTO periods (hours) VALUES (24), (168), (5040);
