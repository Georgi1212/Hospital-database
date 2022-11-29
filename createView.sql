SET SCHEMA FN72039;

/* --- VIEWS -----
Изглед, които ни дава информация за ЕГН-то и името на пациентите
и дава информация за номера на болничната стая, в която лежат.
*/
CREATE VIEW V_PAT_ROOM
AS
SELECT P.NAME, P.EGN, PS.PATIENTS_ROOM_NUMBER
FROM PATIENTS P, PATIENTS_HOSPITAL_SERVICE PS, HOSPITALPATIENTSROOMS HR
WHERE PS.PATIENTS_ROOM_NUMBER = HR.PATIENTSROOMNUMBER
AND PS.DEPARTMENT_NAME = HR.DEPARTMENTNAME
AND PS.EGN = P.EGN;

SELECT * FROM V_PAT_ROOM;


/*
Изглед, който ни дава информация за номера на болничните стаи и
имената на отделенията, в които се намират.
 */
CREATE VIEW V_PAT_ROOM_DEPT
AS
SELECT HR.PATIENTSROOMNUMBER, D.NAME
FROM HOSPITALDEPARTMENTS D, HOSPITALPATIENTSROOMS HR
WHERE D.NAME = HR.DEPARTMENTNAME;

SELECT * FROM V_PAT_ROOM_DEPT;


/*
Изглед, който ни дава информация за имената на отделенията и
броя свободни легла в съответното отделение.
 */
CREATE VIEW V_FREE_ROOM_DEPT
AS
SELECT D.NAME, HR.NUMBERFREEBEDS
FROM HOSPITALDEPARTMENTS D, HOSPITALPATIENTSROOMS HR
WHERE D.NAME = HR.DEPARTMENTNAME;

SELECT * FROM V_FREE_ROOM_DEPT;