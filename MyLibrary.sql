-- CREATE SCHEMA

CREATE DATABASE MyLibrary;
USE MyLibrary;

-- USER TABLE
CREATE TABLE userinfo (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    userType INT NOT NULL CHECK (userType IN (1, 2))
);

-- AUTHORS TABLE
CREATE TABLE authors (
    authorId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    website VARCHAR(100) NOT NULL
);

-- BOOKS TABLE
CREATE TABLE books (
    bookId INT AUTO_INCREMENT PRIMARY KEY,
    authorId INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    year INT CHECK (year >= 0),
    numberOfPages INT CHECK (numberOfPages >= 1),
    cover VARCHAR(255),
    about TEXT,
    `read` INT NOT NULL CHECK (`read` IN (1, 2, 3)),
    rating INT NOT NULL CHECK (rating BETWEEN 0 AND 5),
    comments TEXT,
    releaseDate DATE,
    CONSTRAINT fk_book_author FOREIGN KEY (authorId) REFERENCES authors(authorId) ON DELETE CASCADE
);

-- USERS
INSERT INTO userinfo (username, password, userType) VALUES
('advancedReader', '1234', 1),
('basicReader', '1234', 2);

-- AUTHORS (insert one-by-one and assign correct website manually)
INSERT INTO authors (authorId, name, surname, website) VALUES
(1, 'Batu', 'Salcan', 'website-1'),
(2, 'Lily', 'Gomez', 'website-2');

-- BOOKS (authorId must match)
INSERT INTO books (authorId, title, year, numberOfPages, cover, about, `read`, rating, comments, releaseDate) VALUES
(1, 'Ruby', 2023, 300, '/resources/images/Book1.jpg', 'This book is about Batu Salcan''s dog Ruby.', 1, 5, 'very good book', NULL),
(2, 'Purple Tulip', 2003, 314, '/resources/images/Book2.jpg', 'A woman who struggles in life.', 3, 0, 'want to read', '2025-06-22'),
(1, 'Good Place', 2007, 493, '/resources/images/Book3.jpg', 'In what place are you in your life?', 2, 0, 'not read yet', NULL),
(1, 'Feel the Wind', 2010, 261, '/resources/images/Book4.jpg', 'Life is good.', 1, 4, 'This book made me feel good.', NULL);

-- TRIGGER
DELIMITER $$
CREATE TRIGGER trg_delete_author_if_no_books
AFTER DELETE ON books
FOR EACH ROW
BEGIN
    DECLARE book_count INT;
    SELECT COUNT(*) INTO book_count FROM books WHERE authorId = OLD.authorId;
    IF book_count = 0 THEN
        DELETE FROM authors WHERE authorId = OLD.authorId;
    END IF;
END$$
DELIMITER ;


-- TABLES
select * from userinfo;

select * from books;

select * from authors;

