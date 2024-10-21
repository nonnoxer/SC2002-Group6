package user;

import data.ReadFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import medicine.Inventory;
import medicine.Medicine;

public class Administrator extends Staff {

    //List to store staff members (Doctors & Pharmacists)
    private List<Staff> staffList;

    //Inventory object for managing medication
    private Inventory inventory;
    
    //Constructor to initialize Administrator with an empty staff list and inventory
    public Administrator(String id, String name, String role, String gender, int age) throws IOException {
        super(id, name, role, gender, age);
        this.staffList = new ArrayList<>();
        this.inventory = new Inventory("Medicine_List.csv");
    }

    //Initialize the staff list from a CSV file
    public void initializeStaffList() throws IOException {
        this.staffList = new ArrayList<>();
        Staff[] loadedStaff = ReadFile.readStaffListFile("Staff_List.csv");
        for(Staff staff : loadedStaff) {
            staffList.add(staff);
        }
    }

    //Manage Staff methods
    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println("Staff member added successfully.");
        //Work in progress
    }

    public void removeStaff(Staff staff) {
        staffList.remove(staff);
        System.out.println("Staff member removed successfully.");
        //Work in progress
    }

    public void updateStaff(Staff oldStaff, Staff newStaff) {
        int index = staffList.indexOf(oldStaff);
        if (index >= 0) {
            staffList.set(index, newStaff);
            System.out.println("Staff information updated.");
        }
    }

    public void displayStaffList() {
        for (Staff staff : staffList) {
            System.out.println(staff);
        }
    }

    //Appointment management method
    public void viewAppointments() {
        ///Work in progress
    }

    //View the entire inventory
    public void viewInventory() {
        Medicine[] inventoryList = inventory.GetInventory();
        for(Medicine medicine: inventoryList) {
            System.out.println("Medicine: " + medicine.getName() + ", Stock: " + medicine.getStock() + ", Low Stock Alert Level: " + medicine.getLowStockLevelAlert());
        }
    }

    //Update stock num for an existing medicine 
    public void updateInventoryStock(String medicineName, int stockNum) {
        int updated = inventory.setInventory(medicineName, stockNum);
        if (updated == 1) {
            System.out.println("Inventory updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    //Update stock num and low stock alert num for an existing medicine
    public void updateInventoryStockWithAlert (String medicineName, int stockNum, int lowStockAlert) {
        int updated = inventory.setInventory(medicineName, stockNum, lowStockAlert);
        if (updated == 1) {
            System.out.println("Inventory and low stock alert updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    } 

    //Add new medicine to the inventory
    public void addNewMedicine(String medicineName, int initialStock, int lowStockAlert) {
        inventory.addInventory(medicineName, initialStock, lowStockAlert);
        System.out.println("New medicine added to the inventory: " + medicineName);
    }

    public void removeMedicine(String medicineName) {
        int updated = inventory.removeInventory(medicineName);
        if (updated == 1) {
            System.out.println(medicineName + "removed from the inventory.");
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }
}
