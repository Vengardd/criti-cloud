ALTER TABLE user_app ADD COLUMN email VARCHAR(255) NOT NULL;
ALTER TABLE user_app ADD COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE user_app ADD CONSTRAINT uk_user_app_email UNIQUE (email);
