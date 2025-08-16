--liquibase formatted sql

--changeset akmal:booking-feature-0
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE UPPER(table_name) = 'EVENTS';
CREATE TABLE USERS
(
    USER_ID        NUMERIC(22) PRIMARY KEY,
    EMAIL          VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD_HASH  VARCHAR(64)         NOT NULL,
    PASSWORD_PLAIN VARCHAR(255), -- For testing purposes only, would not exist in production
    FIRST_NAME     VARCHAR(100)        NOT NULL,
    SURNAME        VARCHAR(100)        NOT NULL,
    BIRTHDAY       DATE,
    REGISTERED_AT  TIMESTAMP           NOT NULL,
    IS_ACTIVE      BOOLEAN             NOT NULL,
    LAST_LOGGED_IN TIMESTAMP           NOT NULL
);
CREATE SEQUENCE USERS_SEQ START WITH 1 INCREMENT BY 2;
CREATE INDEX IDX_USERS_EMAIL ON USERS (EMAIL);
--rollback not required
--comment akmal:booking-feature-0

--changeset akmal:booking-feature-1
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE UPPER(table_name) = 'EVENTS';
CREATE TABLE EVENTS
(
    ID             NUMERIC(22)  NOT NULL PRIMARY KEY,
    TITLE          VARCHAR(128) NOT NULL,
    DESCRIPTION    VARCHAR(512) NOT NULL,
    EVENT_TYPE     VARCHAR(16)  NOT NULL,
    DATETIME_START TIMESTAMP    NOT NULL,
    PROVIDER       VARCHAR(64)  NOT NULL
);
CREATE SEQUENCE EVENTS_SEQ START WITH 1 INCREMENT BY 2;
CREATE INDEX IDX_EVENTS_TITLE ON EVENTS (TITLE); -- todo change index type for full text search
CREATE INDEX IDX_EVENTS_DESCRIPTION ON EVENTS (DESCRIPTION);
--rollback not required
--comment akmal:booking-feature-1

--changeset akmal:booking-feature-2
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE UPPER(table_name) = 'BOOKING';
CREATE TABLE BOOKING
(
    ID       NUMERIC(22) NOT NULL PRIMARY KEY,
    EVENT_ID NUMERIC(22) NOT NULL REFERENCES EVENTS (ID),
    STATUS   VARCHAR(16) NOT NULL,
    USER_ID  NUMERIC(22) NOT NULL REFERENCES USERS (ID)
);
CREATE INDEX IDX_BOOKING_USER_ID ON BOOKING (USER_ID);
CREATE SEQUENCE BOOKING_SEQ START WITH 1 INCREMENT BY 2;
--rollback not required
--comment akmal:booking-feature-2

--changeset akmal:booking-feature-3
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE UPPER(table_name) = 'SEATS';
CREATE TABLE SEATS
(
    ID         NUMERIC(22) NOT NULL PRIMARY KEY,
    ROW        NUMERIC(22) NOT NULL,
    NUMBER     NUMERIC(22) NOT NULL,
    STATUS     VARCHAR(32) NOT NULL,
    BOOKING_ID NUMERIC(22) NOT NULL REFERENCES BOOKING (ID)
);
CREATE SEQUENCE SEATS_SEQ START WITH 1 INCREMENT BY 2;
--rollback not required
--comment akmal:booking-feature-3