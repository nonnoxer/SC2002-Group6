package user;

import data.CsvCompatible;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, "placeholder", name, "Patient");
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }

    public void viewMedicalRecord() {
        System.out.println("Viewing Medical Record...");
        System.out.printf("bloodtype = %s\n", bloodType);
        System.out.println();
        System.out.println();
        System.out.println();
        
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

