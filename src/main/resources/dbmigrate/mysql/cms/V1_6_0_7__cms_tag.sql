

-------------------------------------------------------------------------------
--  cms tag
-------------------------------------------------------------------------------
CREATE TABLE CMS_TAG(
        ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(50),
	COUNT_ARTICLE INT,
	TENANT_ID VARCHAR(64),
        CONSTRAINT PK_CMS_TAG PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

