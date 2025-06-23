-- Elimina se esistono (ATTENTO: perderai i dati)
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

-- Ricrea le tabelle con l’ordine desiderato
CREATE TABLE author (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    date_of_birth DATE,
    nationality VARCHAR(255)
);

CREATE TABLE book (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    year_of_publication INTEGER
);

-- Ricrea la sequenza se serve
DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1;

-- Inserisci i dati
INSERT INTO author (id, name, surname, date_of_birth, nationality) VALUES (nextval('hibernate_sequence'), 'George', 'Orwell', '1903-06-25', 'British');
INSERT INTO author (id, name, surname, date_of_birth, nationality) VALUES (nextval('hibernate_sequence'), 'Harper', 'Lee', '1926-04-28', 'American');
INSERT INTO author (id, name, surname, date_of_birth, nationality) VALUES (nextval('hibernate_sequence'), 'Aldous', 'Huxley', '1894-07-26', 'British');

INSERT INTO book (id, title, year_of_publication) VALUES (nextval('hibernate_sequence'), '1984', 1949);
INSERT INTO book (id, title, year_of_publication) VALUES (nextval('hibernate_sequence'), 'Il buio oltre la siepe', 1960);
INSERT INTO book (id, title, year_of_publication) VALUES (nextval('hibernate_sequence'), 'Il mondo nuovo', 1932);
