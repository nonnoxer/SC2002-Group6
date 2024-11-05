package user;


import java.util.ArrayList;

import medicine.Inventory;
import medicine.ReplenishmentRequest;
import record.AppointmentOutcomeRecord;

public class Pharmacist extends Staff {
    private ArrayList<AppointmentOutcomeRecord> records;
    private Inventory inventory;
    
    // Default constructor with no parameters passing in
    // public Pharmacist() {
    //     this.pharmacistID = 0; // Assign default ID
    //     this.inventory = new Inventory(); // Create a default Inventory object
    // }

    public Pharmacist(String id, String name, String role, String gender, int age) {
        super(id, name, role, gender, age);

        inventory = null;
    }

    public void init(Inventory inventory) {
        this.inventory = inventory;
    }
    
    // Constructor to initialize the pharmacistID and inventory
    // public Pharmacist(int pharmacistID, Inventory inventory) {
    //     this.pharmacistID = pharmacistID;
    //     this.inventory = inventory;
    // }

    // public int getPharmacistID() {
    //     return pharmacistID;
    // }

    public ArrayList<AppointmentOutcomeRecord> getRecords() {
        return this.records;
        // yet to be implemented
    }

    public void updatePrescriptionStatus(AppointmentOutcomeRecord record) {
      // yet to be implemented
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void requestReplenishment(ReplenishmentRequest request) {
     // yet to be implemented
    }
}

