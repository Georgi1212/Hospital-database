package hospitalDB;

import java.util.Scanner;

public class HospitalDBController {
    private Scanner input;

    HospitalDBController() {
        input = new Scanner(System.in);
    }

    private void intro() {
        System.out.println(" --- ENTER COMMAND --- ");
        System.out.println(" 1) SELECT ");
        System.out.println(" 2) INSERT ");
        System.out.println(" 3) DELETE ");
        System.out.println(" 4) VIEW ");
        System.out.println(" 5) EXIT ");
    }

    public void run() {
        DB2Test hospController = new DB2Test();
        String stmnt = new String();

        intro();

        String choice = input.next();

        if (choice.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting from the application...");
            input.close();
        }
        else if (choice.equalsIgnoreCase("SELECT")) {
            System.out.println(" ---- SELECT ----");
            System.out.println(" WHAT DO YOU WANT TO SELECT?");

            String selectChoice = input.next();
            hospController.selectExecution(stmnt, selectChoice);

        }
        else if (choice.equalsIgnoreCase("INSERT")) {
            System.out.println(" ---- INSERT YOUR DATA ---- ");
            System.out.println(" WHAT DO YOU WANT TO INSERT?");

            String insertChoice = input.next();
            hospController.insertInto(stmnt, insertChoice);
        }
        else if (choice.equalsIgnoreCase("DELETE")) {
            System.out.println(" ---- DELETE STAFF ----");
            System.out.println(" WHICH STAFF DO YOU WANT TO DELETE? (ID)");

            String deleteChoice = input.next();
            hospController.deleteExecution(stmnt, deleteChoice);
        }
        else if (choice.equalsIgnoreCase("VIEW")) {
            System.out.println(" ---- VIEW ----");
            System.out.println(" WHAT DO YOU WANT TO VIEW?");

            System.out.println(" PATIENTS ROOM NUMBER");
            System.out.println(" ROOM NUMBER DEPT.");
            System.out.println(" DEPT. FREE BEDS");

            String viewChoice = input.next();
            hospController.viewExecution(stmnt, viewChoice);
        }
        else {
            throw new IllegalArgumentException("UNEXPECTED VALUE: " + choice);
        }

        System.out.println("Thank you for using TestMed Hospital Data Base!");
    }

    public static void main(String[] args) {
        HospitalDBController hspCtrl = new HospitalDBController();
        hspCtrl.run();
    }
}
