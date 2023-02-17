SET SCHEMA FN72039;

--ПО ВХОДЕН ПАРАМЕТЪР ЕГН-ТО НА ПАЦИЕНТ ДА СЕ ИЗВЕДЕ ОТДЕЛЕНИЕТО, В КОЕТО ЛЕЖИ И НОМЕРА НА БОЛНИЧНАТА СТАЯ
--(процедура с курсор и входни и изходни параметри)
CREATE OR REPLACE PROCEDURE EGN_PATROOM_DEPT_PROC(IN P_EGN CHAR(10), OUT P_DEPTNAME VARCHAR(20), OUT P_ROOM_NUMBER CHAR(5))
BEGIN
    DECLARE CURSOR1 CURSOR FOR SELECT DEPARTMENT_NAME, PATIENTS_ROOM_NUMBER
                           FROM PATIENTS_HOSPITAL_SERVICE
                           WHERE EGN = P_EGN;
    OPEN CURSOR1;
        FETCH CURSOR1 INTO P_DEPTNAME, P_ROOM_NUMBER;
    CLOSE CURSOR1;
END;

BEGIN
  DECLARE OUT_DEPTNAME VARCHAR(20);
  DECLARE OUT_ROOM_NUMBER CHAR(5);
  DECLARE IN_EGN CHAR(10);

  SET IN_EGN = '5806162378';

  CALL FN72039. EGN_PATROOM_DEPT_PROC(IN_EGN, OUT_DEPTNAME,OUT_ROOM_NUMBER);
  CALL DBMS_OUTPUT.PUT_LINE('PATIENT WITH EGN: ' || IN_EGN || ', STAY AT DEPTARTMENT: ' || OUT_DEPTNAME ||
                            ', IN A ROOM NUMBER: ' || OUT_ROOM_NUMBER);
END;


--ПРОЦЕДУРА, КОЯТО УВЕЛИЧАВА С 25% ЦЕНАТА НА УСЛУГАТА НА ТЕЗИ ПАЦИЕНТИ, КОИТО НЕ СА ЗДРАВНООСИГУРЕНИ:
--(процедура с курсор и while цикъл)
CREATE OR REPLACE PROCEDURE INCREM_HOSPIT_SERVICE_PAT_NOT_HEALTINSURED()
BEGIN
    DECLARE sqlcode INT;
    DECLARE v_egn CHAR(10);
    DECLARE v_price INT;
    DECLARE v_healthinsured INT;
    DECLARE CURSOR1 CURSOR FOR SELECT P.EGN, PHS.PRICEOFSERVICE, P.HEALTHINSURED
                               FROM PATIENTS P, PATIENTS_HOSPITAL_SERVICE PHS
                               WHERE P.EGN = PHS.EGN;

    OPEN CURSOR1;

    FETCH CURSOR1 INTO v_egn, v_price, v_healthinsured;

     WHILE sqlcode = 0 DO
        IF v_healthinsured = 0 THEN
            UPDATE PATIENTS_HOSPITAL_SERVICE
                SET PRICEOFSERVICE = v_price * 1.25
                WHERE EGN = v_egn;
        END IF;

        FETCH CURSOR1 INTO v_egn, v_price, v_healthinsured;

     END WHILE;

    CLOSE CURSOR1;
END;

CALL FN72039.INCREM_HOSPIT_SERVICE_PAT_NOT_HEALTINSURED();

SELECT * FROM PATIENTS_HOSPITAL_SERVICE;


--ПРОЦЕДУРА, КОЯТО ПРОВЕРЯВА ДАЛИ ЕДИН ЛЕКАРСКИ КАБИНЕТ Е ЗАЕТ ИЛИ НЕ. АКО РЕДА ЗА КОЛОНАТА BUSY Е NULL, ДА
--СЕ ХВЪРЛИ ИЗКЛЮЧЕНИЕ ЗА NULL СТОЙНОСТ И ДА СЕ ИЗВЕДЕ, ЧЕ НЯМА ИНФОРМАЦИЯ ДАЛИ ДАДЕН КАБИНЕТ Е ЗАЕТ ИЛИ НЕ.
--(процедура с прихващане на изключение)

CREATE OR REPLACE PROCEDURE HOSPITALOFFICE_BUSY()
BEGIN
    DECLARE at_end INTEGER DEFAULT 0;
    DECLARE not_nextline CONDITION FOR SQLSTATE '02000';
    DECLARE null_busy CONDITION FOR SQLSTATE '22002';
    DECLARE v_officenumber CHAR(5) DEFAULT '';
    DECLARE v_deptname VARCHAR(20) DEFAULT '';
    DECLARE v_busy INT DEFAULT 0;
    DECLARE v_idstaff CHAR(10) DEFAULT '';
    DECLARE cursor1 CURSOR FOR SELECT *
                               FROM HOSPITALOFFICE;
    DECLARE CONTINUE HANDLER FOR not_nextline
        SET at_end = 1;
    DECLARE CONTINUE HANDLER FOR null_busy
            CALL DBMS_OUTPUT.PUT_LINE('THERE IS NO INFO FOR OFFICE NUMBER: ' || v_officenumber || ' IN DEPARTMENT: ' || v_deptname ||
                                      ' IF IT IS BUSY OR NOT');
    OPEN cursor1;

    LOOP1: LOOP
            FETCH cursor1 INTO v_officenumber, v_deptname, v_busy, v_idstaff;

            IF at_end = 1 THEN LEAVE LOOP1;
            END IF;

            IF v_busy IS NULL
                THEN SIGNAL null_busy;
            END IF;

    END LOOP;

    CLOSE cursor1;
END;

CALL FN72039.HOSPITALOFFICE_BUSY();