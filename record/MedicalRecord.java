package record;

import java.util.ArrayList;

import appointment.Appointment;
import user.UserId;

/**
 * The MedicalRecord class represents a patient's medical record, including their personal information, 
 * contact details, blood type, and a list of past appointments. It provides methods to access and modify 
 * the patient's contact information and retrieve the patient's past appointment records.
 * 
 * @author FONG JIAN YUAN
 * @version 1.0
 * @since 2024-11-21
 */
public class MedicalRecord{
    private UserId id;
    private String name, birthDate, gender, contactInfo, bloodType;
    private ArrayList<Appointment> pastAppointments;

    /**
     * Constructs a new MedicalRecord with the given patient details.
     * 
     * @param id the unique identifier for the user
     * @param name the patient's full name
     * @param birthDate the patient's birth date (in string format)
     * @param gender the patient's gender
     * @param contactInfo the patient's contact information
     * @param bloodType the patient's blood type
     * @param pastAppointments a list of past appointments the patient has had
     */
    public MedicalRecord(UserId id, String name, String birthDate, String gender, String contactInfo, String bloodType, ArrayList<Appointment> pastAppointments) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;

        this.pastAppointments = pastAppointments;
    }

    /**
     * Gets the unique identifier for the patient.
     * 
     * @return the patient's unique ID
     */
    public UserId getId() {
        return id;
    }

    /**
     * Gets the patient's full name.
     * 
     * @return the patient's name
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the patient's birth date.
     * 
     * @return the patient's birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the patient's gender.
     * 
     * @return the patient's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the patient's contact information.
     * 
     * @return the patient's contact info
     */
    public String getContactInfo() {
        return contactInfo;
    }


    /**
     * Gets the patient's blood type.
     * 
     * @return the patient's blood type
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Retrieves a list of appointment outcome records for the patient's past appointments.
     * 
     * @return an ArrayList of AppointmentOutcomeRecord objects
     */
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

    /**
     * Updates the patient's contact information.
     * 
     * @param contactInfo the new contact information to set for the patient
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;

    }


    // public void setBloodType(String bloodType){
    //     this.bloodType = bloodType;
    // }
}
