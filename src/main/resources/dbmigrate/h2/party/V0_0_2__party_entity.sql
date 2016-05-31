
CREATE TABLE PARTY_ENTITY(
        ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
        TYPE_ID BIGINT,
        NAME VARCHAR(100),
	REF VARCHAR(100),
	SCOPE_ID VARCHAR(50),
        CONSTRAINT PK_PARTY_ENTITY PRIMARY KEY(ID),
        CONSTRAINT FK_PARTY_ENTITY_TYPE FOREIGN KEY(TYPE_ID) REFERENCES PARTY_TYPE(ID)
);

CREATE INDEX I_PARTY_ENTITY_REFERENCE_TYPE ON PARTY_ENTITY(REF, TYPE_ID);

CREATE INDEX I_PARTY_ENTITY_NAME ON PARTY_ENTITY(NAME);
