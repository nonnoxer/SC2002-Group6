package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import data.CsvCompatible;
import record.MedicalRecord;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;
    private MedicalRecord record;
    private ArrayList<Appointment> appointments;
    private ArrayList<DoctorApi> doctorApis;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, "placeholder", name, "Patient");
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;

        this.record = new MedicalRecord(id, name, birthDate, gender, contactInfo, bloodType);
        this.appointments = new ArrayList<Appointment>();
        this.doctorApis = null;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }

    public MedicalRecord getMedicalRecord() {
        return this.record;
    }

    public ArrayList<DoctorApi> getDoctors() {
        return this.doctorApis;
    }

    public ArrayList<Appointment> getAppointments() {
        return this.appointments;
    }

    public void scheduleAppointment(String doctorId, AppointmentSlot slot) {
        appointments.add(new Appointment(this.id, doctorId, slot));
    }

    public void rescheduleAppointment(int index, AppointmentSlot slot) {
        appointments.get(index).patientReschedule(slot);
    }

    public void cancelAppointment(int index) {
        appointments.get(index).patientCancel();
        appointments.remove(index);
    }

    public void setDoctors(ArrayList<DoctorApi> doctorApis) {
        this.doctorApis = doctorApis;
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
