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

    // TODO: make a while loop
    public void start() {
        menu = loginMenu.login();

        menu.showMenu();
    }
}
