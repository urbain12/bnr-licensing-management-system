ALTER TABLE applications
ADD COLUMN IF NOT EXISTS reference_number VARCHAR(100);

ALTER TABLE applications
ADD COLUMN IF NOT EXISTS institution_name VARCHAR(255);

ALTER TABLE applications
ADD COLUMN IF NOT EXISTS license_type VARCHAR(100);

ALTER TABLE applications
ADD COLUMN IF NOT EXISTS business_address TEXT;

ALTER TABLE applications
ADD COLUMN IF NOT EXISTS submitted_at TIMESTAMP;

ALTER TABLE applications
ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'applications'
        AND column_name = 'applicant_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'applications'
        AND column_name = 'submitted_by'
    ) THEN
        ALTER TABLE applications RENAME COLUMN applicant_id TO submitted_by;
    END IF;
END $$;