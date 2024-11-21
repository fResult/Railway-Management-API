CREATE TABLE IF NOT EXISTS stations (
    id SERIAL PRIMARY KEY,
    code VARCHAR(16) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    contact_information VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS train_trips (
    id SERIAL PRIMARY KEY,
    train_number VARCHAR(30) NOT NULL,
    departure_time TIMESTAMP WITH TIME ZONE,
    arrival_time TIMESTAMP WITH TIME ZONE,
    origin_station_id INTEGER NOT NULL REFERENCES stations(id),
    destination_station_id INTEGER UNIQUE NOT NULL REFERENCES stations(id),
    ticket_price NUMERIC(4, 2) NOT NULL
);
