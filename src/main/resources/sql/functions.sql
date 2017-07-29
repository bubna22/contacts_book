CREATE TYPE group_type AS (
    group_id INTEGER,
    group_name VARCHAR(1024),
    group_color INTEGER
);

CREATE TYPE contact_type AS (
    contact_id INTEGER,
    contact_name VARCHAR(1024),
    contact_email VARCHAR(1024),
    contact_telegram VARCHAR(1024),
    contact_num INTEGER,
    contact_skype VARCHAR(1024),
    group_id INTEGER
);

CREATE OR REPLACE FUNCTION user_login(var_user_login VARCHAR, var_user_pass VARCHAR, var_ip VARCHAR) RETURNS BOOLEAN AS $$
DECLARE
    temp_login varchar;
    temp_rv integer;
BEGIN
    SELECT user_login, user_row_version
        INTO temp_login, temp_rv
        FROM users
        WHERE user_ip IS NULL AND user_login = var_user_login AND user_pass = var_user_pass;
    IF temp_login IS NULL THEN RAISE EXCEPTION 'Аккаунт уже в системе: %', var_user_login; END IF;
    UPDATE users
        SET user_ip = var_ip, user_row_version = user_row_version + 1
        WHERE temp_rv = user_row_version;
    IF FOUND THEN
        return TRUE;
    ELSE
        return FALSE;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION user_unlogin(var_user_login VARCHAR) RETURNS void AS $$
BEGIN
    UPDATE users
        SET user_ip = NULL
        WHERE user_login = var_user_login;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_access(var_user_login VARCHAR, var_ip VARCHAR) RETURNS void AS $$
DECLARE
    temp_login VARCHAR;
BEGIN
    SELECT user_login
        INTO temp_login
        FROM users
        WHERE user_login = var_user_login AND user_ip = var_ip;
    IF user_login IS NULL THEN RAISE EXCEPTION 'unregistered user %', user_login; END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_modify(var_user_login VARCHAR, var_ip VARCHAR, var_contact contact) RETURNS void AS $$
DECLARE
    temp_contact_name VARCHAR;
    temp_contact_id INTEGER;
    temp_user_id INTEGER;
BEGIN
    IF contact IS NULL THEN RAISE EXCEPTION 'incorrect input'; END IF;
    SELECT check_access(var_user_login, var_ip);
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

CREATE OR REPLACE FUNCTION contact_rem(var_user_login VARCHAR, var_ip VARCHAR, var_contact contact) RETURNS void AS $$
DECLARE
    temp_contact_id INTEGER;
BEGIN
    SELECT check_access(var_user_login, var_ip);
    SELECT contacts.contact_id INTO temp_contact_id FROM contacts WHERE contacts.contact_name = var_contact.contact_name;
    IF temp_contact_id IS NULL THEN RAISE EXCEPTION 'no such contact %', var_user_login; END IF;
    DELETE FROM contacts WHERE contact_id = temp_contact_id;
    DELETE FROM contact_user WHERE contact_id = temp_contact_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION contact_list(var_user_login VARCHAR, var_ip VARCHAR) RETURNS SETOF contact AS $$
DECLARE
    dataReturned contact%rowtype;
BEGIN
    SELECT check_access(var_user_login, var_ip);
    FOR returnValue IN
        SELECT * FROM users WHERE users.group_id = var_contact.group_id
        RETURN NEXT returnValue;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------

CREATE OR REPLACE FUNCTION group_modify(var_user_login VARCHAR, var_ip VARCHAR, var_group group) RETURNS void AS $$
DECLARE
    temp_group_name VARCHAR;
    temp_group_id INTEGER;
    temp_user_id INTEGER;
BEGIN
    IF group IS NULL THEN RAISE EXCEPTION 'incorrect input'; END IF;
    SELECT check_access(var_user_login, var_ip);
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

CREATE OR REPLACE FUNCTION group_rem(var_user_login VARCHAR, var_ip VARCHAR, var_group group) RETURNS void AS $$
DECLARE
    temp_group_id INTEGER;
BEGIN
    SELECT check_access(var_user_login, var_ip);
    SELECT groups.group_id INTO temp_group_id FROM contacts WHERE contacts.contact_name = var_contact.contact_name;
    IF temp_group_id IS NULL THEN RAISE EXCEPTION 'no such group %', var_contact.contact_name; END IF;
    DELETE FROM groups WHERE group_id = temp_group_id;
    DELETE FROM group_user WHERE group_id = temp_group_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION group_list(var_user_login VARCHAR, var_ip VARCHAR) RETURNS SETOF group AS $$
DECLARE
    dataReturned group%rowtype;
BEGIN
    SELECT check_access(var_user_login, var_ip);
    FOR returnValue IN
        SELECT * FROM groups
        LOOP
            RETURN NEXT returnValue;
        END LOOP;
END;
$$ LANGUAGE plpgsql;
