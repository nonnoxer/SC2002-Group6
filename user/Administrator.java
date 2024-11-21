package user;

import data.appointment.AppointmentDatabaseApiAdministrator;
import data.user.UserDatabaseApiAdministrator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appointment.Appointment;
import medicine.InventoryApiAdministrator;
import medicine.Medicine;
import medicine.ReplenishmentRequest;
import medicine.ReplenishmentStatus;

public class Administrator extends Staff {
    private UserDatabaseApiAdministrator userDb;
    private AppointmentDatabaseApiAdministrator appointmentDb;

    //Inventory object for managing medication
    private InventoryApiAdministrator inventory;
    
    //Constructor to initialize Administrator with an empty staff list and inventory
    public Administrator(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);
        this.userDb = null;
        this.inventory = null;
    }

    //Initialize the staff list from a CSV file
    public void init(UserDatabaseApiAdministrator userDb, AppointmentDatabaseApiAdministrator appointmentDb, InventoryApiAdministrator inventory) {
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
        HashMap<Integer, Appointment>appointments = appointmentDb.getAppointments();
        System.out.println(String.format("%-5s %-15s %-10s %-15s %-10s %-10s", "id", "doctor", "patient", "status", "slot", "record"));
        for (Map.Entry<Integer, Appointment> entry : appointments.entrySet()) {
            Appointment appointment = entry.getValue();
            System.out.println(String.format("%-5d %-15d %-10d %-15s %-10s %-10s", 
                appointment.getId(), 
                appointment.getDoctorId(), 
                appointment.getPatientId(), 
                appointment.getAppointmentStatus(), 
                appointment.getSlot(), 
                appointment.getRecord())
            );
        }
    }

    //View the entire inventory
    public ArrayList<Medicine> getInventory() {
        return this.inventory.getInventory();
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

    public ArrayList<ReplenishmentRequest> getPendingRequests() {
        ArrayList<ReplenishmentRequest> result = new ArrayList<>();
        for (ReplenishmentRequest request : this.inventory.getRequests()) {
            if (request.getStatus().equals(ReplenishmentStatus.Pending)) {
                result.add(request);
            }
        }
        return result;
    }

    public void approveRequest(ReplenishmentRequest request, boolean approved) {
        this.inventory.approveReplenishmentRequest(request, approved);
    }
}