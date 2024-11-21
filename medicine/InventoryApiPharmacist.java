package medicine;

import java.util.ArrayList;

/**
 * The InventoryApiPharmacist interface defines the contract for interacting with the inventory 
 * for the Pharmacist role. It includes methods to access the inventory, dispense prescriptions, 
 * and handle replenishment requests.
 * 
 * Pharmacists can view the inventory, dispense medicines based on prescriptions, and manage 
 * stock replenishment requests.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public interface InventoryApiPharmacist {
    /**
     * Retrieves all medicines currently available in the inventory.
     *
     * @return an ArrayList of Medicine objects that are available in the inventory.
     */
    public abstract ArrayList<Medicine> getInventory();

    /**
     * Dispenses the specified prescriptions by checking stock availability.
     * If the required stock is available, it updates the stock accordingly. 
     * If stock is insufficient, the method will return false and not update the inventory.
     *
     * @param prescription an ArrayList of Prescription objects to be dispensed.
     * @return true if the prescription can be fully dispensed, false otherwise.
     */
    public abstract boolean dispensePrescription(ArrayList<Prescription> prescription);

    /**
     * Handles a replenishment request by adding it to the list of pending requests.
     *
     * @param request a ReplenishmentRequest object that contains the details of the replenishment request.
     */
    public abstract void handleReplenishmentRequest(ReplenishmentRequest request);
}
