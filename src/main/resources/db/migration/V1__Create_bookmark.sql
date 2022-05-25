DROP TABLE IF EXISTS bookmark;

CREATE TABLE bookmark (
    bookmark_id INTEGER PRIMARY KEY AUTOINCREMENT,
    date_added VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(500) UNIQUE NOT NULL
);

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);

INSERT INTO hibernate_sequence (next_val) VALUES (0);