package menus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SafeScanner {
    private Scanner sc;

    public SafeScanner(Scanner sc) {
        this.sc = sc;
    }

    public String next() {
        return sc.next();
    }

    public String nextLine() {
        return sc.nextLine();
    }

    public String promptLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    /**
     * Keeps prompting for a number between min and max (inclusive).
     * Also consumes the newline character.
     */
    public int promptInt(String prompt, int min, int max) {
        System.out.print(prompt);

        while (true) {
            try {
                int result = sc.nextInt();
                sc.nextLine();
                if (result < min || result > max) {
                    System.out.printf("Please enter a valid number from %d to %d.\n", min, max);
                    continue;
                }

                return result;
            } catch (InputMismatchException e) {
                System.out.printf("Please enter a valid number from %d to %d.\n", min, max);
                //sc.nextLine();// Without this line, entering a letter will give infinite loop
            }
        }
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public void close() {
        this.sc.close();
    }
}
