package medicine;

public class Medicine {
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
}
