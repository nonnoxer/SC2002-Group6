package record;

import java.util.ArrayList;

import user.UserId;
import data.CsvCompatible;

public class MedicalRecord{
    private UserId id;
    private String name, birthDate, gender, contactInfo, bloodType;
    private ArrayList<AppointmentOutcomeRecord> pastAppointments;

    public MedicalRecord(UserId id, String name, String birthDate, String gender, String contactInfo, String bloodType) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;

        this.pastAppointments = new ArrayList<AppointmentOutcomeRecord>();
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
        return pastAppointments;
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

    public void addPastAppointments(AppointmentOutcomeRecord newAppointment){
        this.pastAppointments.add(newAppointment);
    }
}
