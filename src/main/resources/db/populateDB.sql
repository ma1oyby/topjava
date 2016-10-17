DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, user_id) VALUES
  ('Breakfast', 500, 100000),
  ('Lunch', 1000, 100000),
  ('Dinner', 500, 100000),
  ('Breakfast', 450, 100001),
  ('Breakfast', 1250, 100001),
  ('Breakfast', 555, 100001);