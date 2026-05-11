ALTER TABLE audit_logs
ADD COLUMN IF NOT EXISTS previous_state VARCHAR(50);

ALTER TABLE audit_logs
ADD COLUMN IF NOT EXISTS new_state VARCHAR(50);

ALTER TABLE audit_logs
ADD COLUMN IF NOT EXISTS comment TEXT;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'audit_logs'
        AND column_name = 'before_state'
    ) THEN
        UPDATE audit_logs
        SET previous_state = before_state
        WHERE previous_state IS NULL;
    END IF;
END $$;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'audit_logs'
        AND column_name = 'after_state'
    ) THEN
        UPDATE audit_logs
        SET new_state = after_state
        WHERE new_state IS NULL;
    END IF;
END $$;