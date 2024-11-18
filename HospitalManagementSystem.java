import java.io.IOException;

import data.appointment.AppointmentDatabase;
import data.user.UserDatabase;
import medicine.Inventory;
import menus.UserInterface;

public class HospitalManagementSystem {
    private Inventory inventory;
    private UserDatabase userDb;
    private AppointmentDatabase appointmentDb;
    private UserInterface ui;

    public HospitalManagementSystem(String staffListPath, String patientListPath, String medicineListPath, String accountListPath, String appointmentListPath) {
        try {
            userDb = new UserDatabase(accountListPath, patientListPath, staffListPath);
            inventory = new Inventory(medicineListPath);
            appointmentDb = new AppointmentDatabase(appointmentListPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userDb.initUsers(inventory, appointmentDb);

        ui = new UserInterface(userDb);
    }

    public void start() {
        ui.start();
    }
}
