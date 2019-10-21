DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2019-10-19 08:00:00', 'Завтрак (USER)', 700, 100000),
       ('2019-10-19 13:00:00', 'Обед (USER)', 1000, 100000),
       ('2019-10-19 20:00:00', 'Ужин (USER)', 200, 100000),
       ('2019-10-20 08:00:00', 'Завтрак (USER)', 700, 100000),
       ('2019-10-20 13:00:00', 'Обед (USER)', 900, 100000),
       ('2019-10-20 20:00:00', 'Ужин (USER)', 200, 100000),
       ('2019-10-19 08:00:00', 'Завтрак (ADMIN)', 700, 100001),
       ('2019-10-19 13:00:00', 'Обед (ADMIN)', 1000, 100001),
       ('2019-10-19 20:00:00', 'Ужин (ADMIN)', 200, 100001),
       ('2019-10-20 08:00:00', 'Завтрак (ADMIN)', 700, 100001),
       ('2019-10-20 13:00:00', 'Обед (ADMIN)', 900, 100001),
       ('2019-10-20 20:00:00', 'Ужин (ADMIN)', 200, 100001);