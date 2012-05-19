/* 
	***COMPATIBILIDADE***

	=> ORACLE 11g
	=> MySQL 5.5
*/


/* FINAL RESULTS TABLE */
CREATE TABLE SIMPLE_UNIT_FINAL_RESULTS (
	ID_FINAL_RESULT INTEGER NOT NULL,  /* PK */
	DT_HR_EXECUTION TIMESTAMP,
	TOT_NBR_OF_ASSERTIONS SMALLINT NOT NULL,
	TOT_NBR_OF_PASSED_ASSERTIONS SMALLINT NOT NULL,
	TOT_NBR_OF_FAILED_ASSERTIONS SMALLINT NOT NULL,	
	TOT_NBR_OF_UNITS SMALLINT NOT NULL,
	TOT_NBR_OF_PASSED_UNITS SMALLINT NOT NULL,
	TOT_NBR_OF_FAILED_UNITS SMALLINT NOT NULL,

	PRIMARY KEY (ID_FINAL_RESULT)	
);

/* UNIT RESULTS TABLE */
CREATE TABLE SIMPLE_UNIT_UNIT_RESULTS (
	ID_UNIT_RESULT INTEGER NOT NULL,  /* PK */
	ID_FINAL_RESULT INTEGER NOT NULL, 
	TEST_CASE VARCHAR(100) NOT NULL,
	UNIT_METHOD VARCHAR(250) NOT NULL,
	PASSED CHAR(1) NOT NULL,	
	NBR_OF_ASSERTIONS SMALLINT NOT NULL,
	NBR_OF_PASSED_ASSERTIONS SMALLINT NOT NULL,
	NBR_OF_FAILED_ASSERTIONS SMALLINT NOT NULL,
	EXPECTED_EXCEPTION VARCHAR(200),
	OCCURRED_EXCEPTION VARCHAR(200),
	
	PRIMARY KEY (ID_UNIT_RESULT)
);
/* FK PARA SIMPLE_UNIT_FINAL_RESULTS */
ALTER TABLE SIMPLE_UNIT_UNIT_RESULTS
	ADD CONSTRAINT FK_UNIT_RESULTS_FINAL_RESULTS
		FOREIGN KEY (ID_FINAL_RESULT)
		REFERENCES SIMPLE_UNIT_FINAL_RESULTS (ID_FINAL_RESULT);		
		
/* UNIT ASSERTIONS TABLE */
CREATE TABLE SIMPLE_UNIT_UNIT_ASSERTIONS (
	ID_UNIT_ASSERTION INTEGER NOT NULL,	/* PK */
	ID_UNIT_RESULT INTEGER NOT NULL,   
	ASSERTION_TYPE VARCHAR(30) NOT NULL,
	MESSAGE VARCHAR(500) NOT NULL,
	PASSED CHAR(1) NOT NULL,
	
	PRIMARY KEY (ID_UNIT_ASSERTION)
);
/* FK PARA SIMPLE_UNIT_UNIT_RESULTS */
ALTER TABLE SIMPLE_UNIT_UNIT_ASSERTIONS
	ADD CONSTRAINT FK_UNIT_ASSERTS_UNIT_RESULTS
		FOREIGN KEY (ID_UNIT_RESULT)
		REFERENCES SIMPLE_UNIT_UNIT_RESULTS (ID_UNIT_RESULT);