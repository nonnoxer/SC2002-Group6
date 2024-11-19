package medicine;

public class Prescription {
    private String name;
    private int quantity;

    public Prescription(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String toString() {
        return String.format("%s (%d)", this.name, this.quantity);
    }
}
