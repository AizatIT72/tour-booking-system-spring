CREATE TABLE excursions_categories (
    excursion_id BIGINT NOT NULL REFERENCES excursions(id) ON DELETE CASCADE,
    category_id  BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (excursion_id, category_id)
);