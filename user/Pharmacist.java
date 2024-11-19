package user;

import java.util.ArrayList;
import medicine.Inventory;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;

public class Pharmacist extends Staff {
    private ArrayList<AppointmentOutcomeRecord> records;
    private Inventory inventory;

    // Constructor
    public Pharmacist(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);
        this.records = new ArrayList<>();
        this.inventory = null;
    }

    // Initialize the inventory object for the Pharmacist
    public void init(Inventory inventory) {
        this.inventory = inventory;
    }

    // Getter for appointment outcome records
    public ArrayList<AppointmentOutcomeRecord> getRecords() {
        return this.records;
    }

    // Add a new AppointmentOutcomeRecord to the Pharmacist's records
    public void addRecord(AppointmentOutcomeRecord record) {
        this.records.add(record);
        System.out.println("Record added successfully.");
    }

    // Update the prescription status in an appointment outcome record
    public void updatePrescriptionStatus(AppointmentOutcomeRecord record, String status) {
        if (this.records.contains(record)) {
            record.updatePrescriptionStatus(status);
            System.out.println("Prescription status updated to: " + status);
        } else {
            System.out.println("Record not found.");
        }
    }

    // Get the inventory associated with the Pharmacist
    public Inventory getInventory() {
        return this.inventory;
    }

    // Submit a replenishment request for medicine
    public void requestReplenishment(ReplenishmentRequest request) {
        if (this.inventory != null) {
            // Assuming the inventory has a method to handle replenishment requests
            System.out.println("Submitting replenishment request for medicine: " + request.getName());
            request.approveRequest(true); // Mark request as approved
        } else {
            System.out.println("Inventory is not initialized.");
        }
    }

    // View the inventory
    public void viewInventory() {
        if (this.inventory != null) {
            ArrayList<medicine.Medicine> inventoryList = inventory.getInventory();
            for (medicine.Medicine medicine : inventoryList) {
                System.out.println("Medicine: " + medicine.getName() +
                                   ", Stock: " + medicine.getStock() +
                                   ", Low Stock Alert Level: " + medicine.getLowStockLevelAlert());
            }
        } else {
            System.out.println("Inventory is not initialized.");
        }
    }
}
