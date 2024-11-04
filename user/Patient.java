package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import data.CsvCompatible;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;
    private ArrayList<Appointment> appointments;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, "placeholder", name, "Patient");
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;

        this.appointments = new ArrayList<Appointment>();
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }

    public void viewMedicalRecord() {
        System.out.println("Viewing Medical Record...");
        System.out.printf("bloodtype = %s\n", bloodType);
        System.out.println();
        System.out.println();
        System.out.println();
        
    }

    public void viewAvailableSlots() {

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

        // Getters
        public String getGender() {
            return gender;
        }
    
        public String getBirthDate() {
            return birthDate;
        }
    
        public String getBloodType() {
            return bloodType;
        }
    
        public String getContactInfo() {
            return contactInfo;
        }
    
        // Setters
        public void setGender(String gender) {
            this.gender = gender;
        }
    
        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }
    
        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }
    
        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }
    }

