CREATE TABLE excursion_dates (
    id              BIGSERIAL PRIMARY KEY,
    excursion_id    BIGINT NOT NULL REFERENCES excursions(id) ON DELETE CASCADE,
    date_time       TIMESTAMP NOT NULL,
    available_slots INTEGER NOT NULL,
    is_available    BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_excursion_dates_excursion ON excursion_dates(excursion_id);