

-------------------------------------------------------------------------------
--  user repo
-------------------------------------------------------------------------------
CREATE TABLE USER_REPO(
        ID BIGINT AUTO_INCREMENT,
	CODE VARCHAR(50),
        NAME VARCHAR(50),
	REFERENCE VARCHAR(50),
	SCOPE_ID VARCHAR(50),
        CONSTRAINT PK_U_USER_REPO PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

