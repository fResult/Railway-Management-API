INSERT INTO roles (id, name)
VALUES (1, 'ADMIN'), (2, 'STATION_MASTER'), (3, 'PASSENGER');
SELECT setval('roles_id_seq', 4, false);

INSERT INTO users (id, first_name, last_name, email, password)
VALUES
    (1, 'John', 'Wick', 'john.w@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (2, 'John', 'Doe', 'john.d@gmail.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (3, 'Alice', 'Wonderland', 'alice.w@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (4, 'Bob', 'Builder', 'bob.b@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (5, 'Charlie', 'Brown', 'charlie.b@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (6, 'Dora', 'Explorer', 'dorah.e@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (7, 'Eve', 'Joana', 'eve.j@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (8, 'Frank', 'Explorer', 'frank.e@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc'),
    (9, 'Grace', 'Hopper', 'grace.h@lightningrails.com', '$argon2id$v=19$m=16384,t=2,p=1$m5O75FZ/Q4ZP7a4MeQSBSg$PspFiPmLYsLTzsISDp1rMqhbHcTid9RFEiMfPkOxekc');
SELECT setval('users_id_seq', 10, false);
--DROP SEQUENCE users_id_seq CASCADE;
--CREATE SEQUENCE users_id_seq START WITH 9 INCREMENT BY 1;
--ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');

INSERT INTO user_roles (id, user_id, role_id)
VALUES (1, 1, 1), (2, 2, 3), (3, 3, 2), (4, 4, 2), (5, 5, 2), (6, 6, 2);
SELECT setval('user_roles_id_seq', 7, false);
