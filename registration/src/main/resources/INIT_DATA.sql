insert into SELLER (SLR_NAME)  values ('Coca Cola'); 
insert into PRODUCT (PRD_SERIAL_NO,PRD_BAR_CODE,PRD_NAME,PRD_CONTENT,SLR_ID) values (12345678,12345678,'Coca Cola Bottle','Aerated drink',1);
insert into MATERIAL (MTL_CODE,MTL_NAME,MTL_RECYCLABLE,MTL_RECYCLE_CODE) values ('PET','Polysterene Terephthalate',1,1);
insert into PRODUCT_MTL (PRD_ID,MTL_ID) VALUES (1,1);
insert into REGISTRATION (REG_DATE, REG_UPDATE_DATE, REG_STATUS, REG_USER, REG_COMMENTS, PRD_ID) VALUES (NOW(),NOW(),'ACTIVE','bdave','Test Registration',1);
