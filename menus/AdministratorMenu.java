package menus;

import java.util.ArrayList;
import medicine.Medicine;
import medicine.ReplenishmentRequest;
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
        
        while (choice != 5) {
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
                return;
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
                    String name = sc.promptLine("Enter Staff Name: ");
                    String role = sc.promptLine("Enter Staff Role: ");
                    String gender = sc.promptLine("Enter Staff Gender:");
                    int age = sc.promptInt("Enter staff age: ", 0, 200);
                    administrator.addStaff(name, Role.valueOf(role), gender, age);
                    break;
                //Update a staff member
                case 3:
                    String oldId = sc.promptLine("Enter Staff ID to update: ");
                    // Staff oldStaff = findStaffById(oldId);
                    String newName = sc.promptLine("Enter Staff Name: ");
                    String newRole = sc.promptLine("Enter Staff Role: ");
                    String newGender = sc.promptLine("Enter Staff Gender:");
                    int newAge = sc.promptInt("Enter staff age: ", 0, 200);
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
        this.administrator.viewAppointments();
    }

    private void viewInventory() {
        while (true) { 
            System.out.println("===== Manage Medication Inventory =====");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Stock Level");
            System.out.println("3. Add New Medicine");
            System.out.println("4. Remove Medicine");
            System.out.println("5. Update Stock and Low Stock Alert Level");
            System.out.println("6. Back to Main Menu");
            int choice = sc.promptInt("Enter your choice: ", 1, 6);

            String medicineName;
            int stockNum = 0, lowStockAlert = 0;

            switch(choice) {
                //View Inventory
                case 1:
                    ArrayList<Medicine> medicines = administrator.getInventory();
                    if (medicines.isEmpty()) {
                        System.out.println("The inventory is currently empty.");
                        break;
                        }
                    System.out.println("Medication Inventory:");
                    for (Medicine medicine : medicines) {
                        System.out.printf("Medicine: %s, Stock: %d, Low Stock Alert: %d\n",
                            medicine.getName(), medicine.getStock(), medicine.getLowStockLevelAlert());
                        }
                        break;
                //Update Stock Level
                case 2:
                    medicineName = sc.promptLine("Enter the name of the medicine: ");
                    stockNum = sc.promptInt("Enter the new stock level: ", 0, Integer.MAX_VALUE);
                    administrator.updateInventoryStock(medicineName, stockNum);
                    break;
                //Add New Medicine
                case 3:
                    medicineName = sc.promptLine("Enter the name of the new medicine: ");
                    stockNum = sc.promptInt("Enter the initial stock level: ", 0, Integer.MAX_VALUE);
                    lowStockAlert = sc.promptInt("Enter the low stock alert level: ", 0, Integer.MAX_VALUE);
                    administrator.addNewMedicine(medicineName, stockNum, lowStockAlert);
                    break;
                //Remove Medicine
                case 4:
                    medicineName = sc.promptLine("Enter the name of the medicine to remove: ");
                    administrator.removeMedicine(medicineName);
                    break;
                //Update Low Stock Alert Level
                case 5:
                    medicineName = sc.promptLine("Enter the name of the medicine: ");
                    stockNum = sc.promptInt("Enter the stock level: ", 0, Integer.MAX_VALUE);
                    lowStockAlert = sc.promptInt("Enter the new low stock alert level: ", 0, Integer.MAX_VALUE);
                    administrator.updateInventoryStock(medicineName, stockNum, lowStockAlert);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void approveRequests() {
        ArrayList<ReplenishmentRequest> requests = administrator.getPendingRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }

        System.out.println("Pending replenishment requests:");
        for (int i = 0; i < requests.size(); i++) {
            ReplenishmentRequest request = requests.get(i);
            System.out.printf("%d. %s: %d\n", i+1, request.getName(), request.getStock());
        }
        System.out.println("0. Cancel");
        int choice = sc.promptInt("Enter your choice: ", 0, requests.size());
        if (choice == 0) return;

        ReplenishmentRequest request = requests.get(choice-1);
        System.out.println("1. Approve");
        System.out.println("0. Reject");
        boolean approved = sc.promptInt("Enter your choice: ", 0, 1) == 1;
        this.administrator.approveRequest(request, approved);
    }
}