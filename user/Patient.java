package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.AppointmentStatus;
import data.CsvCompatible;
import data.appointment.AppointmentDatabaseApiPatient;
import data.user.UserDatabase;
import data.user.UserDatabaseApiPatient;
import record.MedicalRecord;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;
    private MedicalRecord record;
    private AppointmentDatabaseApiPatient appointmentDb;
    private UserDatabaseApiPatient userDb;

    public Patient(UserId id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, name, Role.Patient);
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

    public void init(UserDatabaseApiPatient userDb, AppointmentDatabaseApiPatient appointmentDb) {
        this.userDb = userDb;
        this.appointmentDb = appointmentDb;
    }

    public ArrayList<DoctorApiPatient> getDoctors() {
        return this.userDb.getDoctors();
    }

    public DoctorApiPatient getDoctorById(UserId doctorId) {
        return this.userDb.findDoctorId(doctorId);
    }

    public ArrayList<Appointment> getAllAppointments() {
        return this.appointmentDb.getPatientAppointments(this.id);
    }

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

    public void scheduleAppointment(UserId doctorId, AppointmentSlot slot) {
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

     public void setContactInfo(String contactInfo) {
         this.contactInfo = contactInfo;
         this.record.setContactInfo(contactInfo);
         this.userDb.updatePatient();
     }
}
