

-------------------------------------------------------------------------------
--  job grade
-------------------------------------------------------------------------------
CREATE TABLE JOB_GRADE(
        ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(50),
	SCOPE_ID VARCHAR(50),
        CONSTRAINT PK_JOB_GRADE PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

