package medicine;

public class ReplenishmentRequest {
    private String name;
    private ReplenishmentStatus status;
    private int stock;

    public ReplenishmentRequest(String name, int stock) {
        this.name = name;
        this.status = ReplenishmentStatus.Pending;
        this.stock = stock;
    }

    public void approveRequest(boolean approved) {
        this.status = approved ? ReplenishmentStatus.Approved : ReplenishmentStatus.Rejected;
    }

    public String getName() {
        return this.name;
    }

    public ReplenishmentStatus getStatus() {
        return this.status;
    }

    public int getStock() {
        return this.stock;
    }
}
