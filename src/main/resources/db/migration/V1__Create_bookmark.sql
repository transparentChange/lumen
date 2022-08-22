DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (
    bookmark_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    date_added VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(500) UNIQUE NOT NULL,
    period_hours INTEGER,
    expiry_date VARCHAR(20),
    status VARCHAR(20),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
    FOREIGN KEY (period_hours)
        REFERENCES periods (hours)
        ON UPDATE CASCADE,
    FOREIGN KEY (status)
        REFERENCES view_status (status)
);


DROP TABLE IF EXISTS user;
CREATE TABLE user (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS view_status;
DROP TABLE IF EXISTS periods;

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);

CREATE TABLE view_status (
    status VARCHAR(20) PRIMARY KEY
);

CREATE TABLE periods (
    hours INTEGER PRIMARY KEY
);

INSERT INTO hibernate_sequence (next_val) VALUES (1);

INSERT INTO periods (hours) VALUES (24), (168), (5040);

INSERT INTO view_status (status) VALUES ('VISITED'), ('COMPLETED');
