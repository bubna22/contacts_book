DROP SCHEMA contacts_book IF EXISTS CASCADE;
CREATE SCHEMA contact_book;
SET SCHEMA 'contacts_book';

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_login VARCHAR(1024) UNIQUE,
    user_pass VARCHAR(1024),
    user_ip VARCHAR(20),
    user_row_version INTEGER
);

CREATE TABLE groups (
    group_id BIGSERIAL PRIMARY KEY,
    group_name VARCHAR(1024) UNIQUE,
    group_color INTEGER DEFAULT -1
);

CREATE TABLE contacts (
    contact_id BIGSERIAL PRIMARY KEY,
    contact_name VARCHAR(1024) UNIQUE,
    contact_email VARCHAR(1024) DEFAULT NULL,
    contact_telegram VARCHAR(1024) DEFAULT NULL,
    contact_num INTEGER DEFAULT -1,
    contact_skype VARCHAR(1024)DEFAULT NULL,
    group_id INTEGER DEFAULT -1
);

CREATE TABLE contact_user (
    contact_user_id BIGSERIAL PRIMARY KEY,
    contact_id INTEGER REFERENCES contacts(contact_id),
    user_id INTEGER REFERENCES users(user_id),
    contact_user_access_type VARCHAR(10) DEFAULT 'owner'
);

CREATE TABLE group_user (
    group_user_id BIGSERIAL PRIMARY KEY,
    group_id INTEGER REFERENCES groups(group_id),
    user_id INTEGER REFERENCES users(user_id),
    group_user_access_type VARCHAR(10) DEFAULT 'owner'
);


