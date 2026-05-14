CREATE TABLE excursions (
    id             BIGSERIAL PRIMARY KEY,
    guide_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    price          NUMERIC(10, 2) NOT NULL,
    max_group_size INTEGER NOT NULL,
    city           VARCHAR(100),
    duration_hours INTEGER,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_excursions_guide ON excursions(guide_id);
CREATE INDEX idx_excursions_city ON excursions(city);