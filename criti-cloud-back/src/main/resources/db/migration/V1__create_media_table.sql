CREATE TABLE media
(
    id          UUID    NOT NULL,
    name        text    NOT NULL,
    details_type        text    NOT NULL,
    details_id  UUID    NOT NULL,
    CONSTRAINT media_pk PRIMARY KEY (id)
);

ALTER TABLE media
ADD CONSTRAINT media_type_enum_check
CHECK (details_type in ('MOVIE', 'SERIES'))