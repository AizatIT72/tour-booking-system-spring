CREATE TABLE guide_profiles (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    bio        TEXT,
    rating     NUMERIC(3, 2),
    avatar_url VARCHAR(500)
);