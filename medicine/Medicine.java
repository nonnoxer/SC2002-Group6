package medicine;

import data.CsvCompatible;

public class Medicine implements CsvCompatible {
    private String name;
    private int stock, lowStockLevelAlert;

    public Medicine(String name, int initialStock, int lowStockLevelAlert) {
        this.name = name;
        this.stock = initialStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    public String getName() {
        return this.name;
    }
    
    public int getStock(){
        return this.stock;
    }
    
    public int getLowStockLevelAlert(){
        return this.lowStockLevelAlert;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setStock(int stock){
        this.stock = stock;
    }
    
    public void setLowStockLevelAlert(int lowStockLevelAlert){
        this.lowStockLevelAlert = lowStockLevelAlert;
    }
    
    public String toString() {
        return String.format("%s,%d,%d", name, stock, lowStockLevelAlert);
    }
}
