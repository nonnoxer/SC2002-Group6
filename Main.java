
/**
 * The main entry point of the Hospital Management System application.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
class Main {
    
    /**
     * The main method which serves as the entry point for the application.
     * It initializes the Hospital Management System with predefined file paths for users, inventory, appointments, and accounts.
     * Then, it starts the system, allowing user interaction with the hospital management features.
     * 
     * The following files are required for initialization:
     * - Staff_List.csv: A CSV file containing the list of staff members.
     * - Patient_List.csv: A CSV file containing the list of patients.
     * - Medicine_List.csv: A CSV file containing the list of medicines in the inventory.
     * - Replenishment_Request_List.csv: A CSV file containing the list of replenishment requests for the inventory.
     * - Account_List.csv: A CSV file containing user account information.
     * - Appointment_List.csv: A CSV file containing patient appointments.
     * 
     * 
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem(
            "Staff_List.csv", 
            "Patient_List.csv", 
            "Medicine_List.csv", 
            "Replenishment_Request_List.csv",
            "Account_List.csv", 
            "Appointment_List.csv"
        );

        system.start();
    }
}