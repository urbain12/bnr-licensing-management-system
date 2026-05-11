-- Password for all default users: password123
UPDATE users
SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE email IN (
    'applicant@bnr.rw',
    'reviewer@bnr.rw',
    'approver@bnr.rw'
);