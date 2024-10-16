-- Create Author table
CREATE TABLE IF NOT EXISTS Author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Book table
CREATE TABLE IF NOT EXISTS Book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Create Genre table
CREATE TABLE IF NOT EXISTS Genre (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Book_Genre table for Many-to-Many relationship
CREATE TABLE IF NOT EXISTS Book_Genre (
    book_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (genre_id) REFERENCES Genre(id)
);

-- Insert test data into Author table
INSERT INTO Author (name) VALUES
    ('J.K. Rowling'),
    ('George R.R. Martin'),
    ('J.R.R. Tolkien');

-- Insert test data into Genre table
INSERT INTO Genre (name) VALUES
    ('Fantasy'),
    ('Adventure'),
    ('Science Fiction');

-- Insert test data into Book table
INSERT INTO Book (title, author_id) VALUES
    ('Harry Potter and the Sorcerers Stone', (SELECT id FROM Author WHERE name = 'J.K. Rowling')),
    ('A Game of Thrones', (SELECT id FROM Author WHERE name = 'George R.R. Martin')),
    ('The Hobbit', (SELECT id FROM Author WHERE name = 'J.R.R. Tolkien'));

-- Insert test data into Book_Genre table
INSERT INTO Book_Genre (book_id, genre_id) VALUES
    ((SELECT id FROM Book WHERE title = 'Harry Potter and the Sorcerers Stone'), (SELECT id FROM Genre WHERE name = 'Fantasy')),
    ((SELECT id FROM Book WHERE title = 'A Game of Thrones'), (SELECT id FROM Genre WHERE name = 'Fantasy')),
    ((SELECT id FROM Book WHERE title = 'The Hobbit'), (SELECT id FROM Genre WHERE name = 'Adventure'));