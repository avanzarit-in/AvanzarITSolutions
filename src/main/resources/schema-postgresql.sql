DROP TABLE IF EXISTS vendor;
DROP TABLE IF EXISTS appuser;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;

CREATE TABLE vendor (
  id Bigserial PRIMARY KEY NOT NULL,
  accno VARCHAR(10),
  country  VARCHAR(3),
  name VARCHAR(35),
  email VARCHAR(250),
  address VARCHAR(250),
  postcode VARCHAR(6),
  city VARCHAR(3),
  region VARCHAR(3),
  pobox VARCHAR(6),
  popostalcode VARCHAR(6),
  copostalcode VARCHAR(6),
  language VARCHAR(2),
  telephone VARCHAR(10),
  phext VARCHAR(4),
  fxext VARCHAR(4),
  mobile VARCHAR(10),
  faxno VARCHAR(10),
  dataline VARCHAR(10),
  telebox VARCHAR(10)
);

CREATE TABLE role (
 id Bigserial PRIMARY KEY NOT NULL,
 name VARCHAR(30) NOT NULL
);

CREATE TABLE appuser (
 id Bigserial PRIMARY KEY NOT NULL,
 username VARCHAR(30) NOT NULL,
 password VARCHAR(300) NOT NULL,
 passwordConfirmation VARCHAR(300)
);

CREATE TABLE user_role (
  user_id INT,
  role_id INT
);
