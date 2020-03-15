

CREATE TABLE IF NOT EXISTS SELLER (
	SLR_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	SLR_NAME VARCHAR(100) NOT NULL, 
	SLR_LEI VARCHAR(100)); 
	

CREATE TABLE IF NOT EXISTS MATERIAL (
	MTL_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	MTL_CODE VARCHAR(6) NOT NULL, 	
	MTL_NAME VARCHAR(200) NOT NULL, 
	MTL_RECYCLABLE TINYINT NOT NULL, 
	MTL_RECYCLE_CODE TINYINT, 
	MTL_TYPE VARCHAR(100), 
	MTL_SUB_TYPE_GROUP VARCHAR(100), 
	MTL_SUB_TYPE VARCHAR(100)
); 	

CREATE TABLE IF NOT EXISTS PRODUCT (
	PRD_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	PRD_SERIAL_NO BIGINT NOT NULL, 
	PRD_BAR_CODE BIGINT NOT NULL, 
	PRD_NAME VARCHAR(200) NOT NULL,
	PRD_CONTENT VARCHAR(250), 
	SLR_ID INT, 
	FOREIGN KEY (SLR_ID) REFERENCES SELLER(SLR_ID) 
);

CREATE TABLE IF NOT EXISTS PRODUCT_MTL (
	PRD_MTL_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	PRD_ID INT NOT NULL, 
	MTL_ID INT NOT NULL,
	FOREIGN KEY (PRD_ID) REFERENCES PRODUCT(PRD_ID), 
	FOREIGN KEY (MTL_ID) REFERENCES MATERIAL(MTL_ID)
);

CREATE TABLE IF NOT EXISTS REGISTRATION (
	REG_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	REG_DATE TIMESTAMP NOT NULL, 
	REG_UPDATE_DATE TIMESTAMP NOT NULL, 
	REG_STATUS VARCHAR(20) NOT NULL, 
	REG_USER VARCHAR(100) NOT NULL, 
	REG_COMMENTS VARCHAR(200), 
	PRD_ID INT NOT NULL, 
	FOREIGN KEY(PRD_ID) REFERENCES PRODUCT(PRD_ID)
);

