-- Password for all seeded users is: password123
-- BCrypt hash for password123
-- You can still change passwords later using the API if needed.

INSERT INTO users (
    id,
    email,
    password,
    full_name,
    active,
    created_at
)
VALUES
(
    gen_random_uuid(),
    'applicant@bnr.rw',
    '$2a$10$9q7CLaXOkKHIyM0Q8FKTZuQwZnRzP5VY71nsnQMQ5ugEQLSmabOcK',
    'Test Applicant',
    true,
    NOW()
),
(
    gen_random_uuid(),
    'reviewer@bnr.rw',
    '$2a$10$9q7CLaXOkKHIyM0Q8FKTZuQwZnRzP5VY71nsnQMQ5ugEQLSmabOcK',
    'Test Reviewer',
    true,
    NOW()
),
(
    gen_random_uuid(),
    'approver@bnr.rw',
    '$2a$10$9q7CLaXOkKHIyM0Q8FKTZuQwZnRzP5VY71nsnQMQ5ugEQLSmabOcK',
    'Test Approver',
    true,
    NOW()
)
ON CONFLICT (email) DO NOTHING;


INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON (
       (u.email = 'applicant@bnr.rw' AND r.name = 'APPLICANT')
    OR (u.email = 'reviewer@bnr.rw' AND r.name = 'REVIEWER')
    OR (u.email = 'approver@bnr.rw' AND r.name = 'APPROVER')
)
ON CONFLICT DO NOTHING;