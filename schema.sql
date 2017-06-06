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


DROP TABLE IF EXISTS vendor;
DROP TABLE IF EXISTS materialmaster;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS contactpersonmaster;

CREATE TABLE vendor (
  vendorid  VARCHAR(500) PRIMARY KEY NOT NULL,
 vendorname1 VARCHAR(500),
 vendorname2 VARCHAR(500),
 vendorname3 VARCHAR(500),
 telephonenumberextn VARCHAR(500),
 mobileno VARCHAR(500),
 email VARCHAR(500),
 faxnumberextn VARCHAR(500),
 buildingno VARCHAR(500),
 address1 VARCHAR(500),
 address2 VARCHAR(500),
 address3 VARCHAR(500),
  address4              VARCHAR(500),
  address5              VARCHAR(500),
  city                  VARCHAR(500),
  postcode              VARCHAR(500),
  region                VARCHAR(500),
  country               VARCHAR(500),
  railwaystation        VARCHAR(500),
  accountholdername     VARCHAR(500),
  accountnumber         VARCHAR(500),
  bankname              VARCHAR(500),
  ifsccode              VARCHAR(500),
  branchname            VARCHAR(500),
  branchlocation        VARCHAR(500),
  pan                   VARCHAR(500),
  vatnumber             VARCHAR(500),
  gstregistrationstatus VARCHAR(500),
  noofgstregistration   VARCHAR(500),
  state                 VARCHAR(500),
  gstnumber             VARCHAR(500),
  vendorstatus          VARCHAR(50),
  createdby             VARCHAR(50),
  createdon             TIMESTAMP,
  modifiedby            VARCHAR(50),
  modifiedon            TIMESTAMP,
  submittedby           VARCHAR(500),
  lastsubmittedon       TIMESTAMP,

  approvedby            VARCHAR(500),
  lastapprovedon        TIMESTAMP,
  approvecount          INTEGER,
  approvereason         VARCHAR(500),

  rejectedby            VARCHAR(500),
  lastrejectedon        TIMESTAMP,
  rejectcount           INTEGER,
  rejectreason          VARCHAR(500),

  lastrevertedby        VARCHAR(500),
  lastrevertedon        TIMESTAMP,
  revertcount           INTEGER,
  revertreason          VARCHAR(500),

  isaccepttnc           VARCHAR(2),
  tncacceptedby         VARCHAR(500),
  tncacceptedon         TIMESTAMP,
  sapsyncdate           TIMESTAMP,
  submityn              VARCHAR(1)
);

CREATE TABLE materialmaster(
  id          Bigserial PRIMARY KEY NOT NULL,
  vendorid    VARCHAR(500),
  code        VARCHAR(500),
  description VARCHAR(500),
  hsn         VARCHAR(500)
);

CREATE TABLE servicesacmaster (
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  vendorid    VARCHAR(500),
  code        VARCHAR(500),
  description VARCHAR(500)
);

CREATE TABLE contactpersonmaster(
  id         Bigserial PRIMARY KEY NOT NULL,
  vendorid   VARCHAR(500),
  lastname   VARCHAR(50),
  firstname  VARCHAR(50) ,
  department VARCHAR(50),
  mobile     VARCHAR(50) ,
  telephone  VARCHAR(50) ,
  email      VARCHAR(250)
);

CREATE TABLE customer (
 customerid            VARCHAR(500) PRIMARY KEY NOT NULL,
 customername1         VARCHAR(500),
 customername2         VARCHAR(500),
 customername3         VARCHAR(500),
 contactperson         VARCHAR(500),
 telephonenumberextn   VARCHAR(500),
 mobileno              VARCHAR(500),
 email                 VARCHAR(500),
 faxnumberextn         VARCHAR(500),
 buildingno            VARCHAR(500),
 address1              VARCHAR(500),
 address2              VARCHAR(500),
 address3              VARCHAR(500),
 address4              VARCHAR(500),
 address5              VARCHAR(500),
 city                  VARCHAR(500),
 postcode              VARCHAR(500),
 region                VARCHAR(500),
 country               VARCHAR(500),
 pan                   VARCHAR(500),
 gstregistrationstatus VARCHAR(500),
 noofgstregistration   VARCHAR(500),
 state                 VARCHAR(500),
 gstnumber             VARCHAR(500),
 customerstatus        VARCHAR(50),
 modifiedby            VARCHAR(50),
 modifiedon            TIMESTAMP,
 submittedby           VARCHAR(500),
 lastsubmittedon       TIMESTAMP,
 approvedby            VARCHAR(500),
 lastapprovedon        TIMESTAMP,
 rejectedby            VARCHAR(500),
 lastrejectedon        TIMESTAMP,
 lastrevertedby        VARCHAR(500),
 lastrevertedon        TIMESTAMP,
 revertcount           INTEGER,
 rejectreason          VARCHAR(500),
 revertreason          VARCHAR(500),
 isaccepttnc           VARCHAR(2),
 tncacceptedby         VARCHAR(500),
 tncacceptedon         TIMESTAMP,
 sapsyncdate           TIMESTAMP,
 submityn              VARCHAR(1)
);


