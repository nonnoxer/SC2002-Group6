package menus;

import java.util.Scanner;

import data.user.UserDatabase;

public class UserInterface {
    private LoginMenu loginMenu;
    private RegisterMenu registerMenu;
    private Menu menu;
    static final SafeScanner scanner = new SafeScanner(new Scanner(System.in));

    public UserInterface(UserDatabase db) {
        this.loginMenu = new LoginMenu(scanner, db);
        this.registerMenu = new RegisterMenu(scanner, db);
    }

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
