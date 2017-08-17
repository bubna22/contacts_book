INSERT INTO users(user_login, user_pass, user_ip) values('bubna', 'test', '1');

INSERT INTO groups(group_name) values('test');
INSERT INTO contacts(contact_name, group_id) values('bubna', 1);

INSERT INTO contact_user(contact_id, user_id) values(1, 1);
INSERT INTO group_user(group_id, user_id) values(1, 1);