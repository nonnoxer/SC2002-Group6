package data.appointment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import appointment.Appointment;
import appointment.AppointmentSlot;
import data.ReadFile;
import data.WriteFile;
import record.AppointmentOutcomeRecord;
import user.UserId;

public class AppointmentDatabase implements AppointmentDatabaseApiPatient, AppointmentDatabaseApiDoctor, AppointmentDatabaseApiPharmacist, AppointmentDatabaseApiAdministrator{
    private HashMap<Integer, Appointment> appointments;
    private int id;
    private String path;

    public AppointmentDatabase(String path) throws IOException {
        this.path = path;
        id = 0;
        appointments = new HashMap<>();

        if (Files.notExists(Path.of(path))) {
            System.out.printf("%s does not exist, creating new database...\n\n", path);
            WriteFile.writeFile(appointments.values(), path);
        } else {
            ArrayList<Appointment> appointmentsList = ReadFile.readAppointmentListFile(path);
            for (Appointment appointment : appointmentsList) {
                appointments.put(appointment.getId(), appointment);
                if (appointment.getId() >= id) id = appointment.getId() + 1;
            }
        }
    }

    private void update() {
        WriteFile.writeFile(appointments.values(), path);
    }

    public HashMap<Integer, Appointment> getAppointments() {
        return this.appointments;
    }

    public Appointment newAppointment(UserId patientId, UserId doctorId, AppointmentSlot slot) {
        Appointment appointment = new Appointment(id, patientId, doctorId, slot);
        this.appointments.put(id, appointment);
        update();
        id++;
        return appointment;
    }

    public Appointment rescheduleAppointment(UserId patientId, int id, AppointmentSlot slot) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getPatientId().equals(patientId)) return null;
        appointment.patientReschedule(slot);
        update();
        return appointment;
    }

    public Appointment cancelAppointment(UserId patientId, int id) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getPatientId().equals(patientId)) return null;
        appointment.patientCancel();
        // this.appointments.remove(id);
        update();
        return appointment;
    }

    public Appointment acceptAppointment(UserId doctorId, int id, boolean accepted) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getDoctorId().equals(doctorId)) return null;
        appointment.doctorAccept(accepted);
        update();
        return appointment;
    }

    public Appointment setOutcome(UserId doctorId, int id, AppointmentOutcomeRecord record) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getDoctorId().equals(doctorId)) return null;
        appointment.complete(record);
        update();
        return appointment;
    }

    public Appointment getAppointment(int id) {
        return this.appointments.get(id);
    }

    public ArrayList<Appointment> getPatientAppointments(UserId patientId) {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatientId().equals(patientId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public ArrayList<Appointment> getDoctorAppointments(UserId doctorId) {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDoctorId().equals(doctorId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public ArrayList<AppointmentOutcomeRecord> getRecords() {
        ArrayList<AppointmentOutcomeRecord> records = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {
            AppointmentOutcomeRecord record = appointment.getRecord();
            if (record == null) continue;
            records.add(record);
        }

        return records;
    }

    public AppointmentOutcomeRecord dispensePrescription(int id) {
        Appointment appointment = appointments.get(id);
        if (appointment == null) return null;

        AppointmentOutcomeRecord record = appointment.getRecord();
        if (record == null) return null;

        record.dispensePrescription();

        update();
        return record;
    }
}
