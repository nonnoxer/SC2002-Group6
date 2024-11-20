package menus;

import java.util.ArrayList;
import medicine.Medicine;
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

    // Update the prescription status of a specific Appointment Outcome Record
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

    // View the inventory details
    private void viewInventory() {
        ArrayList<Medicine> medicines = pharmacist.getInventory();
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
        String name = sc.promptLine("Enter medicine name: ");
        int stock = sc.promptInt("Enter stock request amount: ", 1, Integer.MAX_VALUE);

        ReplenishmentRequest request = new ReplenishmentRequest(name, stock);
        pharmacist.requestReplenishment(request);
        System.out.printf("Replenishment request for '%s' (%d) has been submitted.\n", name, stock);
    }
}
