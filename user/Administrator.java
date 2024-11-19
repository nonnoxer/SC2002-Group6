package user;

import data.appointment.AppointmentDatabase;
import data.user.UserDatabaseApiAdministrator;

import java.io.IOException;
import java.util.ArrayList;
import medicine.Inventory;
import medicine.Medicine;

public class Administrator extends Staff {
    private UserDatabaseApiAdministrator userDb;
    private AppointmentDatabase appointmentDb;

    //Inventory object for managing medication
    private Inventory inventory;
    
    //Constructor to initialize Administrator with an empty staff list and inventory
    public Administrator(UserId id, String name, Role role, String gender, int age) throws IOException {
        super(id, name, role, gender, age);
        this.userDb = null;
        this.inventory = null;
    }

    //Initialize the staff list from a CSV file
    public void init(UserDatabaseApiAdministrator userDb, AppointmentDatabase appointmentDb, Inventory inventory) {
        this.userDb = userDb;
        this.appointmentDb = appointmentDb;
        this.inventory = inventory;
    }

    public void displayStaffList() {
        for (Staff staff : this.userDb.getStaff()) {
            UserId staffID = staff.getId();
            System.out.println("Staff ID: " + staffID.toString());
            String staffName = staff.getName();
            System.out.println("Staff Name: " + staffName);
            String staffRole = staff.getRole().toString();
            System.out.println("Staff Role: " + staffRole);
            String staffGender = staff.getGender();
            System.out.println("Staff Gender: " + staffGender);
            int staffAge = staff.getAge();
            System.out.println("Staff Age: " + staffAge);
            System.out.println();
        }
    }

    //Manage Staff methods
    public void addStaff(String name, Role role, String gender, int age) {
        this.userDb.addStaff(name, role, gender, age);
        System.out.println("Staff member added successfully.");
    }

    public void removeStaff(UserId id) {
        Staff result = this.userDb.removeStaff(id);
        if (result != null) {
            System.out.println("Staff member removed successfully.");
        }
        else {
            System.out.println("Staff member not found.");
        }
    }

    public void updateStaff(UserId id, String newName, Role newRole, String newGender, int newAge) {
        Staff result = this.userDb.updateStaff(id, newName, newRole, newGender, newAge);
        if (result != null) {
            System.out.println("Staff information updated successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    //Appointment management method
    public void viewAppointments() {
        ///Work in progress
        // do stuff with this.appointmentDb
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
}