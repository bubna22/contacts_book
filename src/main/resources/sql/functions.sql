CREATE TYPE group_type AS (
    group_name VARCHAR(1024),
    group_color INTEGER
);

CREATE TYPE contact_type AS (
    contact_name VARCHAR(1024),
    contact_email VARCHAR(1024),
    contact_telegram VARCHAR(1024),
    contact_num INTEGER,
    contact_skype VARCHAR(1024),
    group_id INTEGER
);

CREATE TYPE user_type AS (
    user_login VARCHAR(1024),
    user_pass VARCHAR(1024),
    user_ip VARCHAR(20),
    user_row_version INTEGER
);


CREATE OR REPLACE FUNCTION user_login(var_user user_type) RETURNS BOOLEAN AS $$
DECLARE
    temp_login varchar;
    temp_rv integer;
BEGIN
    SELECT user_login, user_row_version
        INTO temp_login, temp_rv
        FROM users
        WHERE user_ip IS NULL AND user_login = var_user.user_login AND user_pass = var_user.user_pass;
    IF temp_login IS NULL THEN RAISE EXCEPTION 'Аккаунт уже в системе: %', var_user_login; END IF;
    UPDATE users
        SET user_ip = var_user.user_ip, user_row_version = user_row_version + 1
        WHERE temp_rv = user_row_version;
    IF FOUND THEN
        return TRUE;
    ELSE
        return FALSE;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION user_unlogin(var_user user_type) RETURNS void AS $$
BEGIN
    UPDATE users
        SET user_ip = NULL
        WHERE user_login = var_user.user_login;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_access(var_user user_type) RETURNS void AS $$
DECLARE
    temp_login VARCHAR;
BEGIN
    SELECT user_login
        INTO temp_login
        FROM users
        WHERE user_login = var_user.user_login AND user_ip = var_user.user_ip;
    IF temp_login IS NULL THEN RAISE EXCEPTION 'unregistered user %', user_login; END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_modify(var_user user_type, var_contact_name VARCHAR, var_contact contact_type) RETURNS void AS $$
DECLARE
    temp_contact_name VARCHAR;
    temp_contact_id INTEGER;
    temp_user_id INTEGER;
BEGIN
    IF contact IS NULL THEN RAISE EXCEPTION 'incorrect input'; END IF;
    PERFORM check_access(var_contact);
    SELECT contacts.contact_name INTO temp_contact_name FROM contacts WHERE contacts.contact_name = var_contact.contact_name;
    IF temp_contact_name IS NULL THEN
        INSERT INTO contacts (contact_name, contact_email, contact_telegram, contact_num, contact_skype, group_id)
        values(var_contact.contact_name, var_contact.contact_email, var_contact.contact_telegram,
        var_contact.contact_num, var_contact.contact_skype, var_contact.group_id) RETURNING contact_id INTO temp_contact_id;
        SELECT user_id INTO temp_user_id FROM users WHERE user_login = var_user_login;
        INSERT INTO contact_user (contact_id, user_id) values (temp_contact_id, temp_user_id);
    ELSE
        UPDATE contacts
        SET contacts.contact_email = var_contact.contact_email, contacts.contact_telegram = var_contact.contact_telegram,
        contacts.contact_num = var_contact.contact_num, contacts.contact_skype = var_contact.contact_skype, contacts.group_id = var_contact.group_id
        WHERE contacts.contact_name = var_contact.contact_name;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_rem(var_user user_type, var_contact_name VARCHAR, var_contact contact_type) RETURNS void AS $$
DECLARE
    temp_contact_id INTEGER;
BEGIN
    PERFORM check_access(var_contact);
    SELECT contacts.contact_id INTO temp_contact_id FROM contacts WHERE contacts.contact_name = var_contact.contact_name;
    IF temp_contact_id IS NULL THEN RAISE EXCEPTION 'no such contact %', var_user_login; END IF;
    DELETE FROM contacts WHERE contact_id = temp_contact_id;
    DELETE FROM contact_user WHERE contact_id = temp_contact_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_list(var_user user_type) RETURNS SETOF contact_type AS $$
DECLARE
    dataReturned contact_type%rowtype;
