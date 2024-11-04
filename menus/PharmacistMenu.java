package menus;

import java.util.Scanner;

import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.User;

public class PharmacistMenu extends Menu {
    private Scanner sc;
    private Pharmacist pharmacist;

    public PharmacistMenu(Scanner sc, Pharmacist pharmacist) {
        this.sc = sc;
        this.pharmacist = pharmacist;
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("========== Pharmacist Menu ==========");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.println("=====================================");
            System.out.println("Please choose an option:");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    // Call the method to view the prescription
                    pharmacist.viewAppOutcome();
                    break;

                case "2":
                    // Call the method to update the prescription status
                    //p.updatePrescriptionStatus();
                    break;

                case "3":
                    // Call the method to view the medical inventory
                    pharmacist.monitorInventory();
                    break;

                case "4":
                    // Call the method to submit a replenishment request;
                    pharmacist.requestReplenishment();
                    break;

                case "5":
                    // Call to logout
                    System.out.println("Logging out...");
                    exit = true;
                    break;

                default:
                    System.out.println("The option is chosen incorrectly, please try again!");
            }
        }
        //scanner.close();
    }
}
