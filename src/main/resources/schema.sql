DROP TABLE IF EXISTS MPA_RATING, GENRES, USERS, FILMS, USERS_LIKES, FILMS_GENRES, FRIENDS CASCADE;
create table IF NOT EXISTS MPA_RATING
(
    mpa_id integer auto_increment primary key,
    rating_name varchar
);

create table IF NOT EXISTS GENRES
(
    genre_id integer auto_increment primary key,
    name_genre varchar NOT NULL
);

create table IF NOT EXISTS USERS
(
    USERS_ID long auto_increment
        primary key,
    EMAIL    CHARACTER VARYING not null
        unique,
    LOGIN    CHARACTER VARYING not null,
    NAME     CHARACTER VARYING not null,
    BIRTHDAY DATE
);

create table IF NOT EXISTS FILMS
(
    film_id long auto_increment primary key,
    film_name varchar not null,
    description varchar(200),
    release_date date,
    duration integer,
    mpa_id integer references MPA_RATING(mpa_id) ON DELETE NO ACTION

);


create table IF NOT EXISTS USERS_LIKES
(
    users_likes_id integer auto_increment primary key,
    film_id long references FILMS(film_id) ON DELETE CASCADE,
    user_id long references USERS(USERS_ID) ON DELETE CASCADE
);



create table IF NOT EXISTS FILMS_GENRES
(
    films_genres_id integer auto_increment primary key,
    film_id long references FILMS(film_id) ON DELETE CASCADE,
    genres_id integer references GENRES(genre_id) ON DELETE CASCADE
);

create table IF NOT EXISTS  FRIENDS
(
    friends_id integer auto_increment primary key,
    user_id long references USERS(USERS_ID) ON DELETE CASCADE,
    friend_id long references USERS(USERS_ID) ON DELETE CASCADE
);