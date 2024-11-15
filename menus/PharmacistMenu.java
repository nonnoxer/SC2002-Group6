package menus;

import java.util.ArrayList;
import java.util.Scanner;

import medicine.Inventory;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;
import user.Pharmacist;

public class PharmacistMenu extends Menu {
    private SafeScanner sc;
    private Pharmacist pharmacist;

    public PharmacistMenu(SafeScanner sc, Pharmacist pharmacist) {
        this.sc = sc;
        this.pharmacist = pharmacist;
    }

    @Override
    public void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("========== Pharmacist Menu ==========");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.println("=====================================");

            int choice = sc.promptInt("PLease choose an option: ", 1, 5);

            switch (choice) {
                case 1:
                    // Call the method to view the prescription
                    viewAppointmentOutcome();
                    break;

                case 2:
                    // Call the method to update the prescription status
                    updatePrescriptionStatus();
                    break;

                case 3:
                    viewInventory();
                    break;

                case 4:
                    submitRequest();
                    break;

                case 5:
                    // Call to logout
                    System.out.println("Logging out...");
                    exit = true;
                    break;

                default:
                    System.out.println("The option is chosen incorrectly, please try again!");
            }
        }
    }

    private void viewAppointmentOutcome() {
        ArrayList<AppointmentOutcomeRecord> records = pharmacist.getRecords();
        System.out.println("Viewing appointment outcome record...");
        for (AppointmentOutcomeRecord record : records) {
            System.out.println(record.getPrescription());
        }
    }

    private void updatePrescriptionStatus() {
        System.out.println("Updating prescription status...");
        // AppointmentOutcomeRecord record = new AppointmentOutcomeRecord();
        // pharmacist.updatePrescriptionStatus(record);
    }

    private void viewInventory() {
        Inventory inventory = pharmacist.getInventory();
        System.out.println("Monitoring inventory...");
    }

    private void submitRequest() {
        String name = sc.promptLine("Enter medicine name: ");
        int stock = sc.promptInt("Enter stock request: ", 0, 2147483647);
        ReplenishmentRequest request = new ReplenishmentRequest(name, stock);
        pharmacist.requestReplenishment(request);
    }
}
