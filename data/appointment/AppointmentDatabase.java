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

/**
 * Manages the database of appointments for patients, doctors, pharmacists, and administrators.
 * This class allows for CRUD operations on appointments, including creating, rescheduling, 
 * canceling, accepting, completing, and dispensing prescriptions.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class AppointmentDatabase implements AppointmentDatabaseApiPatient, AppointmentDatabaseApiDoctor, AppointmentDatabaseApiPharmacist, AppointmentDatabaseApiAdministrator{
    private HashMap<Integer, Appointment> appointments;
    private int id;
    private String path;

    /**
     * Constructs an AppointmentDatabase and initializes it from a file.
     * If the file does not exist, it creates a new file and initializes an empty database.
     *
     * @param path the file path for storing the appointment data
     * @throws IOException if there is an error reading or writing the file
     */
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

    /**
     * Updates the appointment database file with the current appointments.
     */
    private void update() {
        WriteFile.writeFile(appointments.values(), path);
    }

    /**
     * Returns the entire collection of appointments in the database.
     *
     * @return a HashMap containing all appointments, keyed by their ID
     */
    public HashMap<Integer, Appointment> getAppointments() {
        return this.appointments;
    }

    /**
     * Creates a new appointment and adds it to the database.
     *
     * @param patientId the patient ID associated with the appointment
     * @param doctorId the doctor ID associated with the appointment
     * @param slot the appointment slot for the appointment
     * @return the created Appointment object
     */
    public Appointment newAppointment(UserId patientId, UserId doctorId, AppointmentSlot slot) {
        Appointment appointment = new Appointment(id, patientId, doctorId, slot);
        this.appointments.put(id, appointment);
        update();
        id++;
        return appointment;
    }

    /**
     * Reschedules an appointment for a patient.
     * Only the patient who made the appointment can reschedule it.
     *
     * @param patientId the patient ID requesting the reschedule
     * @param id the appointment ID to reschedule
     * @param slot the new appointment slot
     * @return the rescheduled Appointment object, or null if the appointment could not be rescheduled
     */
    public Appointment rescheduleAppointment(UserId patientId, int id, AppointmentSlot slot) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getPatientId().equals(patientId)) return null;
        appointment.patientReschedule(slot);
        update();
        return appointment;
    }

    /**
     * Cancels an appointment for a patient.
     * Only the patient who made the appointment can cancel it.
     *
     * @param patientId the patient ID requesting the cancellation
     * @param id the appointment ID to cancel
     * @return the canceled Appointment object, or null if the appointment could not be canceled
     */
    public Appointment cancelAppointment(UserId patientId, int id) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getPatientId().equals(patientId)) return null;
        appointment.patientCancel();
        // this.appointments.remove(id);
        update();
        return appointment;
    }

    /**
     * Accepts or declines an appointment by a doctor.
     *
     * @param doctorId the doctor ID accepting or declining the appointment
     * @param id the appointment ID to accept or decline
     * @param accepted true if the doctor accepts the appointment, false if they decline it
     * @return the updated Appointment object, or null if the appointment could not be accepted or declined
     */
    public Appointment acceptAppointment(UserId doctorId, int id, boolean accepted) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getDoctorId().equals(doctorId)) return null;
        appointment.doctorAccept(accepted);
        update();
        return appointment;
    }

    /**
     * Sets the outcome of an appointment, including medical records and treatments.
     *
     * @param doctorId the doctor ID who is setting the outcome
     * @param id the appointment ID to set the outcome for
     * @param record the AppointmentOutcomeRecord containing the outcome details
     * @return the updated Appointment object, or null if the outcome could not be set
     */
    public Appointment setOutcome(UserId doctorId, int id, AppointmentOutcomeRecord record) {
        Appointment appointment = this.appointments.get(id);
        if (appointment == null) return null;
        if (!appointment.getDoctorId().equals(doctorId)) return null;
        appointment.complete(record);
        update();
        return appointment;
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the appointment ID to retrieve
     * @return the Appointment object with the specified ID, or null if not found
     */
    public Appointment getAppointment(int id) {
        return this.appointments.get(id);
    }

    /**
     * Retrieves all appointments for a specific patient.
     *
     * @param patientId the patient ID to retrieve appointments for
     * @return a list of Appointment objects for the specified patient
     */
    public ArrayList<Appointment> getPatientAppointments(UserId patientId) {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatientId().equals(patientId)) {
                result.add(appointment);
            }
        }
        return result;
    }


    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param doctorId the doctor ID to retrieve appointments for
     * @return a list of Appointment objects for the specified doctor
     */
    public ArrayList<Appointment> getDoctorAppointments(UserId doctorId) {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDoctorId().equals(doctorId)) {
                result.add(appointment);
            }
        }
        return result;
    }


    /**
     * Retrieves all appointment outcome records.
     *
     * @return a list of AppointmentOutcomeRecord objects
     */
    public ArrayList<AppointmentOutcomeRecord> getRecords() {
        ArrayList<AppointmentOutcomeRecord> records = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {
            AppointmentOutcomeRecord record = appointment.getRecord();
            if (record == null) continue;
            records.add(record);
        }

        return records;
    }

    /**
     * Dispenses a prescription for the specified appointment.
     *
     * @param id the appointment ID for which the prescription is to be dispensed
     * @return the AppointmentOutcomeRecord associated with the appointment, or null if the prescription cannot be dispensed
     */
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
