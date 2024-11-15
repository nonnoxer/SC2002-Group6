package menus;

import java.util.Scanner;
import user.Administrator;
import user.Staff;

public class AdministratorMenu extends Menu{
    private Scanner sc;
    private Administrator administrator;

    public AdministratorMenu(Scanner sc, Administrator administrator) {
        this.sc = sc;
        this.administrator = administrator;
    }

    public void showMenu(){

        int choice = -1;
        
        while (choice != 8) {
            System.out.println("===== Administrator Menu =====");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();
            handleSelection(choice);
        }
    }

    private void handleSelection(int choice){
        switch (choice) {
            case 1:
                viewStaff();
                break;
            case 2:
                viewAppointments();
                break;
            case 3:
                viewInventory();
                break;
            case 4:
                approveRequests();
                break;
            case 5:
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }

    private void viewStaff() {
        //Keep looping until user decide to go back to administrator menu
        while(true) {
            System.out.println("===== Manage Hospoital Staff =====");
            System.out.println("1. View Staff List");
            System.out.println("2. Add Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. Remove Staff");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            //Consume newline
            sc.nextLine();

            switch(choice) {
                //View Staff List
                case 1:
                    System.out.println();
                    System.out.println("===== Staff List =====");
                    administrator.displayStaffList();
                    break;
                //Add a new staff member
                case 2:
                    System.out.print("Enter Staff ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Staff Name:");
                    String name = sc.nextLine();
                    System.out.print("Enter Staff Role:");
                    String role = sc.nextLine();
                    System.out.print("Enter Staff Gender:");
                    String gender = sc.nextLine();
                    System.out.print("Enter Staff age:");
                    int age = sc.nextInt();
                    //Consume newline
                    sc.nextLine();
                    administrator.addStaff(new Staff(id, name, role, gender, age));
                    break;
                //Update a staff member
                case 3:
                    System.out.print("Enter Staff ID to update: ");
                    String oldId = sc.nextLine();
                    Staff oldStaff = findStaffById(oldId);
                    if (oldStaff != null) {
                        System.out.print("Enter New Staff Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Staff Role: ");
                        String newRole = sc.nextLine();
                        System.out.print("Enter New Staff Gender: ");
                        String newGender = sc.nextLine();
                        System.out.print("Enter New Staff Age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        Staff newStaff = new Staff(oldId, newName, newRole, newGender, newAge);
                        administrator.updateStaff(oldStaff, newStaff);
                    } else {
                        System.out.println("Staff not found.");
                    }
                    break;
                case 4: // Remove a staff member
                    System.out.print("Enter Staff ID to remove: ");
                    String removeId = sc.nextLine();
                    Staff staffToRemove = findStaffById(removeId); // Helper method
                    if (staffToRemove != null) {
                        administrator.removeStaff(staffToRemove);
                    } else {
                        System.out.println("Staff not found.");
                    }
                    break;
                case 5: // Exit menu
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void viewAppointments() {

    }

    private void viewInventory() {

    }

    private void approveRequests() {

    }

    //Method to locate a staff member by ID
    private Staff findStaffById(String id) {
        for (Staff staff : administrator.getStaffList()) {
            if(staff.getID().equals(id)) {
                return staff;
            }
        }
        return null;
    }
}



