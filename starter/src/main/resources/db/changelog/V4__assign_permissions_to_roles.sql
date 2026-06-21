-- ROLE_USER
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'ROLE_USER'
  AND p.name IN ('LESSON_READ', 'TEST_READ', 'USER_READ', 'USER_WRITE')
ON CONFLICT DO NOTHING;

-- ROLE_TEACHER
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'ROLE_TEACHER'
  AND p.name IN ('LESSON_READ', 'LESSON_WRITE', 'TEST_READ', 'TEST_WRITE', 'USER_READ')
ON CONFLICT DO NOTHING;

-- ROLE_ADMIN (все привилегии)
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- Тестовый администратор
INSERT INTO users (username, email, password_hash, role_id)
SELECT
    'admin',
    'admin@atlantis.com',
    '$2a$12$ZFnQYTRfD9XjKE4KCHVWcO5H2q5vzGzP0M2C3TlF8Xk5R7hJ6p0Aq',
    (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Тестовый пользователь
INSERT INTO users (username, email, password_hash, role_id)
SELECT
    'user',
    'user@atlantis.com',
    '$2a$12$ZFnQYTRfD9XjKE4KCHVWcO5H2q5vzGzP0M2C3TlF8Xk5R7hJ6p0Aq',
    (SELECT id FROM role WHERE name = 'ROLE_USER')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

-- Тестовый учитель
INSERT INTO users (username, email, password_hash, role_id)
SELECT
    'teacher',
    'teacher@atlantis.com',
    '$2a$12$ZFnQYTRfD9XjKE4KCHVWcO5H2q5vzGzP0M2C3TlF8Xk5R7hJ6p0Aq',
    (SELECT id FROM role WHERE name = 'ROLE_TEACHER')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'teacher');