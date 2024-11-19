package menus;

import user.Administrator;
import user.Role;
import user.UserId;

public class AdministratorMenu extends Menu{
    private SafeScanner sc;
    private Administrator administrator;

    public AdministratorMenu(SafeScanner sc, Administrator administrator) {
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

            choice = sc.promptInt("Enter your choice: ", 1, 5);
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
            System.out.println("===== Manage Hospital Staff =====");
            System.out.println("1. View Staff List");
            System.out.println("2. Add Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. Remove Staff");
            System.out.println("5. Back to Main Menu");
            int choice = sc.promptInt("Enter your choice: ", 1, 5);

            switch(choice) {
                //View Staff List
                case 1:
                    System.out.println();
                    System.out.println("===== Staff List =====");
                    administrator.displayStaffList();
                    break;
                //Add a new staff member
                case 2:
                    String id = sc.promptLine("Enter Staff ID: ");
                    String name = sc.promptLine("Enter Staff Name: ");
                    String role = sc.promptLine("Enter Staff Role: ");
                    String gender = sc.promptLine("Enter Staff Gender:");
                    int age = sc.promptInt("Enter staff age: ", 0, 200);
                    administrator.addStaff(new UserId(id), name, Role.valueOf(role), gender, age);
                    break;
                //Update a staff member
                case 3:
                    String oldId = sc.promptLine("Enter Staff ID to update: ");
                    // Staff oldStaff = findStaffById(oldId);
                    String newName = sc.promptLine("Enter Staff Name: ");
                    String newRole = sc.promptLine("Enter Staff Role: ");
                    String newGender = sc.promptLine("Enter Staff Gender:");
                    int newAge = sc.promptInt("Enter staff age: ", 0, 200);
                    sc.nextLine(); // Consume newline
                    // Staff newStaff = new Staff(oldId, newName, newRole, newGender, newAge);
                    administrator.updateStaff(new UserId(oldId), newName, Role.valueOf(newRole), newGender, newAge);
                    break;
                case 4: // Remove a staff member
                    String removeId = sc.promptLine("Enter Staff ID to remove: ");
                    administrator.removeStaff(new UserId(removeId));
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
}



