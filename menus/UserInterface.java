package menus;

import java.util.Scanner;

import data.user.UserDatabase;

/**
 * This UserInterface class handles interactions between the user and the system.
 * The interface provides the main interface for logging in or to register as a new patient user
 * in the Hospital Management System (HMS). The interface displays the options and
 * manages the interaction and flow between the login and registration menus.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class UserInterface {
    private LoginMenu loginMenu;
    private RegisterMenu registerMenu;
    private Menu menu;
    static final SafeScanner scanner = new SafeScanner(new Scanner(System.in));

    /**
     * Constructs a new UserInterface with specified UserDatabase.
     * It initialises the login and registration menus.
     *
     * @param db the UserDatabase to be used by the login and registration menus
     */
    public UserInterface(UserDatabase db) {
        this.loginMenu = new LoginMenu(scanner, db);
        this.registerMenu = new RegisterMenu(scanner, db);
    }

    /**
     * This is the starting for the main user interface of the Hospital Management System.
     * The method displays options to the user and prompts them to either log in or
     * register as a new patient, or quit the program. The menu (login or registration)
     * is displayed based on the user's choice, and the flow continues until the user chooses to quit.
     * Choosing 0 will terminate the program
     */
    public void start() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Login");
            System.out.println("2. Register as a new patient");
            System.out.println("0. Quit");
            choice = scanner.promptInt("Enter your choice: ", 0, 2);

            switch (choice) {
                case 1:
                    menu = loginMenu.login();
                    if (menu != null) menu.showMenu();
                    break;
                case 2:
                    menu = registerMenu.register();
                    if (menu != null) menu.showMenu();
                    break;
                default:
                    return;
            }   
        }     
    }
}
