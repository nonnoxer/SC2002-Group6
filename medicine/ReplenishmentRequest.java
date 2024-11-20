package medicine;

import java.io.IOException;

import data.CsvCompatible;

public class ReplenishmentRequest implements CsvCompatible {
    private String name;
    private ReplenishmentStatus status;
    private int stock;

    public ReplenishmentRequest(String name, int stock) {
        this.name = name;
        this.status = ReplenishmentStatus.Pending;
        this.stock = stock;
    }

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

    public String toCsv() {
        return String.format("%s,%s,%d", this.name, this.status.toString(), this.stock);
    }
}
