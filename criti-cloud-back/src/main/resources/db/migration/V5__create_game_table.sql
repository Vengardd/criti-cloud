CREATE TABLE game
(
    id                  UUID    NOT NULL,
    title               text    NOT NULL,
    summary             text,
    igdb_id             text    NOT NULL,
    poster_url          text    NOT NULL,
    CONSTRAINT game_pk PRIMARY KEY (id)
);
