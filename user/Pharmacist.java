package user;

import java.util.ArrayList;

import data.appointment.AppointmentDatabaseApiPharmacist;
import medicine.Inventory;
import medicine.Medicine;
import medicine.PrescriptionStatus;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;

public class Pharmacist extends Staff {
    private AppointmentDatabaseApiPharmacist appointmentDb;
    private Inventory inventory;

    // Constructor
    public Pharmacist(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);
        this.appointmentDb = null;
        this.inventory = null;
    }

    // Initialize the inventory object for the Pharmacist
    public void init(AppointmentDatabaseApiPharmacist appointmentDb, Inventory inventory) {
        this.appointmentDb = appointmentDb;
        this.inventory = inventory;
    }

    // Getter for appointment outcome records
    public ArrayList<AppointmentOutcomeRecord> getPendingRecords() {
        ArrayList<AppointmentOutcomeRecord> result = new ArrayList<>();
        for (AppointmentOutcomeRecord record : this.appointmentDb.getRecords()) {
            if (record.getPrescriptionStatus().equals(PrescriptionStatus.Pending)) {
                result.add(record);
            }
        }
        return result;
    }

    // Update the prescription status in an appointment outcome record
    public boolean dispensePrescription(AppointmentOutcomeRecord record) {
        boolean success = this.inventory.dispensePrescription(record.getPrescription());
        if (!success) return false;

        AppointmentOutcomeRecord result = this.appointmentDb.dispensePrescription(record.getAppointmentId());
        if (result == null) {
            System.out.println("Record not found.");
        }
        return result != null;
    }

    // Get the inventory associated with the Pharmacist
    public ArrayList<Medicine> getInventory() {
        return this.inventory.getInventory();
    }

    // Submit a replenishment request for medicine
    public void requestReplenishment(ReplenishmentRequest request) {
        if (this.inventory != null) {
            this.inventory.handleReplenishmentRequest(request);
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
