//auth: amu
package menus;

import java.util.Scanner;
import user.Patient;
import user.User;
import record.MedicalRecord;


public class PatientMenu extends Menu{
    private Scanner sc = new Scanner(System.in);

    //overiding, implemented from menu class
    @Override
    public void showMenu(User patient){
        int choice = -1;

        System.out.print("test patientmenu");
        boolean quit = false;
        
        while (!quit) { 
            System.out.println("===== Patient Menu =====");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.println("0. quit");
            System.out.print("Enter your choice: ");

            choice = Integer.parseInt(sc.nextLine()); 
            
            if(choice == 0){
                quit = true;
            }
            System.out.print("temporary");
            handleSelection(choice);
        }
    }

    private void handleSelection(int choice){
        switch (choice) {
            case 1://Vieww medical record
                //MedicalRecord()
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                
                break;
            case 6:
            
                break;
            case 7:
                
                break;
            case 8:
                
                break;
            case 9:
                
                
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }
}

