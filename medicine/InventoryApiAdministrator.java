package medicine;

import java.util.ArrayList;

/**
 * The InventoryApiAdministrator interface defines the contract for managing the inventory and replenishment requests
 * for the Administrator role. It includes methods for interacting with the inventory, setting stock levels, adding or removing
 * medicines, and handling replenishment requests.
 * 
 * This interface allows administrators to manage the inventory, approve replenishment requests, and ensure the proper 
 * handling of stock levels and alerts.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public interface InventoryApiAdministrator {
    /**
     * Retrieves all medicines in the inventory.
     *
     * @return an ArrayList of all Medicine objects in the inventory.
     */
    public abstract ArrayList<Medicine> getInventory();
    
    /**
     * Updates the stock level of an existing medicine in the inventory.
     * 
     * @param name the name of the medicine to update.
     * @param stock the new stock level of the medicine.
     * @return 1 if the update was successful, 0 if the medicine was not found.
     */
    public abstract int setInventory(String name, int stock);

    /**
     * Updates the stock level and the low stock alert level of an existing medicine in the inventory.
     * 
     * @param name the name of the medicine to update.
     * @param stock the new stock level of the medicine.
     * @param lowStockLevelAlert the new low stock level alert threshold for the medicine.
     * @return 1 if the update was successful, 0 if the medicine was not found.
     */
    public abstract int setInventory(String name, int stock, int lowStockLevelAlert);

    /**
     * Adds a new medicine to the inventory with the specified initial stock and low stock level alert.
     * 
     * @param name the name of the medicine to add.
     * @param initialStock the initial stock of the medicine.
     * @param lowStockLevelAlert the low stock level alert threshold for the medicine.
     */
    public abstract void addInventory(String name, int initialStock, int lowStockLevelAlert);

    /**
     * Removes a medicine from the inventory by its name.
     * 
     * @param name the name of the medicine to remove.
     * @return 1 if the medicine was removed successfully, 0 if the medicine was not found.
     */
    public abstract int removeInventory(String name);

    /**
     * Retrieves all replenishment requests made for medicines in the inventory.
     * 
     * @return an ArrayList of all ReplenishmentRequest objects.
     */
    public abstract ArrayList<ReplenishmentRequest> getRequests();

    /**
     * Approves or rejects a replenishment request. If approved, the stock for the specified medicine is increased.
     * 
     * @param request the ReplenishmentRequest to approve or reject.
     * @param approved true to approve the request, false to reject it.
     */
    public abstract void approveReplenishmentRequest(ReplenishmentRequest request, boolean approved);
}
