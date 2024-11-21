package user;

import data.appointment.AppointmentDatabaseApiAdministrator;
import data.user.UserDatabaseApiAdministrator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appointment.Appointment;
import medicine.InventoryApiAdministrator;
import medicine.Medicine;
import medicine.Prescription;
import medicine.ReplenishmentRequest;
import medicine.ReplenishmentStatus;
import record.AppointmentOutcomeRecord;

/**
 * Represents an Administrator in the system who has additional responsibilities
 * over a basic staff member, including managing staff, appointments, and inventory.
 * The Administrator can interact with databases for users, appointments, and inventory.
 * 
 * @author LOH HAN MENG
 * @version 1.0
 * @since 2024-11-21
 */
public class Administrator extends Staff {
    private UserDatabaseApiAdministrator userDb;
    private AppointmentDatabaseApiAdministrator appointmentDb;

    //Inventory object for managing medication
    private InventoryApiAdministrator inventory;
    
    /**
     * Constructs an Administrator object with the given parameters and initializes the databases to null.
     *
     * @param id      The user ID of the administrator.
     * @param name    The name of the administrator.
     * @param role    The role of the administrator.
     * @param gender  The gender of the administrator.
     * @param age     The age of the administrator.
     */
    public Administrator(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);
        this.userDb = null;
        this.inventory = null;
    }

    /**
     * Initializes the Administrator with the necessary database references for managing staff, appointments, and inventory.
     *
     * @param userDb   The User Database API Administrator.
     * @param appointmentDb The Appointment Database API Administrator.
     * @param inventory   The Inventory API Administrator.
     */    public void init(UserDatabaseApiAdministrator userDb, AppointmentDatabaseApiAdministrator appointmentDb, InventoryApiAdministrator inventory) {
        this.userDb = userDb;
        this.appointmentDb = appointmentDb;
        this.inventory = inventory;
    }

    /**
     * Displays a list of all staff members in the system.
     * Prints the details of each staff member including ID, name, role, gender, and age.
     */
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

    /**
     * Adds a new staff member to the system.
     *
     * @param name    The name of the new staff member.
     * @param role    The role of the new staff member.
     * @param gender  The gender of the new staff member.
     * @param age     The age of the new staff member.
     */
    public void addStaff(String name, Role role, String gender, int age) {
        this.userDb.addStaff(name, role, gender, age);
        System.out.println("Staff member added successfully.");
    }

    /**
     * Removes an existing staff member from the system.
     *
     * @param id The unique identifier of the staff member to remove.
     */
    public void removeStaff(UserId id) {
        Staff result = this.userDb.removeStaff(id);
        if (result != null) {
            System.out.println("Staff member removed successfully.");
        }
        else {
            System.out.println("Staff member not found.");
        }
    }

    /**
     * Updates the details of an existing staff member.
     *
     * @param id        The unique identifier of the staff member to update.
     * @param newName   The new name for the staff member.
     * @param newRole   The new role for the staff member.
     * @param newGender The new gender for the staff member.
     * @param newAge    The new age for the staff member.
     */
    public void updateStaff(UserId id, String newName, Role newRole, String newGender, int newAge) {
        Staff result = this.userDb.updateStaff(id, newName, newRole, newGender, newAge);
        if (result != null) {
            System.out.println("Staff information updated successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    /**
     * Views and displays the list of all appointments in the system.
     */
    public void viewAppointments() {
        HashMap<Integer, Appointment> appointments = appointmentDb.getAppointments();
        for (Map.Entry<Integer, Appointment> entry : appointments.entrySet()) {
            Appointment appointment = entry.getValue();
            AppointmentOutcomeRecord record = appointment.getRecord();     

            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("  Doctor ID: " + appointment.getDoctorId());
            System.out.println("  Patient ID: " + appointment.getPatientId());
            System.out.println("  Status: " + appointment.getAppointmentStatus());
            System.out.println("  Slot: " + appointment.getSlot().getDate());
            

            if (record != null) {
                System.out.println("  Record:");
                System.out.println("    Appointment ID: " + record.getAppointmentId());
                System.out.println("    Service Type: " + record.getServiceType());
                System.out.println("    Consultation Notes: " + record.getConsultationNotes());
                System.out.println("    Prescription Status: " + record.getPrescriptionStatus());
                System.out.println("    Diagnoses: ");
                System.out.println("      - " + record.getDiagnoses().toString());
                System.out.println("    Treatment Plan: ");
                System.out.println("      - " + record.getTreatmentPlan().toString());
                if (record.getPrescription() != null && !record.getPrescription().isEmpty()) {
                    System.out.println("    Prescriptions:");
                    for (Prescription prescription : record.getPrescription()) {
                        System.out.println("      - " + prescription);
                    }
                }
            }
            
            System.out.println();
        }
    }


    /**
     * Retrieves and returns the current inventory of medicines.
     *
     * @return A list of all medicines in the inventory.
     */
    public ArrayList<Medicine> getInventory() {
        return this.inventory.getInventory();
    }

    /**
     * Updates the stock number of an existing medicine in the inventory.
     *
     * @param medicineName The name of the medicine to update.
     * @param stockNum     The new stock number for the medicine.
     */
    public void updateInventoryStock(String medicineName, int stockNum) {
        int updated = inventory.setInventory(medicineName, stockNum);
        if (updated == 1) {
            System.out.println("Inventory updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    /**
     * Updates the stock number and the low stock alert number of an existing medicine in the inventory.
     *
     * @param medicineName   The name of the medicine to update.
     * @param stockNum       The new stock number for the medicine.
     * @param lowStockAlert  The new low stock alert number for the medicine.
     */
    public void updateInventoryStock (String medicineName, int stockNum, int lowStockAlert) {
        int updated = inventory.setInventory(medicineName, stockNum, lowStockAlert);
        if (updated == 1) {
            System.out.println("Inventory and low stock alert updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    } 

    /**
     * Adds a new medicine to the inventory with the given details.
     *
     * @param medicineName   The name of the new medicine.
     * @param initialStock   The initial stock of the new medicine.
     * @param lowStockAlert  The low stock alert threshold for the new medicine.
     */
    public void addNewMedicine(String medicineName, int initialStock, int lowStockAlert) {
        inventory.addInventory(medicineName, initialStock, lowStockAlert);
        System.out.println("New medicine added to the inventory: " + medicineName);
    }

    /**
     * Removes an existing medicine from the inventory.
     *
     * @param medicineName The name of the medicine to remove.
     */
    public void removeMedicine(String medicineName) {
        int updated = inventory.removeInventory(medicineName);
        if (updated == 1) {
            System.out.println(medicineName + "removed from the inventory.");
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }

    /**
     * Retrieves all pending replenishment requests from the inventory.
     *
     * @return A list of pending replenishment requests.
     */
    public ArrayList<ReplenishmentRequest> getPendingRequests() {
        ArrayList<ReplenishmentRequest> result = new ArrayList<>();
        for (ReplenishmentRequest request : this.inventory.getRequests()) {
            if (request.getStatus().equals(ReplenishmentStatus.Pending)) {
                result.add(request);
            }
        }
        return result;
    }

    /**
     * Approves or rejects a replenishment request.
     *
     * @param request  The replenishment request to approve or reject.
     * @param approved A boolean indicating whether the request is approved (true) or rejected (false).
     */
    public void approveRequest(ReplenishmentRequest request, boolean approved) {
        this.inventory.approveReplenishmentRequest(request, approved);
    }
}