CREATE TABLE IF NOT EXISTS series
(
    id         UUID NOT NULL,
    title      text NOT NULL,
    year       text,
    seasons    integer,
    creator    text,
    plot       text,
    imbd_id    text,
    poster_url text,
    constraint series_pk PRIMARY KEY (id)
);
