CREATE TABLE subscriptions (
    id            BIGSERIAL PRIMARY KEY,
    tourist_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    guide_id      BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    subscribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (tourist_id, guide_id)
);

CREATE INDEX idx_subscriptions_tourist ON subscriptions(tourist_id);
CREATE INDEX idx_subscriptions_guide ON subscriptions(guide_id);