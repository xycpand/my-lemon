

-------------------------------------------------------------------------------
--  car info
-------------------------------------------------------------------------------
CREATE TABLE CAR_INFO(
        ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(200),
	CODE VARCHAR(200),
	STATUS INTEGER,
	WEIGHT INTEGER,
	PEOPLE INTEGER,
	TENANT_ID VARCHAR(64),
        CONSTRAINT PK_CAR_INFO PRIMARY KEY(ID)
) ENGINE=INNODB CHARSET=UTF8;

