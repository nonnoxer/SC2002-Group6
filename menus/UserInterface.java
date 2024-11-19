package menus;

import java.util.Scanner;

import data.user.UserDatabase;

public class UserInterface {
    private LoginMenu loginMenu;
    private Menu menu;
    static final SafeScanner scanner = new SafeScanner(new Scanner(System.in));

    public UserInterface(UserDatabase db) {
        this.loginMenu = new LoginMenu(scanner, db);
    }

    public void start() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Login");
            System.out.println("2. Quit");
            choice = scanner.promptInt("Enter your choice: ", 1, 2);

            switch (choice) {
                case 1:
                    menu = loginMenu.login();
                    if (menu != null) menu.showMenu();
                    break;
                case 2:
                    return;
            }   
        }     
    }
}
