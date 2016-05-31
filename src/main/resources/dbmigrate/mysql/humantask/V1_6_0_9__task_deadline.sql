

-------------------------------------------------------------------------------
--  task deadline
-------------------------------------------------------------------------------
CREATE TABLE TASK_DEADLINE(
	ID BIGINT AUTO_INCREMENT,
	TYPE VARCHAR(100),
	DEADLINE_TIME DATETIME,
	TRIGGER_TIME DATETIME,
	REPEAT_TIMES INT,
	NOTIFICATION_TYPE VARCHAR(200),
	NOTIFICATION_RECEIVER VARCHAR(200),
	NOTIFICATION_TEMPLATE_CODE VARCHAR(200),
	REASSIGNMENT VARCHAR(200),
	OPERATION VARCHAR(200),
	TASK_ID BIGINT,
        CONSTRAINT PK_TASK_DEADLINE PRIMARY KEY(ID),
	CONSTRAINT FK_TASK_DEADLINE_TASK  FOREIGN KEY(TASK_ID) REFERENCES TASK_INFO(ID)
) ENGINE=INNODB CHARSET=UTF8;


