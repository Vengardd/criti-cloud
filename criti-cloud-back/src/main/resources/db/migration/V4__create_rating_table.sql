CREATE TABLE rating
(
    id                  UUID    NOT NULL,
    user_id             UUID    NOT NULL,
    media_id            UUID    NOT NULL,
    rating              decimal NOT NULL,
    source              text    NOT NULL,
    CONSTRAINT rating_pk PRIMARY KEY (id),
    CONSTRAINT rating_fk_user_id FOREIGN KEY (user_id) REFERENCES user_app(id),
    CONSTRAINT rating_fk_media_id FOREIGN KEY (media_id) REFERENCES media(id)
);
