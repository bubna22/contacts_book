INSERT INTO users(user_login, user_pass, user_ip) values('bubna', 'test', NULL);

INSERT INTO groups(group_name) values('test');
INSERT INTO contacts(contact_name) values('bubna');

INSERT INTO contact_user(contact_id, user_id) values(1, 1);
INSERT INTO group_user(group_id, user_id) values(1, 1);