import java.io.IOException;

import data.appointment.AppointmentDatabase;
import data.user.UserDatabase;
import medicine.Inventory;
import menus.UserInterface;
/**
 * Represents the core system for managing hospital operations, including user management, inventory control,
 * and appointment scheduling.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class HospitalManagementSystem {
    private Inventory inventory;
    private UserDatabase userDb;
    private AppointmentDatabase appointmentDb;
    private UserInterface ui;

    /**
     * Constructs a new HospitalManagementSystem instance and initializes the various system components.
     * It loads data from the provided file paths for users, inventory, appointments, and accounts.
     * 
     * The system will initialize the following:
     * - User database with patient, staff, and account data.
     * - Inventory management system with medicines and replenishment requests.
     * - Appointment database with appointment records.
     *
     * @param staffListPath The file path for the staff list CSV file.
     * @param patientListPath The file path for the patient list CSV file.
     * @param medicineListPath The file path for the medicine inventory list CSV file.
     * @param requestListPath The file path for the medicine replenishment request list CSV file.
     * @param accountListPath The file path for the user account list CSV file.
     * @param appointmentListPath The file path for the appointment list CSV file.
     */
    public HospitalManagementSystem(
        String staffListPath,
        String patientListPath,
        String medicineListPath,
        String requestListPath,
        String accountListPath,
        String appointmentListPath
    ) {
        try {
            userDb = new UserDatabase(accountListPath, patientListPath, staffListPath);
            inventory = new Inventory(medicineListPath, requestListPath);
            appointmentDb = new AppointmentDatabase(appointmentListPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        userDb.initUsers(inventory, appointmentDb);

        ui = new UserInterface(userDb);
    }

    /**
     * Starts the hospital management system, initiating the user interface.
     * This method begins the process of user interaction with the system, allowing users to perform various actions
     * depending on their role (patient, staff, administrator, etc.).
     */
    public void start() {
        ui.start();
    }
}
