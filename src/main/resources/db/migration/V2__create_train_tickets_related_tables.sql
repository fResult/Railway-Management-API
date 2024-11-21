CREATE TABLE IF NOT EXISTS stations (
    id SERIAL PRIMARY KEY,
    code VARCHAR(16) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    contact_id INTEGER NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS train_trips (
    id SERIAL PRIMARY KEY,
    train_number VARCHAR(24) NOT NULL,
    departure_time TIMESTAMP WITH TIME ZONE,
    arrival_time TIMESTAMP WITH TIME ZONE,
    origin_station_id INTEGER NOT NULL REFERENCES stations(id),
    destination_station_id INTEGER NOT NULL REFERENCES stations(id),
    price NUMERIC(4, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    train_trip_id INTEGER NOT NULL REFERENCES train_trips(id),
    passenger_id INTEGER NOT NULL REFERENCES users(id),
    seat_number VARCHAR(16) NOT NULL
);
