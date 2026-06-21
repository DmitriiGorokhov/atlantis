INSERT INTO role (name, description) VALUES
    ('ROLE_USER', 'Обычный пользователь'),
    ('ROLE_TEACHER', 'Учитель'),
    ('ROLE_ADMIN', 'Администратор')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permission (name, description) VALUES
    ('LESSON_READ', 'Чтение уроков'),
    ('LESSON_WRITE', 'Создание/редактирование уроков'),
    ('LESSON_DELETE', 'Удаление уроков'),
    ('TEST_READ', 'Чтение тестов'),
    ('TEST_WRITE', 'Создание/редактирование тестов'),
    ('USER_READ', 'Просмотр профилей'),
    ('USER_WRITE', 'Редактирование профилей'),
    ('USER_DELETE', 'Удаление пользователей'),
    ('ADMIN_ACCESS', 'Доступ к админ-панели')
ON CONFLICT (name) DO NOTHING;