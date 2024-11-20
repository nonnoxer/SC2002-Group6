package menus;

import java.util.ArrayList;
import medicine.Inventory;
import medicine.PrescriptionStatus;
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

            int choice = sc.promptInt("Please choose an option: ", 1, 5);

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

                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;

                default:
                    System.out.println("The option is chosen incorrectly, please try again!");
            }
        }
    }

    // View all Appointment Outcome Records
    private void viewAppointmentOutcome() {
        ArrayList<AppointmentOutcomeRecord> records = pharmacist.getRecords();
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

    // Update the prescription status of a specific Appointment Outcome Record
    private void updatePrescriptionStatus() {
        ArrayList<AppointmentOutcomeRecord> records = pharmacist.getRecords();
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records available.");
            return;
        }

        System.out.println("Select a record to update:");
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("%d. Appointment Slot: %s, Current Status: %s\n", i + 1, 
                    records.get(i).getSlot().getDate(), records.get(i).getPrescriptionStatus());
        }

        int choice = sc.promptInt("Enter record number: ", 1, records.size()) - 1;
        AppointmentOutcomeRecord selectedRecord = records.get(choice);

        System.out.println("1. Dispense prescription");
        System.out.println("0. Cancel");
        int dispenseChoice = sc.promptInt("Enter your choice: ", 0, 1);
        if (dispenseChoice == 0) return;

        pharmacist.updatePrescriptionStatus(selectedRecord, PrescriptionStatus.Dispensed);
        System.out.println("Prescription status updated successfully.");
    }

    // View the inventory details
    private void viewInventory() {
        Inventory inventory = pharmacist.getInventory();
        if (inventory == null) {
            System.out.println("Inventory not initialized.");
            return;
        }

        ArrayList<medicine.Medicine> medicines = inventory.getInventory();
        if (medicines.isEmpty()) {
            System.out.println("The inventory is currently empty.");
            return;
        }

        System.out.println("Medication Inventory:");
        for (medicine.Medicine medicine : medicines) {
            System.out.printf("Medicine: %s, Stock: %d, Low Stock Alert: %d\n",
                    medicine.getName(), medicine.getStock(), medicine.getLowStockLevelAlert());
        }
    }

    // Submit a replenishment request for medicine
    private void submitRequest() {
        Inventory inventory = pharmacist.getInventory();
        if (inventory == null) {
            System.out.println("Inventory not initialized.");
            return;
        }

        String name = sc.promptLine("Enter medicine name: ");
        int stock = sc.promptInt("Enter stock request amount: ", 1, Integer.MAX_VALUE);

        ReplenishmentRequest request = new ReplenishmentRequest(name, stock);
        pharmacist.requestReplenishment(request);

        inventory.handleReplenishmentRequest(request);
        System.out.printf("Replenishment request for '%s' has been submitted.\n", name);
    }
}
