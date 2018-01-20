DROP TABLE IF EXISTS appuser;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS password_reset_token;
DROP TABLE IF EXISTS workflow;

CREATE TABLE workflow (
changerequestid BIGSERIAL PRIMARY KEY NOT NULL,
 vendorid  VARCHAR(500),
  assignedgroup VARCHAR(100),
  assigneduser VARCHAR(100),
  status VARCHAR(100),
  stage VARCHAR(100)
);

CREATE TABLE role (
  id   BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(30)           NOT NULL
);

CREATE TABLE appuser (
  id         BIGSERIAL PRIMARY KEY NOT NULL,
  username   VARCHAR(30)           NOT NULL,
  password   VARCHAR(300)          NOT NULL,
  mobile VARCHAR(50),
  telephone VARCHAR(50),
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
INSERT INTO role VALUES (1002, 'BUSINESS_OWNER_VENDOR');
INSERT INTO role VALUES (1003, 'BUSINESS_OWNER_CUSTOMER');
INSERT INTO role VALUES (1004, 'VENDOR');
INSERT INTO role VALUES (1005, 'CUSTOMER');
INSERT INTO role VALUES (1006, 'PO_ADMIN');
INSERT INTO role VALUES (1007, 'PO_ADMIN_MANAGER');
INSERT INTO role VALUES (1008, 'PO_FINANCE');
INSERT INTO role VALUES (1009, 'PO_FINANCE_MANAGER');
INSERT INTO role VALUES (1010, 'PO_TAX');
INSERT INTO role VALUES (1011, 'PO_TAX_MANAGER');
INSERT INTO role VALUES (1012, 'PO_ORG');
INSERT INTO role VALUES (1013, 'PO_ORG_MANAGER');

DROP TABLE IF EXISTS vendor;
DROP TABLE IF EXISTS materialmaster;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS contactpersonmaster;
DROP TABLE IF EXISTS customercontactpersonmaster;
DROP TABLE IF EXISTS  servicesacmaster;
DROP TABLE IF EXISTS attachment;


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

CREATE TABLE attachment(
  id          Bigserial PRIMARY KEY NOT NULL,
  vendorid    VARCHAR(500),
  doctype VARCHAR(50),
  docname VARCHAR(50)
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


CREATE TABLE customercontactpersonmaster(
  id         Bigserial PRIMARY KEY NOT NULL,
  customerid   VARCHAR(500),
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
 railwaystation        VARCHAR(500),
 pan                   VARCHAR(500),
 vatnumber             VARCHAR(500),
 gstregistrationstatus VARCHAR(500),
 noofgstregistration   VARCHAR(500),
 state                 VARCHAR(500),
 gstnumber             VARCHAR(500),
 customerstatus        VARCHAR(50),
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