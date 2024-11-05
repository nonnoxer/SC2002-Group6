class Main {
    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem("Staff_List.csv", "Patient_List.csv", "Medicine_List.csv", "Account_List.csv");

        system.start();
    }
}