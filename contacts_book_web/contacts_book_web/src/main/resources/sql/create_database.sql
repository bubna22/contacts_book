CREATE TABLE users (
    user_id bigserial PRIMARY KEY,
    user_login VARCHAR(1024),
    user_pass VARCHAR(1024),
    user_ip VARCHAR(20),
    user_row_version INTEGER DEFAULT 0
);
CREATE UNIQUE INDEX user_login_unique_index ON users (user_login, user_ip);
CREATE TABLE groups (
    group_id bigserial PRIMARY KEY,
    group_name VARCHAR(1024),
    group_color INTEGER DEFAULT NULL
);
CREATE UNIQUE INDEX group_name_unique_index ON groups (group_name);
CREATE TABLE contacts (
    contact_id bigserial PRIMARY KEY,
    contact_name VARCHAR(1024),
    contact_email VARCHAR(1024) DEFAULT NULL,
    contact_telegram VARCHAR(1024) DEFAULT NULL,
    contact_num INTEGER DEFAULT NULL,
    contact_skype VARCHAR(1024)DEFAULT NULL,
    group_id INTEGER DEFAULT NULL
);
CREATE UNIQUE INDEX contact_name_unique_index ON contacts (contact_name);
CREATE TYPE access_type_variations AS ENUM ('owner', 'reader', 'modifier');
CREATE TABLE access_type (
    access_type_id BIGSERIAL PRIMARY KEY,
    access_type_value access_type_variations
);
CREATE TABLE contact_user (
    contact_user_id bigserial PRIMARY KEY,
    contact_id INTEGER REFERENCES contacts(contact_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    contact_user_access_type INTEGER REFERENCES access_type(access_type_id) ON DELETE CASCADE
);
CREATE INDEX contact_user_ids_unique_index ON contact_user (contact_id, user_id);
CREATE TABLE group_user (
    group_user_id bigserial PRIMARY KEY,
    group_id INTEGER REFERENCES groups(group_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    group_user_access_type INTEGER REFERENCES access_type(access_type_id) ON DELETE CASCADE
);
CREATE INDEX group_user_ids_unique_index ON group_user (group_id, user_id);