package user;

import data.WriteFile;
import java.io.IOException;
import java.util.ArrayList;
import medicine.Inventory;
import medicine.Medicine;

public class Administrator extends Staff {
    private ArrayList<Staff> staffs;

    //Inventory object for managing medication
    private Inventory inventory;
    
    //Constructor to initialize Administrator with an empty staff list and inventory
    public Administrator(String id, String name, String role, String gender, int age) throws IOException {
        super(id, name, role, gender, age);
        this.staffs = null;
        this.inventory = null;
    }

    //Initialize the staff list from a CSV file
    public void init(ArrayList<Staff> staffs, Inventory inventory) {
        this.staffs = staffs;
        this.inventory = inventory;
    }

    public void displayStaffList() {
        for (Staff staff : staffs) {
            String staffID = staff.getID();
            System.out.println("Staff ID: " + staffID);
            String staffName = staff.getName();
            System.out.println("Staff Name: " + staffName);
            String staffRole = staff.getRole();
            System.out.println("Staff Role: " + staffRole);
            String staffGender = staff.getGender();
            System.out.println("Staff Gender: " + staffGender);
            int staffAge = staff.getAge();
            System.out.println("Staff Age: " + staffAge);
            System.out.println();
        }
    }

    //Manage Staff methods
    public void addStaff(Staff staff) {
        staffs.add(staff);
        System.out.println("Staff member added successfully.");
        //Write to file
        try {
            writeStaffListToFile();
        } catch (Exception e) {
            //Rollback if writing fails
            staffs.remove(staff);
        }
    }

    public void removeStaff(Staff staff) {
        if(staffs.remove(staff)) {
            System.out.println("Staff member removed successfully.");
            //Write to file
            try {
                writeStaffListToFile();
            } catch (Exception e) {
                System.out.println("Error updating file: " + e.getMessage());
                //Rollback if writing fails
                staffs.add(staff);
            }
        } else {
            System.out.println("Staff member not found.");
        }
    }

    public void updateStaff(Staff oldStaff, Staff newStaff) {
        int index = staffs.indexOf(oldStaff);
        if (index >= 0) {
            staffs.set(index, newStaff);
            System.out.println("Staff information updated successfully.");
            //Write to file
            try {
                writeStaffListToFile();
            } catch (Exception e) {
                //Rollback if writing fails
                staffs.set(index, oldStaff);
            }
        } else {
            System.out.println("Staff member not found.");
        }
    }

    private void writeStaffListToFile() {
        try {
            WriteFile.writeFile(new ArrayList<>(staffs), "Staff_List.csv");
            System.out.println("Staff list updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    //Appointment management method
    public void viewAppointments() {
        ///Work in progress
    }

    //View the entire inventory
    public void viewInventory() {
        ArrayList<Medicine> inventoryList = inventory.getInventory();
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

    //Update stock num and low stock alert num for an existing medicine (Method overload)
    public void updateInventoryStock (String medicineName, int stockNum, int lowStockAlert) {
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

    public ArrayList<Staff> getStaffList() {
        return this.staffs;
    }
}