DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO roles (id, name, description)
VALUES
(gen_random_uuid(), 'APPLICANT', 'Application submitter'),
(gen_random_uuid(), 'REVIEWER', 'Application reviewer'),
(gen_random_uuid(), 'APPROVER', 'Final approver'),
(gen_random_uuid(), 'ADMIN', 'System administrator');

INSERT INTO permissions (id, name, description)
VALUES
(gen_random_uuid(), 'CREATE_APPLICATION', 'Create application'),
(gen_random_uuid(), 'VIEW_OWN_APPLICATION', 'View own applications'),

(gen_random_uuid(), 'REVIEW_APPLICATION', 'Review applications'),
(gen_random_uuid(), 'REQUEST_INFORMATION', 'Request more information'),
(gen_random_uuid(), 'VIEW_ASSIGNED_APPLICATIONS', 'View assigned applications'),

(gen_random_uuid(), 'APPROVE_APPLICATION', 'Approve application'),
(gen_random_uuid(), 'REJECT_APPLICATION', 'Reject application'),

(gen_random_uuid(), 'MANAGE_USERS', 'Manage users'),
(gen_random_uuid(), 'VIEW_ALL', 'View everything');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON (
       (r.name = 'APPLICANT' AND p.name IN (
            'CREATE_APPLICATION',
            'VIEW_OWN_APPLICATION'
       ))

    OR (r.name = 'REVIEWER' AND p.name IN (
            'REVIEW_APPLICATION',
            'REQUEST_INFORMATION',
            'VIEW_ASSIGNED_APPLICATIONS'
       ))

    OR (r.name = 'APPROVER' AND p.name IN (
            'APPROVE_APPLICATION',
            'REJECT_APPLICATION'
       ))

    OR (r.name = 'ADMIN')
);