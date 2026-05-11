
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE permissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE role_permissions (
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,

    PRIMARY KEY (role_id, permission_id),

    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id),

    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id)
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
);

CREATE TABLE application_reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    application_id UUID NOT NULL,
    reviewer_id UUID NOT NULL,

    comments TEXT,

    reviewed_at TIMESTAMP,

    CONSTRAINT fk_review_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id),

    CONSTRAINT fk_review_user
        FOREIGN KEY (reviewer_id)
        REFERENCES users(id)
);

CREATE TABLE application_approvals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    application_id UUID NOT NULL,
    approver_id UUID NOT NULL,

    decision VARCHAR(50) NOT NULL,
    comments TEXT,

    decided_at TIMESTAMP,

    CONSTRAINT fk_approval_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id),

    CONSTRAINT fk_approval_user
        FOREIGN KEY (approver_id)
        REFERENCES users(id)
);

CREATE TABLE application_documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    application_id UUID NOT NULL,

    document_type VARCHAR(100) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_app_document_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id)
);

CREATE TABLE application_document_versions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    document_id UUID NOT NULL,

    version_number INTEGER NOT NULL,

    original_filename VARCHAR(255) NOT NULL,
    stored_filename VARCHAR(255) NOT NULL,

    file_path TEXT NOT NULL,

    mime_type VARCHAR(100) NOT NULL,

    file_size BIGINT NOT NULL,

    uploaded_by UUID NOT NULL,

    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_document_version_document
        FOREIGN KEY (document_id)
        REFERENCES application_documents(id),

    CONSTRAINT fk_document_version_user
        FOREIGN KEY (uploaded_by)
        REFERENCES users(id)
);