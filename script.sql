-- Database: GamesPlatform

-- Удаление таблицы user_game_collections
DROP TABLE IF EXISTS user_game_collections CASCADE;

-- Удаление таблицы notifications
DROP TABLE IF EXISTS notifications CASCADE;

-- Удаление таблицы games_suggestions
DROP TABLE IF EXISTS games_suggestions CASCADE;

-- Удаление таблицы reviews
DROP TABLE IF EXISTS reviews CASCADE;

DROP TABLE IF EXISTS steam_reviews CASCADE;

-- Удаление таблицы game_platforms
DROP TABLE IF EXISTS game_platforms CASCADE;


DROP TABLE IF EXISTS game_genres CASCADE;

-- Удаление таблицы games
DROP TABLE IF EXISTS games CASCADE;

-- Удаление таблицы users
DROP TABLE IF EXISTS users CASCADE;

-- Удаление таблицы roles
DROP TABLE IF EXISTS roles CASCADE;

-- Удаление таблицы platforms
DROP TABLE IF EXISTS platforms CASCADE;

DROP TABLE IF EXISTS genres CASCADE;

-- Удаление таблицы ratings
DROP TABLE IF EXISTS ratings CASCADE;


/*CREATE DATABASE "GamesPlatform"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
*/

CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE users
(
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id  INT,
    enabled  BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);


/*ALTER TABLE users
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

UPDATE users
SET enabled = TRUE;
*/
CREATE TABLE platforms
(
    platform_id SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL
);

create table genres
(
    genre_id    SERIAL PRIMARY KEY,
    description VARCHAR(50)

);

CREATE TABLE games
(
    game_id      SERIAL PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    steam_id     INT UNIQUE,
    description  TEXT,
    release_date DATE,
    developer    VARCHAR(100),
    editor_id    INT,
    image_url    varchar(100),
    FOREIGN KEY (editor_id) REFERENCES users (user_id)
);

CREATE TABLE game_platforms
(
    game_id     INT,
    platform_id INT,
    PRIMARY KEY (game_id, platform_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id),
    FOREIGN KEY (platform_id) REFERENCES platforms (platform_id)
);

CREATE TABLE game_genres
(
    game_id  INT,
    genre_id INT,
    PRIMARY KEY (game_id, genre_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

CREATE TABLE ratings
(
    rating_id    SERIAL PRIMARY KEY,
    user_id      INT,
    game_id      INT,
    rating_value INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id)
);



CREATE TABLE reviews
(
    review_id  SERIAL PRIMARY KEY,
    game_id    INT,
    user_id    INT,
    comment    TEXT,
    date       DATE,
    is_blocked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (game_id) REFERENCES games (game_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);


CREATE TABLE steam_reviews
(
    steam_review_id SERIAL PRIMARY KEY,
    game_id         INT,
    username        varchar(50),
    comment         TEXT,
    date            DATE,
    FOREIGN KEY (game_id) REFERENCES games (game_id)
);

-- Выбор всех ролей
SELECT *
FROM steam_reviews;

CREATE TABLE user_game_collections
(
    user_id    INT,
    game_id    INT,
    date_added DATE,
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id)
);



CREATE TABLE notifications
(
    notification_id   SERIAL PRIMARY KEY,
    user_id           INT,
    game_id           INT,
    notification_type VARCHAR(50),
    is_notified       BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id)
);

