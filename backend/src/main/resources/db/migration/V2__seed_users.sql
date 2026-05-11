INSERT INTO users (
    id,
    email,
    password,
    full_name,
    role
)
VALUES
(
    gen_random_uuid(),
    'applicant@bnr.rw',
    'password',
    'Test Applicant',
    'APPLICANT'
),
(
    gen_random_uuid(),
    'reviewer@bnr.rw',
    'password',
    'Test Reviewer',
    'REVIEWER'
),
(
    gen_random_uuid(),
    'approver@bnr.rw',
    'password',
    'Test Approver',
    'APPROVER'
),
(
    gen_random_uuid(),
    'auditor@bnr.rw',
    'password',
    'Auditor Officer',
    'COMPLIANCE_OFFICER'
),
(
    gen_random_uuid(),
    'admin@bnr.rw',
    'password',
    'System Admin',
    'ADMIN'
);