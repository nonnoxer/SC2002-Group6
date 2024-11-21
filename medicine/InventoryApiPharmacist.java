package medicine;

import java.util.ArrayList;

public interface InventoryApiPharmacist {
    public abstract ArrayList<Medicine> getInventory();
    public abstract boolean dispensePrescription(ArrayList<Prescription> prescription);
    public abstract void handleReplenishmentRequest(ReplenishmentRequest request);
}
