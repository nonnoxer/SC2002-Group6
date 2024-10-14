package user;

public class Patient extends User {
    private String birthDate, bloodType, contactInfo;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, bloodType, name, "Patient");
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }
}
