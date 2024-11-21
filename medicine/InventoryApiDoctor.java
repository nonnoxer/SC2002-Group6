package medicine;

import java.util.ArrayList;

/**
 * The InventoryApiDoctor interface defines the contract for accessing the inventory of medicines
 * for the Doctor role. It includes methods that allow doctors to retrieve the list of available medicines.
 * 
 * Doctors can view the inventory but do not have permissions to modify stock levels or handle replenishment requests.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public interface InventoryApiDoctor {
    /**
     * Retrieves all medicines currently available in the inventory.
     *
     * @return an ArrayList of Medicine objects that are available in the inventory.
     */
    public abstract ArrayList<Medicine> getInventory();
}
