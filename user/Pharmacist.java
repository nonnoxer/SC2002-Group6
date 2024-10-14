package JavaProject;

import java.util.List;

public class Pharmacist extends User {
    private int pharmacistID;
    public Inventory inventory;

    // Constructor to initialize the pharmacistID and inventory
    public Pharmacist(int pharmacistID, Inventory inventory) {
        this.pharmacistID = pharmacistID;
        this.inventory = inventory;
    }

    public int getPharmacistID() {
        return pharmacistID;
    }

    // Haven't finished yet implementing the methods
    
    public void viewPrescription() {
        System.out.println("Viewing prescriptions...");
    }

    public void updatePrescriptionStatus() {
        System.out.println("Updating prescription status...");
    }

    public void monitorInventory() {
        System.out.println("Monitoring inventory...");
    }

    public void requestReplenishment() {
        System.out.println("Requesting replenishment...");
    }

}
