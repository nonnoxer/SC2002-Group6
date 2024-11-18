package appointment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import data.ReadFile;
import data.WriteFile;

public class AppointmentDatabase {
    private ArrayList<Appointment> appointments;

    public AppointmentDatabase(String path) throws IOException {
        if (Files.notExists(Path.of(path))) {
            System.out.printf("%s does not exist, creating new database...\n\n", path);
            appointments = new ArrayList<Appointment>();
            WriteFile.writeFile(appointments, path);
        } else {
            appointments = ReadFile.readAppointmentListFile(path);
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return this.appointments;
    }
}
