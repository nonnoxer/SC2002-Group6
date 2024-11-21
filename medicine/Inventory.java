package medicine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import data.ReadFile;
import data.WriteFile;

/**
 * The Inventory class manages the inventory of medicines in the system. It provides functionality for 
 * adding, updating, dispensing, and removing medicines from the inventory, as well as handling 
 * replenishment requests. The class also interfaces with other user roles, such as Doctors, Pharmacists, 
 * and Administrators, to ensure proper management of medicine stocks and requests.
 * 
 * This class reads and writes inventory data to/from specified CSV files and provides methods for managing 
 * the stock levels, low stock alerts, and dispensing of prescriptions.
 * 
 * @see InventoryApiDoctor
 * @see InventoryApiPharmacist
 * @see InventoryApiAdministrator
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class Inventory implements InventoryApiDoctor, InventoryApiPharmacist, InventoryApiAdministrator {
    private HashMap<String, Medicine> medicines;
    private ArrayList<ReplenishmentRequest> requests;
    private String medicineListPath, requestListPath;

    /**
     * Constructs an Inventory instance by reading data from the specified CSV files for medicines 
     * and replenishment requests.
     *
     * @param medicineListPath the path to the medicine list CSV file.
     * @param requestListPath the path to the replenishment request CSV file.
     * @throws IOException if an error occurs while reading the files.
     */
    public Inventory(String medicineListPath, String requestListPath) throws IOException{
        this.medicineListPath = medicineListPath;
        this.requestListPath = requestListPath;
        this.medicines = new HashMap<>();
        ArrayList<Medicine> medicineList = ReadFile.readMedicineListFile(this.medicineListPath);
        for (Medicine medicine : medicineList) {
            this.medicines.put(medicine.getName(), medicine);
        }
        if (Files.notExists(Path.of(requestListPath))) {
            System.out.printf("%s does not exist, creating new database...\n\n", requestListPath);
            this.requests = new ArrayList<>();
            WriteFile.writeFile(this.requests, requestListPath);
        } else {
            this.requests = ReadFile.readRequestListFile(requestListPath);
        }
    }

    /**
     * Retrieves all medicines in the inventory.
     *
     * @return an ArrayList of all Medicine objects in the inventory.
     */
    public ArrayList<Medicine> getInventory() {
        return new ArrayList<>(medicines.values());
    }

    /**
     * Retrieves all replenishment requests.
     *
     * @return an ArrayList of all ReplenishmentRequest objects.
     */
    public ArrayList<ReplenishmentRequest> getRequests() {
        return this.requests;
    }

    /**
     * Updates the medicine file with the current list of medicines in the inventory.
     */
    private void updateMedicineFile() {
        WriteFile.writeFile(this.medicines.values(), this.medicineListPath);
    }

    /**
     * Updates the replenishment request file with the current list of requests.
     */
    private void updateRequestFile() {
        WriteFile.writeFile(this.requests, this.requestListPath);
    }

    /**
     * Updates the medicine stock in the inventory for the specified medicine name.
     * 
     * @param name the name of the medicine to update.
     * @param stock the new stock level.
     * @return 1 if the update was successful, 0 if the medicine was not found.
     */
    public int setInventory(String name, int stock) {
        Medicine medicine = this.medicines.get(name);
        if (medicine == null) return 0;


        medicine.setStock(stock);
        updateMedicineFile();
        return 1;
    }

    /**
     * Updates the medicine stock and low stock level alert for the specified medicine name.
     * 
     * @param name the name of the medicine to update.
     * @param stock the new stock level.
     * @param lowStockLevelAlert the new low stock level alert threshold.
     * @return 1 if the update was successful, 0 if the medicine was not found.
     */
    public int setInventory(String name, int stock, int lowStockLevelAlert){
        Medicine medicine = this.medicines.get(name);
        if (medicine == null) return 0;
        medicine.setStock(stock);
        medicine.setLowStockLevelAlert(lowStockLevelAlert);
        updateMedicineFile();
        return 1;
    }

    /**
     * Adds a new medicine to the inventory with the specified initial stock and low stock level alert.
     *
     * @param name the name of the medicine to add.
     * @param initialStock the initial stock of the medicine.
     * @param lowStockLevelAlert the low stock level alert threshold for the medicine.
     */
    public void addInventory(String name, int initialStock, int lowStockLevelAlert) {
        medicines.put(name, new Medicine(name, initialStock, lowStockLevelAlert));
        updateMedicineFile();
    }

    /**
     * Dispenses a prescription, reducing the stock of the medicines in the prescription. 
     * It checks whether there is enough stock for each medicine before dispensing.
     *
     * @param prescription the list of Prescription objects to dispense.
     * @return true if the prescription can be dispensed, false if any medicine in the prescription is unavailable or insufficient.
     */
    public boolean dispensePrescription(ArrayList<Prescription> prescription) {
        // Check whether prescription can be dispensed
        for (Prescription med : prescription) {
            Medicine stock = this.medicines.get(med.getName());
            if (stock == null || stock.getStock() < med.getQuantity()) return false;
        }

        for (Prescription med : prescription) {
            Medicine stock = this.medicines.get(med.getName());
            stock.dispenseStock(med.getQuantity());
        }
        updateMedicineFile();

        return true;
    }

    /**
     * Handles a replenishment request by adding it to the list of requests.
     *
     * @param request the ReplenishmentRequest to add.
     */
    public void handleReplenishmentRequest(ReplenishmentRequest request) {
        this.requests.add(request);
        updateRequestFile();
    }

    /**
     * Approves or rejects a replenishment request. If approved, the stock for the specified medicine is increased.
     *
     * @param request the ReplenishmentRequest to approve or reject.
     * @param approved true to approve the request, false to reject it.
     */
    public void approveReplenishmentRequest(ReplenishmentRequest request, boolean approved) {
        request.approveRequest(approved);
        updateRequestFile();
        if (approved) {
            Medicine medicine = this.medicines.get(request.getName());
            if (medicine == null) return;
            medicine.addStock(request.getStock());
            updateMedicineFile();
        }
    }
    
    /**
     * Removes a medicine from the inventory by its name.
     *
     * @param name the name of the medicine to remove.
     * @return 1 if the medicine was removed successfully, 0 if the medicine was not found.
     */
    public int removeInventory(String name){
        Medicine medicine = this.medicines.remove(name);
        if (medicine == null) return 0;

        updateMedicineFile();
        return 1;
    }
}
