package record;

import java.util.ArrayList;

public class MedicalRecord {
    private String id, name, birthDate, gender, contactInfo, bloodType;
    private ArrayList<AppointmentOutcomeRecord> pastAppointments;

    public MedicalRecord(String id, String name, String birthDate, String gender, String contactInfo, String bloodType) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;

        this.pastAppointments = new ArrayList<AppointmentOutcomeRecord>();
    }

    public String getId() {
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
