DO $$
BEGIN
    INSERT INTO roles (id, name)
    VALUES (1, 'ADMIN'), (2, 'STATION_STAFF'), (3, 'PASSENGER');
     IF (SELECT last_value FROM roles_id_seq) < 4 THEN
        PERFORM setval('roles_id_seq', 4, false);
    END IF;
END $$;

DO $$
BEGIN
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

    IF (SELECT last_value FROM users_id_seq) < 10 THEN
        PERFORM setval('users_id_seq', 10, false);
    END IF;
END $$;

DO $$
BEGIN
    INSERT INTO user_roles (id, user_id, role_id)
    VALUES (1, 1, 1), (2, 2, 3), (3, 3, 1), (4, 3, 2), (5, 4, 2), (6, 5, 2), (7, 6, 2), (8, 7, 2), (9, 8, 2), (10, 9, 2);
    IF (SELECT last_value FROM user_roles_id_seq) < 11 THEN
        PERFORM setval('user_roles_id_seq', 11, false);
    END IF;
END $$;
