package medicine;

import data.CsvCompatible;

/**
 * The Medicine class represents a medicine in the inventory system. It encapsulates details 
 * about the medicine, including its name, stock quantity, and low stock level alert. It also provides 
 * methods for managing and updating the stock, checking stock levels, and exporting data to CSV format.
 * 
 * The Medicine class implements the CsvCompatible interface, allowing it to be serialized 
 * to and from CSV files.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class Medicine implements CsvCompatible {
    private String name;
    private int stock, lowStockLevelAlert;

    /**
     * Constructor for the Medicine class.
     * 
     * @param name the name of the medicine.
     * @param initialStock the initial stock quantity of the medicine.
     * @param lowStockLevelAlert the stock level that triggers a low stock alert.
     */
    public Medicine(String name, int initialStock, int lowStockLevelAlert) {
        this.name = name;
        this.stock = initialStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    /**
     * Retrieves the name of the medicine.
     *
     * @return the name of the medicine.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Retrieves the current stock of the medicine.
     *
     * @return the current stock quantity of the medicine.
     */
    public int getStock(){
        return this.stock;
    }
    
    /**
     * Retrieves the low stock alert threshold for the medicine.
     *
     * @return the low stock level threshold.
     */
    public int getLowStockLevelAlert(){
        return this.lowStockLevelAlert;
    }

    /**
     * Checks if the stock of the medicine is below the low stock level alert threshold.
     *
     * @return true if stock is less than the low stock level alert, otherwise false.
     */
    public boolean checkLowStock() {
        return this.stock < this.lowStockLevelAlert;
    }
    
    /**
     * Sets the name of the medicine.
     * 
     * @param name the new name for the medicine.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the stock quantity of the medicine.
     * 
     * @param stock the new stock quantity for the medicine.
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Dispenses a specified quantity of the medicine, reducing the stock.
     * 
     * @param stock the quantity of the medicine to dispense.
     */
    public void dispenseStock(int stock) {
        this.stock -= stock;
    }

    /**
     * Adds stock to the current stock quantity of the medicine.
     * 
     * @param stock the quantity to add to the medicine's stock.
     */
    public void addStock(int stock) {
        this.stock += stock;
    }
    
    /**
     * Sets the low stock alert threshold for the medicine.
     * 
     * @param lowStockLevelAlert the new low stock level alert threshold.
     */
    public void setLowStockLevelAlert(int lowStockLevelAlert){
        this.lowStockLevelAlert = lowStockLevelAlert;
    }
    
    /**
     * Converts the Medicine object into a CSV-formatted string for exporting or saving to a file.
     *
     * @return a string representing the Medicine object in CSV format: 
     *         "name,stock,lowStockLevelAlert".
     */
    public String toCsv() {
        return String.format("%s,%d,%d", name, stock, lowStockLevelAlert);
    }

    /**
     * Returns a string representation of the Medicine object.
     *
     * @return a string in the format "Medicine{name='name', stock='stock', lowStockLevelAlert='lowStockLevelAlert'}".
     */
    public String toString(){
        return "Medicine{name='" + name + "', stock='" + stock + "', lowStockLevelAlert='" + lowStockLevelAlert + "'}";
    }
}
