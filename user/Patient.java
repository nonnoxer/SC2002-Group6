package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import data.CsvCompatible;
import data.appointment.AppointmentDatabaseApiPatient;
import data.user.UserDatabaseApiPatient;
import record.MedicalRecord;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;
    private MedicalRecord record;
    private AppointmentDatabaseApiPatient appointmentDb;
    private UserDatabaseApiPatient userDb;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, "placeholder", name, "Patient");
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;

        this.record = new MedicalRecord(id, name, birthDate, gender, contactInfo, bloodType);
        this.appointmentDb = null;
        this.userDb = null;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }

    public MedicalRecord getMedicalRecord() {
        return this.record;
    }

    public void setUserDb(UserDatabaseApiPatient userDb) {
        this.userDb = userDb;
    }

    public ArrayList<DoctorApiPatient> getDoctors() {
        return this.userDb.getDoctors();
    }

    public DoctorApiPatient getDoctorById(String doctorId) {
        return this.userDb.findDoctorId(doctorId);
    }

    public void setAppointmentDb(AppointmentDatabaseApiPatient appointmentDb) {
        this.appointmentDb = appointmentDb;
    }

    public ArrayList<Appointment> getAppointments() {
        return this.appointmentDb.getPatientAppointments(this.id);
    }

    public void scheduleAppointment(String doctorId, AppointmentSlot slot) {
        this.appointmentDb.newAppointment(this.id, doctorId, slot);
    }

    public void rescheduleAppointment(int appointmentId, AppointmentSlot slot) {
        this.appointmentDb.rescheduleAppointment(this.id, appointmentId, slot);
    }

    public void cancelAppointment(int appointmentId) {
        this.appointmentDb.cancelAppointment(this.id, appointmentId);
    }

    // Move getters and setters to MedicalRecord

    // // Getters
    // public String getGender() {
    //     return gender;
    // }

    // public String getBirthDate() {
    //     return birthDate;
    // }

    // public String getBloodType() {
    //     return bloodType;
    // }

    // public String getContactInfo() {
    //     return contactInfo;
    // }

    // // Setters
    // public void setGender(String gender) {
    //     this.gender = gender;
    // }

    // public void setBirthDate(String birthDate) {
    //     this.birthDate = birthDate;
    // }

    // public void setBloodType(String bloodType) {
    //     this.bloodType = bloodType;
    // }

    // public void setContactInfo(String contactInfo) {
    //     this.contactInfo = contactInfo;
    // }
}
