package medicine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import data.ReadFile;
import data.WriteFile;

public class Inventory{
    private ArrayList<Medicine> medicines;
    private ArrayList<ReplenishmentRequest> requests;
    private String medicineListPath, requestListPath;

    public Inventory(String medicineListPath, String requestListPath) throws IOException{
        this.medicineListPath = medicineListPath;
        this.requestListPath = requestListPath;
        this.medicines = ReadFile.readMedicineListFile(this.medicineListPath);
        if (Files.notExists(Path.of(requestListPath))) {
            System.out.printf("%s does not exist, creating new database...\n\n", requestListPath);
            this.requests = new ArrayList<>();
            WriteFile.writeFile(this.requests, requestListPath);
        } else {
            this.requests = ReadFile.readRequestListFile(requestListPath);
        }
    }

    public ArrayList<Medicine> getInventory(){
        return medicines;
    }

    public ArrayList<ReplenishmentRequest> getRequests() {
        return this.requests;
    }

    private void updateMedicineFile() {
        WriteFile.writeFile(this.medicines, this.medicineListPath);
    }

    private void updateRequestFile() {
        WriteFile.writeFile(this.requests, this.requestListPath);
    }

    public int setInventory(String name, int stock){
        for(int i=0; i<medicines.size(); i++){
            if (medicines.get(i).getName().equals(name)){
                medicines.get(i).setStock(stock);
                updateMedicineFile();
                return 1;
            }
        }
        return 0;
    }

    public int setInventory(String name, int stock, int lowStockLevelAlert){
        for(int i=0; i<medicines.size(); i++){
            if (medicines.get(i).getName().equals(name)){
                medicines.get(i).setStock(stock);
                medicines.get(i).setLowStockLevelAlert(lowStockLevelAlert);
                updateMedicineFile();
                return 1;
            }
        }
        return 0;
    }

    public void addInventory(String name, int initialStock, int lowStockLevelAlert){
        medicines.add(new Medicine(name, initialStock, lowStockLevelAlert));
        updateMedicineFile();
    }

    public void handleReplenishmentRequest(ReplenishmentRequest request) {
        this.requests.add(request);
        updateRequestFile();
    }

    public void approveReplenishmentRequest(ReplenishmentRequest request, boolean approved) {
        request.approveRequest(approved);
        updateRequestFile();
        if (approved) {
            for (Medicine medicine : this.medicines) {
                if (medicine.getName().equals(request.getName())) {
                    medicine.addStock(request.getStock());
                    break;
                }
            }
            updateMedicineFile();
        }
    }
    
    public int removeInventory(String name){
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getName().equals(name)) {
                medicines.remove(i);
                updateMedicineFile();
                return 1; 
            }
        }
        return 0;
    }
}
