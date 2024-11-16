package medicine;

import java.io.IOException;
import java.util.ArrayList;
import data.ReadFile;
import data.WriteFile;

public class Inventory{
    private ArrayList<Medicine> medicines;
    private String medicineListPath;

    public Inventory(String medicineListPath) throws IOException{
        this.medicineListPath = medicineListPath;
        this.medicines = ReadFile.readMedicineListFile(this.medicineListPath);
    }

    public ArrayList<Medicine> getInventory(){
        return medicines;
    }

    public int setInventory(String name, int stock){
        for(int i=0; i<medicines.size(); i++){
            if (medicines.get(i).getName().equals(name)){
                medicines.get(i).setStock(stock);
                WriteFile.writeFile(medicines, this.medicineListPath);
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
                WriteFile.writeFile(medicines, this.medicineListPath);
                return 1;
            }
        }
        return 0;
    }

    public void addInventory(String name, int initialStock, int lowStockLevelAlert){
        medicines.add(new Medicine(name, initialStock, lowStockLevelAlert));
        WriteFile.writeFile(medicines, this.medicineListPath);
    }

    public void handleReplenishmentRequest(ReplenishmentRequest request) {
    addInventory(request.getName(), request.getStock(), 0);
    System.out.println("Replenishment request for " + request.getName() + " has been fulfilled.");
}
    
    public int removeInventory(String name){
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getName().equals(name)) {
                medicines.remove(i);
                return 1; 
            }
        }
        return 0;
    }
}
