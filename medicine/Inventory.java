package medicine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import data.ReadFile;
import data.WriteFile;

public class Inventory implements InventoryApiDoctor, InventoryApiPharmacist, InventoryApiAdministrator {
    private HashMap<String, Medicine> medicines;
    private ArrayList<ReplenishmentRequest> requests;
    private String medicineListPath, requestListPath;

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

    public ArrayList<Medicine> getInventory() {
        return new ArrayList<>(medicines.values());
    }

    public ArrayList<ReplenishmentRequest> getRequests() {
        return this.requests;
    }

    private void updateMedicineFile() {
        WriteFile.writeFile(this.medicines.values(), this.medicineListPath);
    }

    private void updateRequestFile() {
        WriteFile.writeFile(this.requests, this.requestListPath);
    }

    public int setInventory(String name, int stock) {
        Medicine medicine = this.medicines.get(name);
        if (medicine == null) return 0;


        medicine.setStock(stock);
        updateMedicineFile();
        return 1;
    }

    public int setInventory(String name, int stock, int lowStockLevelAlert){
        Medicine medicine = this.medicines.get(name);
        if (medicine == null) return 0;
        medicine.setStock(stock);
        medicine.setLowStockLevelAlert(lowStockLevelAlert);
        updateMedicineFile();
        return 1;
    }

    public void addInventory(String name, int initialStock, int lowStockLevelAlert) {
        medicines.put(name, new Medicine(name, initialStock, lowStockLevelAlert));
        updateMedicineFile();
    }

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

    public void handleReplenishmentRequest(ReplenishmentRequest request) {
        this.requests.add(request);
        updateRequestFile();
    }

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
    
    public int removeInventory(String name){
        Medicine medicine = this.medicines.remove(name);
        if (medicine == null) return 0;

        updateMedicineFile();
        return 1;
    }
}
