DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-26 08:00:00', 'Юзер завтрак', 900, 100000);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-27 14:00:00', 'Юзер обед', 1500, 100000);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-28 19:00:00', 'Юзер ужин', 600, 100000);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-27 07:30:00', 'Админ завтрак', 600, 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-27 13:30:00', 'Админ обед', 1000, 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2016-12-27 18:30:00', 'Админ ужин', 400, 100001);



-- CREATE OR REPLACE FUNCTION load_meal_test_data()
--   RETURNS VOID AS
-- $body$
-- DECLARE
--   v_max     INT DEFAULT 20;
--   v_counter INT DEFAULT 0;
-- BEGIN
--   LOOP
--     EXIT WHEN v_counter >= v_max;
--     INSERT INTO meals (description, calories, user_id, date_time) VALUES (
--         CONCAT('meal_', v_counter)
--       , FLOOR(RANDOM() * 1000) + 1
--       , FLOOR(RANDOM() * 1.1) + 100000
--       , (SELECT TIMESTAMP '2016-01-10 00:00:00' + random() * (current_timestamp - TIMESTAMP '2016-01-01 00:00:00')));
--     v_counter = v_counter + 1;
--   END LOOP;
-- END;
-- $body$ LANGUAGE plpgsql;
--
-- SELECT load_meal_test_data();