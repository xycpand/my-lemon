

-------------------------------------------------------------------------------
--  account lock info
-------------------------------------------------------------------------------
CREATE TABLE ACCOUNT_LOCK_INFO(
        ID BIGINT AUTO_INCREMENT,
	TYPE VARCHAR(200),
	USERNAME VARCHAR(64),
	LOCK_TIME DATETIME,
	RELEASE_TIME DATETIME,
        CONSTRAINT PK_ACCOUNT_LOCK_INFO PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

