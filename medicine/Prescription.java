package medicine;

/**
 * The Prescription class represents a prescription for a medicine with a specified quantity.
 * It contains details about the medicine's name and the quantity prescribed. The class provides methods
 * to get and set these details and to print the prescription in a user-friendly format.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class Prescription {
    private String name;
    private int quantity;

    /**
     * Constructor for the Prescription class.
     * 
     * @param name the name of the prescribed medicine.
     * @param quantity the quantity of the medicine prescribed.
     */
    public Prescription(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Retrieves the name of the prescribed medicine.
     * 
     * @return the name of the prescribed medicine.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the quantity of the prescribed medicine.
     * 
     * @return the quantity of the prescribed medicine.
     */
    public int getQuantity() {
        return this.quantity;
    }
    
    /**
     * Sets the quantity of the prescribed medicine.
     * 
     * @param quantity the new quantity of the prescribed medicine.
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the Prescription object.
     * The string includes the medicine's name and prescribed quantity, formatted as:
     * "{name} ({quantity})"
     * 
     * @return a string representation of the prescription.
     */
    public String toString() {
        return String.format("%s (%d)", this.name, this.quantity);
    }
}
