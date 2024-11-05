package medicine;

public class ReplenishmentRequest {
    private String name, status;
    private int stock;

    public ReplenishmentRequest(String name, int stock) {
        this.name = name;
        this.status = "Pending";
        this.stock = stock;
    }

    public void approveRequest(boolean approved) {
        this.status = approved ? "Approved" : "Rejected";
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public int getStock() {
        return this.stock;
    }
}
