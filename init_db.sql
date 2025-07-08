CREATE TABLE groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE bookmarks (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    url TEXT NOT NULL,
    group_id INT,
    tag_id INT,

    CONSTRAINT fk_bookmarks_groups FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    CONSTRAINT fk_bookmarks_tags FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE SET NULL
);