package JavaProject;

import java.util.Scanner;

public class PharmacistMenu {

	public static void showPharmacistMenu() {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		// Create a pharmacist object by using the default constructor
		Pharmacist p = new Pharmacist();
		
		 while (!exit) {
	            System.out.println("========== Pharmacist Menu ==========");
	            System.out.println("1. View Appointment Outcome Record");
	            System.out.println("2. Update Prescription Status");
	            System.out.println("3. View Medication Inventory");
	            System.out.println("4. Submit Replenishment Request");
	            System.out.println("5. Logout");
	            System.out.println("=====================================");
	            System.out.println("Please choose an option:");

	            String option = scanner.nextLine();
	            
	            switch(option) {
	            	case "1":
	            		// Call the method to view the prescription
	            		p.viewPrescription();
	            		break;
	            	
	            	case "2":
	            		// Call the method to update the prescription status
	            		p.updatePrescriptionStatus();
	            		break;
	            		
	            	case "3":
	            		// Call the method to view the medical inventory
	            		p.monitorInventory();
	            		break;
	            		
	            	case "4":
	            		// Call the method to submit a replenishment request;
	            		p.requestReplenishment();
	            		break;
	            		
	            	case "5":
	            		// Call to logout
	            		System.out.println("logging out...");
	            		exit = true;
	            		break;
	            	default: 
	            		System.out.println("The option is chosen incorrectly, please try again!");
	            }
	    }
		scanner.close();
	            
	}
	
}
