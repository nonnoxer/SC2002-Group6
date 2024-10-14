package JavaProject;

import java.util.List;

public class Pharmacist extends User {
    private int pharmacistID;
    public Inventory inventory;
    
    // Default constructor with no parameters passing in
    public Pharmacist() {
        this.pharmacistID = 0; // Assign default ID
        this.inventory = new Inventory(); // Create a default Inventory object
    }
    
    // Constructor to initialize the pharmacistID and inventory
    public Pharmacist(int pharmacistID, Inventory inventory) {
        this.pharmacistID = pharmacistID;
        this.inventory = inventory;
    }

    public int getPharmacistID() {
        return pharmacistID;
    }

    public void viewAppOutcome() {
        System.out.println("Viewing appointment outcome record...");
        // yet to be implemented
    }

    public void updatePrescriptionStatus() {
        System.out.println("Updating prescription status...");
      // yet to be implemented
    }

    public void monitorInventory() {
        System.out.println("Monitoring inventory...");
     // yet to be implemented
    }

    public void requestReplenishment() {
        System.out.println("Requesting replenishment...");
     // yet to be implemented
    }

}

