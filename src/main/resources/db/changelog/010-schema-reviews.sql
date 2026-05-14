CREATE TABLE reviews (
    id           BIGSERIAL PRIMARY KEY,
    author_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    excursion_id BIGINT NOT NULL REFERENCES excursions(id) ON DELETE CASCADE,
    booking_id   BIGINT REFERENCES bookings(id) ON DELETE SET NULL,
    rating       INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment      TEXT,
    guide_reply  TEXT,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reviews_excursion ON reviews(excursion_id);
CREATE INDEX idx_reviews_author ON reviews(author_id);