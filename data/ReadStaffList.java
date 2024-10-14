package data;

import java.io.IOException;

import user.Administrator;
import user.Doctor;
import user.Pharmacist;
import user.Staff;

public class ReadStaffList {
    public static Staff[] readStaffListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        Staff[] staffs = new Staff[values.length];
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 5) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 5 elements.");
            }
            String id = line[0], name = line[1], role = line[2], gender = line[3];
            int age;
            try {
                age = Integer.parseInt(line[4]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid line: expected " + line[4] + " to be an integer.");
            }

            Staff staff;
            switch (role) {
                case "Doctor":
                    staff = new Doctor(id, name, role, gender, age);
                    break;
                case "Pharmacist":
                    staff = new Pharmacist(id, name, role, gender, age);
                    break;
                case "Administrator":
                    staff = new Administrator(id, name, role, gender, age);
                    break;
                default:
                    throw new IOException("Invalid line: expected " + line[2] + " to be one of 'Doctor', 'Pharmacist', 'Administrator'.");
            }

            staffs[i] = staff;
        }

        return staffs;
    }
}
