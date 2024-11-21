package record;

import java.util.ArrayList;

import appointment.Appointment;
import user.UserId;

public class MedicalRecord{
    private UserId id;
    private String name, birthDate, gender, contactInfo, bloodType;
    private ArrayList<Appointment> pastAppointments;

    public MedicalRecord(UserId id, String name, String birthDate, String gender, String contactInfo, String bloodType, ArrayList<Appointment> pastAppointments) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;

        this.pastAppointments = pastAppointments;
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public ArrayList<AppointmentOutcomeRecord> getPastAppointments() {
        ArrayList<AppointmentOutcomeRecord> allRecords = new ArrayList<>();
        
        for (Appointment appointment : pastAppointments) {
            allRecords.add(appointment.getRecord()); 
        }

        return allRecords;
    }
    

    // public void setId(String id){
    //     this.id = id;
    // }
    
    // public void setName(String name){
    //     this.name = name;
    // }
    
    // public void setBirthDate(String birthDate) {
    //     this.birthDate = birthDate;
    // }
    
    // public void setGender(String gender){
    //     this.gender = gender;
    // }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;

    }


    // public void setBloodType(String bloodType){
    //     this.bloodType = bloodType;
    // }
}
