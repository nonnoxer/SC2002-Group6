package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.AppointmentStatus;
import data.CsvCompatible;
import data.appointment.AppointmentDatabaseApiPatient;
import data.user.UserDatabaseApiPatient;
import record.MedicalRecord;

/**
 * Represents a Patient, extending the User class and implementing the CsvCompatible interface.
 * The Patient class handles personal details, medical records, appointments, and related functionality.
 * It interacts with the user and appointment databases to manage patient-specific operations.
 * 
 * @author AMUDHAN ANANDHARAMAN
 * @version 1.0
 * @since 2024-11-21
 */
public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;
    private MedicalRecord record;
    private AppointmentDatabaseApiPatient appointmentDb;
    private UserDatabaseApiPatient userDb;

    /**
     * Constructs a new Patient object.
     *
     * @param id The unique user ID of the patient.
     * @param name The name of the patient.
     * @param birthDate The birthdate of the patient.
     * @param gender The gender of the patient.
     * @param bloodType The blood type of the patient.
     * @param contactInfo The contact information of the patient.
     */
    public Patient(UserId id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, name, Role.Patient);
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;

        this.appointmentDb = null;
        this.userDb = null;
    }

    /**
     * Converts the patient's data to a CSV format string.
     *
     * @return A string in CSV format representing the patient's details.
     */
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }

    /**
     * Retrieves the medical record of the patient.
     *
     * @return The patient's medical record.
     */
    public MedicalRecord getMedicalRecord() {
        return this.record;
    }

    /**
     * Initializes the Patient object with the user database and appointment database.
     * This method is used to set up connections to the relevant data sources.
     *
     * @param userDb The UserDatabaseApiPatient instance to interact with user data.
     * @param appointmentDb The AppointmentDatabaseApiPatient instance to interact with appointments.
     */
    public void init(UserDatabaseApiPatient userDb, AppointmentDatabaseApiPatient appointmentDb) {
        this.userDb = userDb;
        this.appointmentDb = appointmentDb;
        this.record = new MedicalRecord(id, name, birthDate, gender, contactInfo, bloodType, appointmentDb.getPatientAppointments(id));
    }

    /**
     * Retrieves a list of doctors associated with the patient.
     *
     * @return An ArrayList of DoctorApiPatient objects.
     */
    public ArrayList<DoctorApiPatient> getDoctors() {
        return this.userDb.getDoctors();
    }

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param doctorId The unique ID of the doctor.
     * @return The DoctorApiPatient object representing the doctor, or null if not found.
     */
    public DoctorApiPatient getDoctorById(UserId doctorId) {
        return this.userDb.findDoctorId(doctorId);
    }

    /**
     * Retrieves all appointments associated with the patient.
     *
     * @return An ArrayList of Appointment objects.
     */
    public ArrayList<Appointment> getAllAppointments() {
        return this.appointmentDb.getPatientAppointments(this.id);
    }

    /**
     * Retrieves the scheduled appointments for the patient (pending or confirmed).
     *
     * @return An ArrayList of scheduled Appointment objects.
     */
    public ArrayList<Appointment> getScheduledAppointments() {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : this.appointmentDb.getPatientAppointments(this.id)) {
            AppointmentStatus status = appointment.getAppointmentStatus();
            if (status.equals(AppointmentStatus.Pending) || status.equals(AppointmentStatus.Confirmed)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Retrieves the completed appointments for the patient.
     *
     * @return An ArrayList of completed Appointment objects.
     */
    public ArrayList<Appointment> getCompletedAppointments() {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : this.appointmentDb.getPatientAppointments(this.id)) {
            AppointmentStatus status = appointment.getAppointmentStatus();
            if (status.equals(AppointmentStatus.Completed)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Schedules a new appointment for the patient with a specific doctor at a given slot.
     *
     * @param doctorId The unique ID of the doctor.
     * @param slot The appointment slot for the new appointment.
     */
    public void scheduleAppointment(UserId doctorId, AppointmentSlot slot) {
        this.appointmentDb.newAppointment(this.id, doctorId, slot);
    }

    /**
     * Reschedules an existing appointment for the patient.
     *
     * @param appointmentId The ID of the appointment to reschedule.
     * @param slot The new appointment slot.
     */
    public void rescheduleAppointment(int appointmentId, AppointmentSlot slot) {
        this.appointmentDb.rescheduleAppointment(this.id, appointmentId, slot);
    }

    /**
     * Cancels an existing appointment for the patient.
     *
     * @param appointmentId The ID of the appointment to cancel.
     */
    public void cancelAppointment(int appointmentId) {
        this.appointmentDb.cancelAppointment(this.id, appointmentId);
    }

    /**
     * Updates the contact information of the patient.
     * This also updates the contact information in the patient's medical record.
     *
     * @param contactInfo The new contact information.
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
        this.record.setContactInfo(contactInfo);
        this.userDb.updatePatient();
    }
}
