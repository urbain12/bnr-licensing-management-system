CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE applications (
    id UUID PRIMARY KEY,

    applicant_id UUID NOT NULL,
    reviewer_id UUID,
    approver_id UUID,

    status VARCHAR(50) NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_applicant
        FOREIGN KEY (applicant_id)
        REFERENCES users(id)
);

CREATE TABLE documents (
    id UUID PRIMARY KEY,

    application_id UUID NOT NULL,

    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,

    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,

    version_number INTEGER NOT NULL,

    uploaded_by UUID NOT NULL,

    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_document_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id)
);

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,

    application_id UUID NOT NULL,

    actor_id UUID NOT NULL,

    action VARCHAR(100) NOT NULL,

    before_state VARCHAR(50),
    after_state VARCHAR(50),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    metadata JSONB,

    CONSTRAINT fk_audit_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id)
);