package user;

import java.util.ArrayList;

import data.appointment.AppointmentDatabaseApiPharmacist;
import medicine.InventoryApiPharmacist;
import medicine.Medicine;
import medicine.PrescriptionStatus;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;

/**
 * Represents a Pharmacist, extending the Staff class.
 * The Pharmacist class is responsible for managing prescription records, dispensing prescriptions, 
 * handling inventory management, and submitting replenishment requests.
 * 
 * @author FONG JIAN YUAN
 * @version 1.0
 * @since 2024-11-21
 */
public class Pharmacist extends Staff {
    private AppointmentDatabaseApiPharmacist appointmentDb;
    private InventoryApiPharmacist inventory;

    /**
     * Constructs a new Pharmacist object with the provided user details.
     *
     * @param id The unique user ID of the pharmacist.
     * @param name The name of the pharmacist.
     * @param role The role of the user (should be Role.Pharmacist).
     * @param gender The gender of the pharmacist.
     * @param age The age of the pharmacist.
     */
    public Pharmacist(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);
        this.appointmentDb = null;
        this.inventory = null;
    }

    /**
     * Initializes the Pharmacist object with the appointment database and inventory.
     * This method establishes the connection with the relevant data sources.
     *
     * @param appointmentDb The AppointmentDatabaseApiPharmacist instance to interact with appointment records.
     * @param inventory The InventoryApiPharmacist instance to manage the inventory of medicines.
     */
    public void init(AppointmentDatabaseApiPharmacist appointmentDb, InventoryApiPharmacist inventory) {
        this.appointmentDb = appointmentDb;
        this.inventory = inventory;
    }

    /**
     * Retrieves all pending prescription records from the appointment database.
     * A pending prescription status indicates that the pharmacist needs to dispense the prescription.
     *
     * @return An ArrayList of AppointmentOutcomeRecord objects with a Pending prescription status.
     */
    public ArrayList<AppointmentOutcomeRecord> getPendingRecords() {
        ArrayList<AppointmentOutcomeRecord> result = new ArrayList<>();
        for (AppointmentOutcomeRecord record : this.appointmentDb.getRecords()) {
            if (record.getPrescriptionStatus().equals(PrescriptionStatus.Pending)) {
                result.add(record);
            }
        }
        return result;
    }

    /**
     * Dispenses a prescription for a given appointment outcome record.
     * If successful, updates the prescription status and interacts with the inventory to dispense the medication.
     *
     * @param record The AppointmentOutcomeRecord object containing the prescription to be dispensed.
     * @return True if the prescription was successfully dispensed, otherwise false.
     */
    public boolean dispensePrescription(AppointmentOutcomeRecord record) {
        boolean success = this.inventory.dispensePrescription(record.getPrescription());
        if (!success) return false;

        AppointmentOutcomeRecord result = this.appointmentDb.dispensePrescription(record.getAppointmentId());
        if (result == null) {
            System.out.println("Record not found.");
        }
        return result != null;
    }

    /**
     * Retrieves the inventory of medicines associated with the pharmacist.
     *
     * @return An ArrayList of Medicine objects currently in the inventory.
     */
    public ArrayList<Medicine> getInventory() {
        return this.inventory.getInventory();
    }

    /**
     * Submits a replenishment request for medicines in the inventory.
     * The request is forwarded to the inventory system for handling.
     *
     * @param request The ReplenishmentRequest object containing details of the requested replenishment.
     */
    public void requestReplenishment(ReplenishmentRequest request) {
        if (this.inventory != null) {
            this.inventory.handleReplenishmentRequest(request);
        } else {
            System.out.println("Inventory is not initialized.");
        }
    }

    /**
     * Views the current inventory of medicines.
     * Prints the name, stock level, and low stock alert level for each medicine in the inventory.
     */
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
