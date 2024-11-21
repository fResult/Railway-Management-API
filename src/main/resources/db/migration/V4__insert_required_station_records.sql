DO $$
BEGIN
    INSERT INTO stations (id, code, name, location, contact_id)
    VALUES
        (1, 'N24', 'Khu Khot', 'Pathum Thani, Thailand', 3),
        (2, 'W1', 'National Stadium', 'Bangkok, Thailand', 4),
        (3, 'E24', 'Kheha', 'Samut Prakarn, Thailand', 5),
        (4, 'S12', 'Bang Wa', 'Bangkok, Thailand', 6),
        (5, 'N8', 'Mochit', 'Bangkok, Thailand', 7),
        (6, 'E9', 'On Nut', 'Bangkok, Thailand', 8),
        (7, 'S3', 'Saphan Taksin', 'Bangkok, Thailand', 9);
    IF (SELECT last_value FROM stations_id_seq) < 8 THEN
        PERFORM setval('stations_id_seq', 8, false);
    END IF;
END $$;