BEGIN
    PERFORM check_access(var_user);
    FOR dataReturned IN
        SELECT * as data FROM contacts
            JOIN contact_user ON contact_user.contact_id = contacts.contact_id
            JOIN users ON users.user_id = contact_user.user_id
        LOOP
            RETURN NEXT ROW(dataReturned);
        END LOOP;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------

CREATE OR REPLACE FUNCTION group_modify(var_user user_type, var_group_name VARCHAR, var_group group_type) RETURNS void AS $$
DECLARE
    temp_group_name VARCHAR;
    temp_group_id INTEGER;
    temp_user_id INTEGER;
BEGIN
    IF var_group IS NULL THEN RAISE EXCEPTION 'incorrect input'; END IF;
    PERFORM check_access(var_user);
    SELECT groups.group_name INTO temp_group_name FROM groups WHERE groups.contact_name = var_group.contact_name;
    IF temp_group_name IS NULL THEN
        INSERT INTO groups (group_name, group_color) values(var_group.group_name, var_group.group_color) RETURNING group_id INTO temp_group_id;
        SELECT user_id INTO temp_user_id FROM users WHERE user_login = var_user_login;
        INSERT INTO group_user (group_id, user_id) values (temp_group_id, temp_user_id);
    ELSE
        UPDATE groups
            SET groups.group_color = var_group.group_color
            WHERE groups.group_name = var_group.group_name;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION group_rem(var_user user_type, var_group_name VARCHAR, var_group group_type) RETURNS void AS $$
DECLARE
    temp_group_id INTEGER;
BEGIN
    PERFORM check_access(var_user_login, var_ip);
    SELECT groups.group_id INTO temp_group_id FROM contacts WHERE contacts.contact_name = var_contact.contact_name;
    IF temp_group_id IS NULL THEN RAISE EXCEPTION 'no such group %', var_contact.contact_name; END IF;
    DELETE FROM groups WHERE group_id = temp_group_id;
    DELETE FROM group_user WHERE group_id = temp_group_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION group_list(var_user user_type) RETURNS SETOF group_type AS $$
DECLARE
    dataReturned group_type%rowtype;
BEGIN
    PERFORM check_access(var_user);
    FOR dataReturned IN
        SELECT * as data FROM groups
            JOIN group_user ON group_user.group_id = groups.group_id
            JOIN users ON users.user_id = group_user.user_id
        LOOP
            RETURN NEXT ROW(dataReturned);
        END LOOP;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------

CREATE OR REPLACE FUNCTION get_users_count() RETURNS INTEGER AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT count(*)
        INTO count
        FROM users;
    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_contacts_count(var_user_login VARCHAR) RETURNS INTEGER AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT count(*)
        INTO count
        FROM contacts
        JOIN contact_user ON contact_user.contact_id = contacts.contact_id
        JOIN users ON users.user_id = contact_user.user_id AND users.user_login = var_user_login;
    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_groups_count(var_user_login VARCHAR) RETURNS INTEGER AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT count(*)
        INTO count
        FROM groups
        JOIN group_user ON group_user.group_id = groups.group_id
        JOIN users ON users.user_id = contact_user.user_id AND users.user_login = var_user_login;
    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_avg_users_in_group_count() RETURNS INTEGER AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT avg(count_usrs)
        INTO count
        FROM (SELECT count(user_id) as count_usrs FROM group_user GROUP BY group_id) pr;
    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_avg_contacts_by_user_count() RETURNS INTEGER AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT avg(count_cntcts)
        INTO count
        FROM (SELECT count(contact_id) as count_cntcts FROM contact_user GROUP BY user_id) pr;
    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_inactive_users() RETURNS SETOF user_type AS $$
DECLARE
    dataReturned user_type%rowtype;
BEGIN
    FOR dataReturned IN
        SELECT * FROM users WHERE user_id IN (SELECT user_id FROM users
            JOIN contact_user ON contact_user.user_id = users.user_id
            GROUP BY user_id
            HAVING count(contact_user.contact_id) < 10)
        LOOP
            RETURN NEXT dataReturned;
        END LOOP;
END;
$$ LANGUAGE plpgsql;

