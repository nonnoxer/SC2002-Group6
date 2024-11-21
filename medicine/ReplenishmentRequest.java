package medicine;

import data.CsvCompatible;
import java.io.IOException;

/**
 * The ReplenishmentRequest class represents a replenishment request for a specific medicine, including its current stock and approval status.
 * This class allows tracking and updating of inventory restocking requests and their processing.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class ReplenishmentRequest implements CsvCompatible {
    private String name;
    private ReplenishmentStatus status;
    private int stock;

    /**
     * Creates a new replenishment request with a specified name and stock quantity.
     * The request is initially set to the "Pending" status.
     *
     * @param name  the name of the medicine for which the replenishment is requested
     * @param stock the number of units requested to replenish
     */
    public ReplenishmentRequest(String name, int stock) {
        this.name = name;
        this.status = ReplenishmentStatus.Pending;
        this.stock = stock;
    }

    /**
     * Creates a new replenishment request from a CSV line. The line must contain three elements:
     *
     * The name of the medicine
     * The status of the request (one of "Pending", "Approved", "Rejected")
     * The number of units requested for replenishment
     *
     * @param line an array of strings representing the CSV line
     * @throws IOException if the line has an invalid format or contains invalid data
     */
    public ReplenishmentRequest(String[] line) throws IOException {
        if (line.length != 3) {
            String line_full = String.join(",", line);
            throw new IOException("Invalid line " + line_full + ": expected 3 elements.");
        }

        this.name = line[0];

        try {
            this.stock = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid line: expected " + line[2] + " to be an integer.");
        }
        try {
            this.status = ReplenishmentStatus.valueOf(line[1]);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid line: expected " + line[1] + " to be one of 'Pending', 'Approved', 'Rejected'.");
        }
    }

    /**
     * Approves or rejects the replenishment request. 
     * The status is set to either "Approved" or "Rejected" based on the given approval flag.
     *
     * @param approved if true, the request is approved; if false, the request is rejected
     */
    public void approveRequest(boolean approved) {
        this.status = approved ? ReplenishmentStatus.Approved : ReplenishmentStatus.Rejected;
    }

    /**
     * Gets the name of the medicine for the replenishment request.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the current status of the replenishment request.
     *
     * @return the status of the request (Pending, Approved, or Rejected)
     */
    public ReplenishmentStatus getStatus() {
        return this.status;
    }

    /**
     * Gets the number of units requested to replenish.
     *
     * @return the quantity of medicine requested
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Converts this replenishment request to a CSV string representation.
     *
     * @return a CSV formatted string representing the replenishment request
     */
    public String toCsv() {
        return String.format("%s,%s,%d", this.name, this.status.toString(), this.stock);
    }
}
