package medicine;

import data.CsvCompatible;

public class Medicine implements CsvCompatible {
    private String name;
    private int initialStock, lowStockLevelAlert;

    public Medicine(String name, int initialStock, int lowStockLevelAlert) {
        this.name = name;
        this.initialStock = initialStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("%s,%d,%d", name, initialStock, lowStockLevelAlert);
    }
}
