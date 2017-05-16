DROP TABLE IF EXISTS vendor;
DROP TABLE IF EXISTS appuser;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;

CREATE TABLE vendor (
  vendorid  VARCHAR(500) PRIMARY KEY NOT NULL,
 vendorname1 VARCHAR(500),
 vendorname2 VARCHAR(500),
 vendorname3 VARCHAR(500),
 contactperson VARCHAR(500),
 telephonenumberextn VARCHAR(500),
 mobileno VARCHAR(500),
 email VARCHAR(500),
 faxnumberextn VARCHAR(500),
 buildingno VARCHAR(500),
 address1 VARCHAR(500),
 address2 VARCHAR(500),
 address3 VARCHAR(500),
 address4 VARCHAR(500),
 address5 VARCHAR(500),
 city VARCHAR(500),
 postcode VARCHAR(500),
 region VARCHAR(500),
 country VARCHAR(500),
 railwaystation VARCHAR(500),
 accountholdername VARCHAR(500),
 accountnumber VARCHAR(500),
 bankname VARCHAR(500),
 ifsccode VARCHAR(500),
 branchname VARCHAR(500),
 branchlocation VARCHAR(500),
 pan VARCHAR(500),
 vatnumber VARCHAR(500),
 gstregistrationstatus VARCHAR(500),
 noofgstregistration VARCHAR(500),
 state VARCHAR(500),
 gstnumber VARCHAR(500),
 materialcode VARCHAR(500),
 materialdescription VARCHAR(500),
 hsn  VARCHAR(500)

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
