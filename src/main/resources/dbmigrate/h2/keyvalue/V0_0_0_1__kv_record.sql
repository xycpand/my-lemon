

-------------------------------------------------------------------------------
--  kv record
-------------------------------------------------------------------------------
CREATE TABLE KV_RECORD(
        ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
	CATEGORY VARCHAR(200),
	STATUS INT,
	REF VARCHAR(200),
	USER_ID VARCHAR(64),
	CREATE_TIME DATETIME,
	NAME VARCHAR(100),
	FORM_TEMPLATE_CODE VARCHAR(200),
	TENANT_ID VARCHAR(64),
        CONSTRAINT PK_KV_RECORD PRIMARY KEY(ID)
);

COMMENT ON TABLE KV_RECORD IS 'keyvalue存储';
COMMENT ON COLUMN KV_RECORD.ID IS '主键';
COMMENT ON COLUMN KV_RECORD.CATEGORY IS '分类';
COMMENT ON COLUMN KV_RECORD.STATUS IS '状态';
COMMENT ON COLUMN KV_RECORD.REF IS '引用';
COMMENT ON COLUMN KV_RECORD.USER_ID IS '操作人';
COMMENT ON COLUMN KV_RECORD.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN KV_RECORD.NAME IS '名称';
COMMENT ON COLUMN KV_RECORD.FORM_TEMPLATE_CODE IS '表单模板ID';
COMMENT ON COLUMN KV_RECORD.TENANT_ID IS '多租户';

