CREATE TABLE movie
(
    id              UUID    NOT NULL,
    title           text    NOT NULL,
    year            integer,
    runtime         integer,
    director        text,
    plot            text,
    imbd_id         text,
    poster_url      text,
    CONSTRAINT movie_pk PRIMARY KEY (id)
);
