

-------------------------------------------------------------------------------
--  meeting item
-------------------------------------------------------------------------------
CREATE TABLE MEETING_ITEM(
        ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(50),
	TENANT_ID VARCHAR(64),
	INFO_ID BIGINT,
        CONSTRAINT PK_MEETING_ITEM PRIMARY KEY(ID),
        CONSTRAINT FK_MEETING_ITEM_INFO FOREIGN KEY(INFO_ID) REFERENCES MEETING_INFO(ID)
) ENGINE=INNODB CHARSET=UTF8;

