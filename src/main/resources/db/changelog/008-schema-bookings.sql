CREATE TABLE bookings (
    id                 BIGSERIAL PRIMARY KEY,
    tourist_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    excursion_date_id  BIGINT NOT NULL REFERENCES excursion_dates(id) ON DELETE CASCADE,
    participants_count INTEGER NOT NULL DEFAULT 1,
    total_price        NUMERIC(10, 2) NOT NULL,
    special_requests   TEXT,
    status             VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED'
                              CHECK (status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED')),
    booking_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_bookings_tourist ON bookings(tourist_id);
CREATE INDEX idx_bookings_excursion_date ON bookings(excursion_date_id);