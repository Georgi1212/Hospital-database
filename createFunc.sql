SET SCHEMA FN72039;

--ПО ПОДАДЕНО ИМЕ НА ОТДЕЛЕНИЕ, ДА СЕ ИЗВЕДАТ БРОЯ НА СЛУЖИТЕЛИТЕ В СЪОТВЕТНОТО ОТДЕЛЕНИЕ И
--СРЕДНАТА ЗАПЛАТА В ТОВА ОТДЕЛЕНИЕ:
CREATE OR REPLACE FUNCTION HOSPITAL_DEPARTMENT_NUMBEREMP_AVGSAL(IN P_DEPTNAME VARCHAR(20))
RETURNS TABLE (DEPTNAME VARCHAR(20), NUMBER_EMP INT, AVG_SAL DECIMAL(9,2))
RETURN
    SELECT DEPARTMENTNAME, COUNT(*), AVG(SALARY)
    FROM STAFF
    WHERE DEPARTMENTNAME = P_DEPTNAME
    GROUP BY DEPARTMENTNAME;

SELECT *
FROM TABLE(FN72039.HOSPITAL_DEPARTMENT_NUMBEREMP_AVGSAL('ANASTEZIOLOGIQ')) T;


--ПО ПОДАДЕНО ЕГН НА ПАЦИЕНТ, ДА СЕ ИЗВЕДЕ ДАТАТА, НА КОЯТО МУ Е ПРАВЕНА НЯКАКВА ПРОЦЕДУРА, ИМЕТО НА ПРОЦЕДУРАТА
-- И ИМЕ НА ПОСТАВЕНА ПРЕДИ ТОВА ДИАГНОЗА:
CREATE OR REPLACE FUNCTION PROCEDURES_DATA_PATIENT(IN P_EGN CHAR(10))
RETURNS TABLE (EGN CHAR(10), DATE_PROCEDURE DATE, TYPE_PROCEDURE VARCHAR(10), NAME_DIAGNOSIS VARCHAR(15))
RETURN
    SELECT EGN, DATEPROCEDURE, TYPEOFPROCEDURE, DIAGNOSISNAME
    FROM HISTORYOFPROCEDURES
    WHERE EGN = P_EGN;


SELECT *
FROM TABLE(FN72039.PROCEDURES_DATA_PATIENT('8903045614')) T;


--ПО НОМЕР НА ДОКТОР, ДА СЕ ИЗВЕДЕ НОМЕРА НА КАБИНEТА, В КОЙТО ПРЕГЛЕЖДА ПАЦИЕНТИ, И ИМЕТО НА ОТДЕЛЕНИЕТО,
--В КОЕТО РАБОТИ:
CREATE OR REPLACE FUNCTION DOCTOR_OFFICE(IN P_STAFFID CHAR(10))
RETURNS TABLE (STAFF_ID CHAR(10), HOSP_OFFICE CHAR(5), DEPT VARCHAR(20))
RETURN
    SELECT S.ID, H.OFFICENUMBER, S.DEPARTMENTNAME
    FROM STAFF S, HOSPITALOFFICE H
    WHERE S.POSITION = 'DOCTOR'
    AND S.ID = H.IDSTAFF
    AND S.ID = P_STAFFID;

SELECT *
FROM TABLE(FN72039.DOCTOR_OFFICE('1SDIMITV06')) T;