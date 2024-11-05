package medicine;

import java.io.IOException;
import java.util.ArrayList;
import data.ReadFile;
import data.WriteFile;

public class Inventory{
    private ArrayList<Medicine> medicines;

    public Inventory(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    public ArrayList<Medicine> getInventory(){
        return medicines;
    }

    public int setInventory(String name, int stock){
        // for(int i=0; i<medicines.size(); i++){
        //     if (medicines.get(i).getName().equals(name)){
        //         medicines.get(i).setStock(stock);
        //         WriteFile.writeFile(medicines, this.path);
        //         return 1;
        //     }
        // }
        return 0;
    }

    public int setInventory(String name, int stock, int lowStockLevelAlert){
        for(int i=0; i<medicines.size(); i++){
            if (medicines.get(i).getName().equals(name)){
                medicines.get(i).setStock(stock);
                medicines.get(i).setLowStockLevelAlert(lowStockLevelAlert);
                // WriteFile.writeFile(medicines, this.path);
                return 1;
            }
        }
        return 0;
    }

    public void addInventory(String name, int initialStock, int lowStockLevelAlert){
        medicines.add(new Medicine(name, initialStock, lowStockLevelAlert));
        // WriteFile.writeFile(medicines, this.path);
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
