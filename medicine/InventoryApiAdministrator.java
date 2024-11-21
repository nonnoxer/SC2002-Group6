package medicine;

import java.util.ArrayList;

public interface InventoryApiAdministrator {
    public abstract ArrayList<Medicine> getInventory();
    public abstract int setInventory(String name, int stock);
    public abstract int setInventory(String name, int stock, int lowStockLevelAlert);
    public abstract void addInventory(String name, int initialStock, int lowStockLevelAlert);
    public abstract int removeInventory(String name);
    public abstract ArrayList<ReplenishmentRequest> getRequests();
    public abstract void approveReplenishmentRequest(ReplenishmentRequest request, boolean approved);
}
