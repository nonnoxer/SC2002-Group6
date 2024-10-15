package medicine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import data.ReadFile;
import data.WriteFile;
import medicine.Medicine;

public class Inventory{
    private static Medicine[] medicine_list;
    private String path;
    public Inventory(String path) throws IOException{
        this.path = path;
        try{
            medicine_list = ReadFile.readMedicineListFile(path);
        } catch (IOException e){
            throw new IOException("Invalid path: " + path);
        }
    }

    public Medicine[] GetInventory(){
        return medicine_list;
    }

    public int setInventory(String name, int stock){
        for(int i=0; i<medicine_list.length; i++){
            if (medicine_list[i].getName().equals(name)){
                medicine_list[i].setStock(stock);
                WriteFile.writeFile(medicine_list, this.path);
                return 1;
            }
        }
        return 0;
    }

    public int setInventory(String name, int stock, int lowStockLevelAlert){
        for(int i=0; i<medicine_list.length; i++){
            if (medicine_list[i].getName().equals(name)){
                medicine_list[i].setStock(stock);
                medicine_list[i].setLowStockLevelAlert(lowStockLevelAlert);
                WriteFile.writeFile(medicine_list, this.path);
                return 1;
            }
        }
        return 0;
    }

    public void addInventory(String name, int initialStock, int lowStockLevelAlert){
        ArrayList<Medicine> dynamicMedicineList = new ArrayList<>(Arrays.asList(medicine_list));
        dynamicMedicineList.add(new Medicine(name, initialStock, lowStockLevelAlert));
        medicine_list = dynamicMedicineList.toArray(medicine_list);
        WriteFile.writeFile(medicine_list, this.path);
    }
    
    public int removeInventory(String name){
        ArrayList<Medicine> dynamicMedicineList = new ArrayList<>(Arrays.asList(medicine_list));
        for (int i = 0; i < dynamicMedicineList.size(); i++) {
            if (dynamicMedicineList.get(i).getName().equals(name)) {
                dynamicMedicineList.remove(i);
                medicine_list = dynamicMedicineList.stream().filter(medicine -> medicine != null).toArray(Medicine[]::new);
                WriteFile.writeFile(medicine_list, this.path);
                return 1; 
            }
        }
        return 0;
    }
}
