

-------------------------------------------------------------------------------
--  job title
-------------------------------------------------------------------------------
CREATE TABLE JOB_TITLE(
        ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(50),
	SCOPE_ID VARCHAR(50),
        CONSTRAINT PK_JOB_TITLE PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

