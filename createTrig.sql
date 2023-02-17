SET SCHEMA FN72039;

--ТРИГЕР, КОЙТО Е "ПРЕДИ" INSERT И "ГЛЕДА" АКО НОМЕРА НА СТАЯТА, В КОЙТО ЩЕ СЕ НАСТАНИ ПАЦИЕНТ Е NULL
--ДА ГО СЛОЖАТ В БОЛНИЧНА СТАЯ, КОЯТО Е В ОТДЕЛЕНИЕ, В КОЕТО ЩЕ БЪДЕ НАСТАНЕН И ПАЦИЕНТА
CREATE OR REPLACE TRIGGER TRIG_BEFORE_INSERT_PATIENTS
    BEFORE INSERT ON PATIENTS_HOSPITAL_SERVICE
    REFERENCING NEW AS N
    FOR EACH ROW
    WHEN (N.PATIENTS_ROOM_NUMBER IS NULL)
        SET N.PATIENTS_ROOM_NUMBER = '51012';

INSERT INTO PATIENTS_HOSPITAL_SERVICE(EGN, DATEARRIVAL, TIMEARRIVAL, PURPOSEVISIT,
OFFICENUMBER, PRICEOFSERVICE, DOCTORID, REGISTRYSTAFFID, PATIENTS_ROOM_NUMBER, DEPARTMENT_NAME)
VALUES('9702061589', '2022-05-23','12:00:00','PREGLED','51004','50','1VPETROV10','4DIVANOV41',NULL,'HEMATOLOGIQ');


--ТРИГЕР, КОЙТО "СЛЕД" INSERT И АКО ВЪВЕДЕМ В ТАБЛИЦАТА STAFF ДОКТОР,ШЕФ НА ОТДЕЛЕНИE, ДА МУ СЕ УВЕЛИЧИ ЗАПЛАТАТА С 5% И
--СЪОТВЕТНО ДА СЕ УВЕЛИЧИ ЗАПЛАТА НА ОСТАНАЛИТЕ ДОКОРИ, КОИТО СА ШЕФОВЕ НА ОТДЕЛЕНИЯ С 5%.
CREATE OR REPLACE PROCEDURE STAFF_INCREM_SAL_PROC()
BEGIN
    DECLARE at_end INTEGER DEFAULT 0;
    DECLARE v_salary INT;
    DECLARE v_staffid CHAR(10);
    DECLARE v_headofdepartment INT;
    DECLARE v_position VARCHAR(15);
    DECLARE not_nextline CONDITION FOR SQLSTATE '02000';
    DECLARE cursor1 CURSOR FOR SELECT ID, POSITION, SALARY, HEADOFDEPARTMENT
	                           FROM STAFF;
    DECLARE CONTINUE HANDLER FOR not_nextline
            SET at_end = 1;

    OPEN cursor1;

    LOOP1: LOOP
        FETCH cursor1 INTO v_staffid, v_position, v_salary, v_headofdepartment;

        IF at_end = 1 THEN LEAVE LOOP1;
        END IF;

        IF v_position = 'DOCTOR' AND v_headofdepartment = 1 THEN
            UPDATE STAFF
            SET SALARY = v_salary * 1.05
            WHERE ID = v_staffid;
        END IF;

    END LOOP;

    CLOSE cursor1;
END;

CREATE OR REPLACE TRIGGER TRIG_AFTER_INSERT_PATIENTS
AFTER INSERT ON FN72039.STAFF REFERENCING NEW AS N
FOR EACH ROW
    CALL FN72039.STAFF_INCREM_SAL_PROC();

INSERT INTO STAFF(ID, NAME, POSITION, TELEPHONENUMBER, EMAIL, SALARY,
                    DIRECTORHOSPITAL, CHIEFNURSE, HEADOFDEPARTMENT, DEPARTMENTNAME)
VALUES ('6NNIKOLOV1', 'NIKOLAI NIKOLOV', 'DOCTOR', '0877963514', 'nnikolv01@testmed.bg','5000', NULL, NULL, '1', 'ORTOPEDIQ');