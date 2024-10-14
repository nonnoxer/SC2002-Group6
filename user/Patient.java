package user;

import data.CsvCompatible;

public class Patient extends User implements CsvCompatible {
    private String gender, birthDate, bloodType, contactInfo;

    public Patient(String id, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        super(id, bloodType, name, "Patient");
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", id, name, birthDate, gender, bloodType, contactInfo);
    }
}