CREATE TABLE games_suggestions
(
    suggestion_id SERIAL PRIMARY KEY,
    title         VARCHAR(100) NOT NULL,
    description   TEXT,
    user_id       INT,
    status        VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Заполнение таблицы roles
INSERT INTO roles (role_name)
VALUES ('ROLE_USER'),
       ('ROLE_EDITOR'),
       ('ROLE_MODERATOR');

INSERT INTO platforms (name)
VALUES ('windows'),
       ('mac'),
       ('linux');

INSERT INTO genres (description)
VALUES ('Экшены'),
 ('Action'),
 ('Инди'),
 ('Стратегии'),
 ('RPG'),
 ('Simulation'),
 ('Strategy'),
 ('Adventure'),
 ('Indie'),
 ('Early Access'),
 ('Приключенческие игры'),
 ('Ролевые игры'),
 ('Ранний доступ'),
 ('Многопользовательские игры'),
 ('Симуляторы'),
 ('Гонки'),
 ('Казуальные игры'),
 ('Free to Play'),
 ('Animation & Modeling'),
 ('Video Production'),
 ('Casual'),
 ('Racing'),
 ('Violent'),
 ('Massively Multiplayer'),
 ('Sports'),
 ('Design & Illustration'),
 ('Documentary'),
 ('Tutorial')
;

-- Заполнение таблицы users
INSERT INTO users (username, email, password, role_id)
VALUES ('user1', 'user1@example.com', 'password1', 1),
       ('user2', 'user2@example.com', 'password2', 1),
       ('editor1', 'editor1@example.com', 'password3', 2),
       ('moderator1', 'moderator1@example.com', 'password4', 3);

-- Заполнение таблицы games
INSERT INTO games (title, description, release_date, developer, editor_id, image_url)
VALUES ('Witcher 3', 'Final game in witcher series', '2015-01-01', 'CD Project RED', 3, '/images/witcher-3.jpg'),
       ('Quake Champions', 'FPS multiplayer game', '2019-02-01', 'idSoftware', 3, null),
       ('Counter-strike 1.6', 'Tactical FPS game', '1999-03-01', 'Valve', 3, null),
       ('Counter-strike : Global Offensive', 'Tactical FPS game', '2012-03-01', 'Valve', 3, null),
       ('Counter-strike 2', 'Tactical FPS game', '1999-03-01', 'Valve', 3, null),
       ('Fallout: New Vegas', 'RPG game', '2011-03-01', 'Bethesda', 3, null);

INSERT INTO game_platforms (game_id, platform_id)
VALUES (1, 1), -- Windows
       (1, 2), -- MacOS
       (1, 3), -- Linux
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1);
-- Linux

-- Заполнение таблицы reviews
INSERT INTO reviews (game_id, user_id, comment, date, is_blocked)
VALUES (1, 1, 'Review for Game1 by User1', '2022-01-05', FALSE),
       (1, 2, 'Review for Game1 by User2', '2022-01-10', FALSE),
       (2, 1, 'Review for Game2 by User1', '2022-02-05', FALSE),
       (3, 2, 'Review for Game3 by User2', '2022-03-10', FALSE);

-- Заполнение таблицы user_game_collections
INSERT INTO user_game_collections (user_id, game_id, date_added)
VALUES (1, 1, '2022-01-15'),
       (1, 2, '2022-02-20'),
       (2, 3, '2022-03-25');

-- Заполнение таблицы notifications
INSERT INTO notifications (user_id, game_id, notification_type, is_notified)
VALUES (1, 1, 'Type1', TRUE),
       (2, 2, 'Type2', FALSE),
       (2, 3, 'Type3', TRUE);

-- Заполнение таблицы games_suggestions
INSERT INTO games_suggestions (title, description, user_id, status)
VALUES ('Suggested Game1', 'Description for Suggested Game1', 1, 'Pending'),
       ('Suggested Game2', 'Description for Suggested Game2', 2, 'Approved');


-- Выбор всех ролей
SELECT *
FROM roles;

-- Выбор всех пользователей
SELECT *
FROM users;

-- Выбор всех игр
SELECT *
FROM games;

-- Выбор всех отзывов
SELECT *
FROM reviews;

-- Выбор всех элементов пользовательских коллекций
SELECT *
FROM user_game_collections;

-- Выбор всех уведомлений
SELECT *
FROM notifications;

-- Выбор всех предложенных игр
SELECT *
FROM games_suggestions;

-- Выбор всех рейтингов
SELECT *
FROM ratings;

SELECT *
FROM platforms;

/*DELETE FROM GAMES WHERE game_id = 0*/

select * from games where title = 'Miasma Chronicles'