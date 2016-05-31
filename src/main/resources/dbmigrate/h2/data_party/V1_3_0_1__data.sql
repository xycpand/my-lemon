

INSERT INTO PARTY_TYPE(ID,NAME,TYPE,TENANT_ID) VALUES(5,'岗位',2,'1');

INSERT INTO PARTY_ENTITY(ID,TYPE_ID,NAME,LEVEL,TENANT_ID) VALUES(15,5,'总经理',2,'1');
INSERT INTO PARTY_ENTITY(ID,TYPE_ID,NAME,LEVEL,TENANT_ID) VALUES(16,5,'经理',1,'1');

INSERT INTO PARTY_STRUCT_RULE(ID,PARENT_TYPE_ID,CHILD_TYPE_ID,STRUCT_TYPE_ID,TENANT_ID) VALUES(10,2,5,1,'1');
INSERT INTO PARTY_STRUCT_RULE(ID,PARENT_TYPE_ID,CHILD_TYPE_ID,STRUCT_TYPE_ID,TENANT_ID) VALUES(11,3,5,1,'1');
INSERT INTO PARTY_STRUCT_RULE(ID,PARENT_TYPE_ID,CHILD_TYPE_ID,STRUCT_TYPE_ID,TENANT_ID) VALUES(12,4 ,5,1,'1');

INSERT INTO PARTY_STRUCT(STRUCT_TYPE_ID,PARENT_ENTITY_ID,CHILD_ENTITY_ID,TENANT_ID,PRIORITY,PART_TIME,LINK,ADMIN) VALUES(1,1,15,'1',0,0,0,1);
INSERT INTO PARTY_STRUCT(STRUCT_TYPE_ID,PARENT_ENTITY_ID,CHILD_ENTITY_ID,TENANT_ID,PRIORITY,PART_TIME,LINK,ADMIN) VALUES(1,5,16,'1',0,0,0,1);

DELETE FROM PARTY_STRUCT where child_entity_id=11;
DELETE FROM PARTY_STRUCT where child_entity_id=12;

INSERT INTO PARTY_STRUCT(STRUCT_TYPE_ID,PARENT_ENTITY_ID,CHILD_ENTITY_ID,TENANT_ID,PRIORITY,PART_TIME,LINK,ADMIN) VALUES(1,15,12,'1',0,0,0,1);
INSERT INTO PARTY_STRUCT(STRUCT_TYPE_ID,PARENT_ENTITY_ID,CHILD_ENTITY_ID,TENANT_ID,PRIORITY,PART_TIME,LINK,ADMIN) VALUES(1,16,11,'1',0,0,0,1);

