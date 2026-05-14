UPDATE excursion_dates SET is_available = true WHERE is_available IS NULL;
ALTER TABLE excursion_dates ALTER COLUMN is_available SET NOT NULL;
ALTER TABLE excursion_dates ALTER COLUMN is_available SET DEFAULT true;