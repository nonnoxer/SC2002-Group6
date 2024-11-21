package menus;

import java.util.ArrayList;
import medicine.Medicine;
import medicine.Prescription;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;
import user.Pharmacist;

/**
 * The PharmacistMenu represents the menu interface for a pharmacist, allowing them to view appointment outcomes, 
 * update prescription statuses, view medication inventory, and submit replenishment requests.
 * This menu is displayed repeatedly until the pharmacist decides to log out.
 * 
 * @author FONG JIAN YUAN
 * @version 1.0
 * @since 2024-11-21
 */
public class PharmacistMenu extends Menu {
    private SafeScanner sc;
    private Pharmacist pharmacist;

    /**
     * Construct a new PharmacistMenu object with a specified SafeScanner and Pharmacist.
     * 
     * @param sc the SafeScanner object to capture user input
     * @param pharmacist the Pharmacist object representing the current pharmacist
     */
    public PharmacistMenu(SafeScanner sc, Pharmacist pharmacist) {
        this.sc = sc;
        this.pharmacist = pharmacist;
    }

    /**
     * Displays the pharmacist's menu options and allows them to make choices.
     * The menu will repeatedly show until the pharmacist chooses to log out.
     */
    @Override
    public void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========== Pharmacist Menu ==========");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("0. Logout");
            System.out.println("=====================================");

            int choice = sc.promptInt("Please choose an option: ", 0, 4);

            switch (choice) {
                case 1:
                    viewAppointmentOutcome();
                    break;

                case 2:
                    updatePrescriptionStatus();
                    break;

                case 3:
                    viewInventory();
                    break;

                case 4:
                    submitRequest();
                    break;

                case 0:
                    exit = true;
                    break;

                default:
                    System.out.println("The option is chosen incorrectly, please try again!");
            }
        }
    }

    /**
     * Displays and allows the pharmacist to view appointment outcome records.
     * If there are no available records, a message is displayed indicating the absence.
     */
    private void viewAppointmentOutcome() {
        ArrayList<AppointmentOutcomeRecord> records = pharmacist.getPendingRecords();
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records available.");
            return;
        }
        System.out.println("Appointment Outcome Records:");
        for (AppointmentOutcomeRecord record : records) {
            record.printAppointmentOutcomeRecord();
            System.out.println();
        }
    }

    /**
     * Allows the pharmacist to update the prescription status for a selected appointment outcome record.
     * If no records are available, a message is displayed indicating the absence of available outcome records.
     * If the pharmacist chooses a record, they can dispense the prescription, 
     * and the prescription status will be updated accordingly.
     * else the system will print out insufficient stock for the prescription.
     */
    private void updatePrescriptionStatus() {
        ArrayList<AppointmentOutcomeRecord> records = pharmacist.getPendingRecords();
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records available.");
            return;
        }

        System.out.println("Select a record to update:");
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("%d. Appointment Slot: %s, Current Status: %s\n", i + 1, 
                    records.get(i).getSlot().getDate(), records.get(i).getPrescriptionStatus());
        }
        System.out.println("0. Cancel");

        int choice = sc.promptInt("Enter record number: ", 0, records.size());
        if (choice == 0) return;
        AppointmentOutcomeRecord selectedRecord = records.get(choice-1);

        for (Prescription prescription : selectedRecord.getPrescription()) {
            System.out.printf("- %s (%d)\n", prescription.getName(), prescription.getQuantity());
        }

        System.out.println("1. Dispense prescription");
        System.out.println("0. Cancel");
        int dispenseChoice = sc.promptInt("Enter your choice: ", 0, 1);
        if (dispenseChoice == 0) return;

        boolean result = pharmacist.dispensePrescription(selectedRecord);
        if (result) {
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    /**
     * Displays the current medication inventory and stock levels in an array list.
     * If any medication stock is low, it will display a warning for those items.
     * If the inventory is empty, a corresponding message will be shown.
     */
    private void viewInventory() {
        ArrayList<Medicine> medicines = pharmacist.getInventory();
        if (medicines.isEmpty()) {
            System.out.println("The inventory is currently empty.");
            return;
        }

        System.out.println("Medication Inventory:");
        for (Medicine medicine : medicines) {
            System.out.printf("Medicine: %s, Stock: %d",
                medicine.getName(), medicine.getStock());
            if (medicine.checkLowStock()) {
                System.out.printf(" [ALERT: Stock low (<%d)]",
                medicine.getLowStockLevelAlert());
            }
            System.out.print("\n");
        }
    }

    /**
     * Prompts the pharmacist to submit a replenishment request for a specific medicine.
     * The pharmacist is asked to provide the name of the medicine and the desired quantity.
     * The request is then submitted to the pharmacist's system.
     */
    private void submitRequest() {
        String name = sc.promptLine("Enter medicine name: ");
        int stock = sc.promptInt("Enter stock request amount: ", 1, Integer.MAX_VALUE);

        ReplenishmentRequest request = new ReplenishmentRequest(name, stock);
        pharmacist.requestReplenishment(request);
        System.out.printf("Replenishment request for '%s' (%d) has been submitted.\n", name, stock);
    }
}