package hospitalDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DB2Test {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void openConnection() {
// Step 1: Load IBM DB2 JDBC driver
        try {
            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        } catch (Exception cnfex) {
            System.out.println("Problem in loading or registering IBM DB2 JDBC driver");
            cnfex.printStackTrace();
        }

// Step 2: Opening database connection
        try {
            connection = DriverManager.getConnection("jdbc:db2://62.44.108.24:50000/SAMPLE", "db2admin", "db2admin");
            statement = connection.createStatement();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (null != connection) {
                // cleanup resources, once after processing
                resultSet.close();
                statement.close();
                // and then finally close connection
                connection.close();
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void select(String stmnt, int column) {
        try {
            resultSet = statement.executeQuery(stmnt);
            String result = "";

            while (resultSet.next()) {

                for (int i = 1; i <= column; i++) {
                    result += resultSet.getString(i);
                    if (i == column) result += " \n";
                    else result += ", ";
                }
            }
            System.out.println("Executing query: " + stmnt + "\n");
            System.out.println("Result output \n");
            System.out.println("---------------------------------- \n");
            System.out.println(result);
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public void insert(String stmnt) {
        try {
            statement.executeUpdate(stmnt);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        System.out.println("Successfully inserted!");
    }

    public void view(String stmnt, int column) {
        try {
            resultSet = statement.executeQuery(stmnt);
            String result = "";

            while (resultSet.next()) {

                for (int i = 1; i <= column; i++) {
                    result += resultSet.getString(i);
                    if (i == column) result += " \n";
                    else result += "  ";
                }
            }

            System.out.println(result);

        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public void delete(String stmnt) {
        try {
            statement.executeUpdate(stmnt);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        System.out.println("Successfully deleted!");
    }


    public void viewExecution(String stmtnt, String choice) {
        openConnection();

        char viewChosen = Character.toUpperCase(choice.charAt(0));
        if (categoryIsValid(viewChosen)) {
            viewByCategory(viewChosen, choice);
        } else {
            System.out.println("Wrong category entered!");
            return;
        }
    }

    private boolean categoryIsValid(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    private void viewByCategory(char category, String choice) {
        switch (category) {
            case 'P': {//PATIENTS ROOM NUMBER
                System.out.println("NAME - EGN - PATIENTS ROOM NUMBER");
                view("SELECT * FROM FN72039.V_PAT_ROOM", 3);
                break;
            }
            case 'R': {//ROOM NUMBER DEPT.
                System.out.println("ROOM № DEPT NAME");
                view("SELECT * FROM FN72039.V_PAT_ROOM_DEPT", 2);
                break;
            }
            case 'D':{//DEPT. FREE BEDS
                System.out.println("DEPARTMENT NAME - NUMBER FREE BEDS");
                view("SELECT * FROM FN72039.V_FREE_ROOM_DEPT", 2);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + category);
        }
    }

    public void selectExecution(String stmnt, String choice) {
        openConnection();
        //choice.toUpperCase();

        // chosen fixed selections
        if(choice.equalsIgnoreCase("STAFF")) {
            System.out.println("\nSHOWING ALL STAFF...");
            stmnt = "SELECT * FROM FN72039.STAFF";
            select(stmnt, 10);
        }
        else if (choice.equalsIgnoreCase("PATIENTS")) {
            System.out.println("\nSHOWING ALL PATIENTS");
            stmnt = "SELECT * FROM FN72039.PATIENTS";
            select(stmnt, 7);
        }
        else if (choice.equalsIgnoreCase("PATIENTS_HOSPITAL_SERVICE")) {
            System.out.println("\nSHOWING ALL PATIENTS HOSPITAL SERVICES");
            stmnt = "SELECT * FROM FN72039.PATIENTS_HOSPITAL_SERVICE";
            select(stmnt, 10);
        }
        else if (choice.equalsIgnoreCase("HISTORY_OF_PROCEDURES")) {
            System.out.println("\nSHOWING PATIENT'S HISTORY PROCEDURES");
            stmnt = "SELECT * FROM FN72039.HISTORYOFPROCEDURES";
            select(stmnt, 5);
        }
        else if (choice.equalsIgnoreCase("HOSPITAL_DEPARTMENTS")) {
            System.out.println("\nSHOWING HOSPITAL DEPARTMENTS");
            stmnt = "SELECT * FROM FN72039.HOSPITALDEPARTMENTS";
            select(stmnt, 2);
        }
        else if (choice.equalsIgnoreCase("HOSPITAL_PATIENTS_ROOMS")) {
            System.out.println("\nSHOWING HOSPITAL PATIENTS ROOMS");
            stmnt = "SELECT * FROM FN72039.HOSPITALPATIENTSROOMS";
            select(stmnt, 4);
        }
        else if (choice.equalsIgnoreCase("HOSPITAL_OFFICE")) {
            System.out.println("\nSHOWING HOSPITAL OFFICES");
            stmnt = "SELECT * FROM FN72039.HOSPITALOFFICE";
            select(stmnt, 4);
        }
        else if (choice.equalsIgnoreCase("CLEANS")) {
            System.out.println("\nSHOWING TABLE CLEANS");
            stmnt = "SELECT * FROM FN72039.CLEANS";
            select(stmnt, 2);
        }
        else if (choice.equalsIgnoreCase("GO THROUGH")) {
            System.out.println("\nSHOWING TABLE GO THROUGH");
            stmnt = "SELECT * FROM FN72039.GOTHROUGH";
            select(stmnt, 3);
        }
        else if (choice.equalsIgnoreCase("HELPS")) {
            System.out.println("\nSHOWING TABLE HELPS");
            stmnt = "SELECT * FROM FN72039.HELPS";
            select(stmnt, 2);
        }
        else if (choice.equalsIgnoreCase("TAKING_CARE_OF")) {
            System.out.println("\nSHOWING TABLE TAKING CARE OF");
            stmnt = "SELECT * FROM FN72039.TAKINGCAREOF";
            select(stmnt, 2);
        }
        else if(choice.equalsIgnoreCase("LEAVE")) {
            return;
        }
        else {
            System.out.println("WRONG TABLE NAME!");
            return;
        }

    }

    public void insertInto(String stmnt, String choice) {
        openConnection();

        choice.toUpperCase();
        if (choice.equalsIgnoreCase("STAFF")) {
            staff_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("PATIENTS")) {
            patients_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("PATIENTS_HOSPITAL_SERVICE")) {
            patients_hospital_service_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("HISTORYOFPROCEDURES")) {
            history_of_procedures_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("HOSPITALDEPARTMENTS")) {
            hospital_departments_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("HOSPITALPATIENTSROOMS")) {
            hospital_patients_rooms_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("HOSPITALOFFICE")) {
            hopital_office_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("CLEANS")) {
            cleans_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("GOTHROUGH")) {
            go_through_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("HELPS")) {
            helps_choice(stmnt);
        }
        else if (choice.equalsIgnoreCase("TAKINGCAREOF")) {
            taking_care_of_choice(stmnt);
        }
        else if(choice.equalsIgnoreCase("LEAVE")) {
            return;
        }
        else {
            System.out.println("WRONG TABLE NAME!");
            return;
        }
    }

    private void staff_choice(String stmnt) {
        Scanner inputScanner = new Scanner(System.in);

        String id;
        String name;
        String position;
        String telephoneNumber;
        String eMail;
        String salary;
        String directorHospital;
        String chiefNurse;
        String headOfDepartment;
        String departmentName;

        System.out.println("Enter STAFF ID: ");
        id = inputScanner.nextLine();

        System.out.println("Enter STAFF name: ");
        name = inputScanner.nextLine();

        System.out.println("Enter STAFF position: ");
        position = inputScanner.nextLine();

        System.out.println("Enter STAFF telephoneNumber: ");
        telephoneNumber = inputScanner.nextLine();

        System.out.println("Enter STAFF e-mail: ");
        eMail = inputScanner.nextLine();

        System.out.println("Enter STAFF salary: ");
        salary = inputScanner.nextLine();

        System.out.println("Enter STAFF directorHospital: ");
        directorHospital = inputScanner.nextLine();

        System.out.println("Enter STAFF chief nurse: ");
        chiefNurse = inputScanner.nextLine();

        System.out.println("Enter STAFF head of department: ");
        headOfDepartment = inputScanner.nextLine();

        System.out.println("Enter STAFF department: ");
        departmentName = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.STAFF(ID, NAME, POSITION, TELEPHONENUMBER, EMAIL, SALARY, DIRECTORHOSPITAL," +
                " CHIEFNURSE, HEADOFDEPARTMENT, DEPARTMENTNAME)"
                + " VALUES ('" + id + "','" + name + "','" + position + "','" + telephoneNumber
                + "','" + eMail + "','" + salary + "','" + directorHospital + "','" + chiefNurse
                + "','" + headOfDepartment + "','" + departmentName + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void patients_choice(String stmnt) {
        Scanner inputScanner = new Scanner(System.in);

        String egn;
        String name;
        String gender;
        String telephoneNumber;
        String address;
        String healthInsured;
        String telk;

        System.out.println("Enter PATIENTS egn: ");
        egn = inputScanner.nextLine();

        System.out.println("Enter PATIENTS name: ");
        name = inputScanner.nextLine();

        System.out.println("Enter PATIENTS gender: ");
        gender = inputScanner.nextLine();

        System.out.println("Enter PATIENTS address: ");
        address = inputScanner.nextLine();

        System.out.println("Enter PATIENTS telephoneNumber: ");
        telephoneNumber = inputScanner.nextLine();

        System.out.println("Enter PATIENTS healthInsured: ");
        healthInsured = inputScanner.nextLine();

        System.out.println("Enter PATIENTS telk: ");
        telk = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.PATIENTS(EGN, NAME, GENDER, ADDRESS, TELEPHONENUMBER, HEALTHINSURED, TELK)"
                + " VALUES ('" + egn + "','" + name + "','" + gender + "','" + address
                + "','" + telephoneNumber + "','" + healthInsured + "','" + telk + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void patients_hospital_service_choice(String stmnt) {
        Scanner inputScanner = new Scanner(System.in);

        String egn;
        String dateArrival;
        String timeArrival;
        String purposeVisit;
        String officeNumber;
        String priceOfService;
        String doctorId;
        String registryStaffId;
        String patientsRoomNumber;
        String departmentName;

        System.out.println("Enter PATIENTS egn: ");
        egn = inputScanner.nextLine();

        System.out.println("Enter PATIENTS date arrival: ");
        dateArrival = inputScanner.nextLine();

        System.out.println("Enter PATIENTS time arrival: ");
        timeArrival = inputScanner.nextLine();

        System.out.println("Enter PATIENTS purpose visit: ");
        purposeVisit = inputScanner.nextLine();

        System.out.println("Enter PATIENTS office number: ");
        officeNumber = inputScanner.nextLine();

        System.out.println("Enter PATIENTS price of service: ");
        priceOfService = inputScanner.nextLine();

        System.out.println("Enter PATIENTS doctor id: ");
        doctorId = inputScanner.nextLine();

        System.out.println("Enter PATIENTS registry staff id: ");
        registryStaffId = inputScanner.nextLine();

        System.out.println("Enter PATIENTS room number: ");
        patientsRoomNumber = inputScanner.nextLine();

        System.out.println("Enter PATIENTS department: ");
        departmentName = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.PATIENTS_HOSPITAL_SERVICE(EGN, DATEARRIVAL, TIMEARRIVAL, PURPOSEVISIT, OFFICENUMBER," +
                " PRICEOFSERVICE, DOCTORID, REGISTRYSTAFFID, PATIENTS_ROOM_NUMBER, DEPARTMENT_NAME)"
                + " VALUES ('" + egn + "','" + dateArrival + "','" + timeArrival + "','" + purposeVisit
                + "','" + officeNumber + "','" + priceOfService + "','" + doctorId + "','" + registryStaffId + "','" + patientsRoomNumber + "','" + departmentName + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void history_of_procedures_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String egn;
        String dateProcedure;
        String typeOfProcedure;
        String resultOfProcedures;
        String diagnosisName;

        System.out.println("Enter PATIENTS egn: ");
        egn = inputScanner.nextLine();

        System.out.println("Enter PATIENTS date of procedures: ");
        dateProcedure = inputScanner.nextLine();

        System.out.println("Enter PATIENTS type of procedure: ");
        typeOfProcedure = inputScanner.nextLine();

        System.out.println("Enter PATIENTS result of procedures: ");
        resultOfProcedures = inputScanner.nextLine();

        System.out.println("Enter PATIENTS diagnosis name: ");
        diagnosisName = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.HISTORYOFPROCEDURES(EGN, DATEPROCEDURE, TYPEOFPROCEDURE, RESULTOFPROCESURES, DIAGNOSISNAME)"
                + " VALUES ('" + egn + "','" + dateProcedure + "','" + typeOfProcedure + "','" + resultOfProcedures
                + "','" + diagnosisName + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void hospital_departments_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String nameDept;
        String headDoctorId;

        System.out.println("Enter DEPARTMENT name: ");
        nameDept = inputScanner.nextLine();

        System.out.println("Enter DEPARTMENT head doctor id: ");
        headDoctorId = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.HOSPITALDEPARTMENTS(NAME, HEADDOCTORID)"
                + " VALUES ('" + nameDept + "','" + headDoctorId + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void hospital_patients_rooms_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String patientRoomNumber;
        String departmentName;
        String numberBeds;
        String numberFreeBeds;

        System.out.println("Enter HOSPITAL PATIENT ROOMS number: ");
        patientRoomNumber = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL PATIENT ROOMS dept. name: ");
        departmentName = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL PATIENT ROOMS number of beds: ");
        numberBeds = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL PATIENT ROOMS number of free beds: ");
        numberFreeBeds = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.HOSPITALPATIENTSROOMS(PATIENTSROOMNUMBER, DEPARTMENTNAME, NUMBERBEDS, NUMBERFREEBEDS)"
                + " VALUES ('" + patientRoomNumber + "','" + departmentName + "','" + numberBeds + "','" + numberFreeBeds + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void hopital_office_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String officeNumber;
        String departmentName;
        String busy;
        String idStaff;

        System.out.println("Enter HOSPITAL OFFICE number: ");
        officeNumber = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL OFFICE dept. name: ");
        departmentName = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL OFFICE busy: ");
        busy = inputScanner.nextLine();

        System.out.println("Enter HOSPITAL OFFICE id staff: ");
        idStaff = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.HOSPITALOFFICE(OFFICENUMBER, DEPARTMENTNAME, BUSY, IDSTAFF)"
                + " VALUES ('" + officeNumber + "','" + departmentName + "','" + busy + "','" + idStaff + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void cleans_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String sanitarianId;
        String departmentName;

        System.out.println("Enter Sanitarian id: ");
        sanitarianId = inputScanner.nextLine();

        System.out.println("Enter department name: ");
        departmentName = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.CLEANS(SANITARIANID, DEPARTMENTNAME)"
                + " VALUES ('" + sanitarianId + "','" + departmentName + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void go_through_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String nurseId;
        String departmentName;
        String patientsRoomNumber;

        System.out.println("Enter Nurse id: ");
        nurseId = inputScanner.nextLine();

        System.out.println("Enter Department name: ");
        departmentName = inputScanner.nextLine();

        System.out.println("Enter Patients room number: ");
        patientsRoomNumber = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.GOTHROUGH(NURSEID, DEPARTMENT_NAME, PATIENTS_ROOM_NUMBER)"
                + " VALUES ('" + nurseId + "','" + departmentName + "','" + patientsRoomNumber + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void helps_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String sanitarianId;
        String patietsEgn;

        System.out.println("Enter Sanitarian id: ");
        sanitarianId = inputScanner.nextLine();

        System.out.println("Enter Patients egn: ");
        patietsEgn = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.HELPS(SANITARIANID, PATIENTEGN)"
                + " VALUES ('" + sanitarianId + "','" + patietsEgn + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    private void taking_care_of_choice(String stmnt){
        Scanner inputScanner = new Scanner(System.in);

        String nurseId;
        String patietsEgn;

        System.out.println("Enter Nurse id: ");
        nurseId = inputScanner.nextLine();

        System.out.println("Enter Patients egn: ");
        patietsEgn = inputScanner.nextLine();

        stmnt = " INSERT INTO FN72039.TAKINGCAREOF(NURSEID, PATIENTEGN)"
                + " VALUES ('" + nurseId + "','" + patietsEgn + "')";

        this.insert(stmnt);
        inputScanner.close();
    }

    public void deleteExecution(String stmnt, String id) {
        openConnection();
        char quote = '\'';

        stmnt = "DELETE FROM FN72039.STAFF WHERE ID =" + quote + id + quote;
        delete(stmnt);
    }
}

