package menus;

import java.util.Scanner;

import user.Administrator;

public class AdministratorMenu extends Menu{
    private Scanner sc;
    private Administrator administrator;

    public AdministratorMenu(Scanner sc, Administrator administrator) {
        this.sc = sc;
        this.administrator = administrator;
    }

    public void showMenu(){

        int choice = -1;
        
        while (choice != 8) {
            System.out.println("===== Doctor Menu =====");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();
            handleSelection(choice);
        }
    }

    private void handleSelection(int choice){
        switch (choice) {
            case 1:
                viewStaff();
                break;
            case 2:
                viewAppointments();
                break;
            case 3:
                viewInventory();
                break;
            case 4:
                approveRequests();
                break;
            case 5:
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }

    private void viewStaff() {

    }

    private void viewAppointments() {

    }

    private void viewInventory() {

    }

    private void approveRequests() {

    }
}



