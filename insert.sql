DROP TABLE IF EXISTS appuser;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS password_reset_token;

CREATE TABLE role (
  id   BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(30)           NOT NULL
);

CREATE TABLE appuser (
  id         BIGSERIAL PRIMARY KEY NOT NULL,
  username   VARCHAR(30)           NOT NULL,
  password   VARCHAR(300)          NOT NULL,
  email      VARCHAR(300),
  lastlogin  TIMESTAMP,
  userstatus VARCHAR(20)
);

CREATE TABLE user_role (
  user_id INT,
  role_id INT
);

CREATE TABLE password_reset_token (
  id         BIGSERIAL PRIMARY KEY NOT NULL,
  userid     VARCHAR(30)           NOT NULL,
  token      VARCHAR(100)          NOT NULL,
  expirydate TIMESTAMP
);

INSERT INTO role VALUES (1001, 'ADMIN');
INSERT INTO role VALUES (1002, 'BUSINESS_OWNER');
INSERT INTO role VALUES (1003, 'VENDOR');

