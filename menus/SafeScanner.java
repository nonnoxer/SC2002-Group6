package menus;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The SafeScanner class is a wrapper around the {@link Scanner} class that provides enhanced functionality
 * for safe and user-friendly input handling, with error checking and input validation.
 * It is designed to prompt the user for input with custom messages and ensures that inputs are within valid ranges or match specified formats.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class SafeScanner {
    private Scanner sc;

    /**
     * Constructs a new SafeScanner using the provided Scanner instance.
     *
     * @param sc the Scanner instance to be wrapped by SafeScanner
     */
    public SafeScanner(Scanner sc) {
        this.sc = sc;
    }

    /**
     * Reads the next token from the input as a string.
     *
     * @return the next token in the input as a string
     */
    public String next() {
        return sc.next();
    }

    /**
     * Reads the next line of input as a string.
     *
     * @return the next line of input as a string
     */
    public String nextLine() {
        return sc.nextLine();
    }

    /**
     * Prompts the user for a line of input with the given message.
     *
     * @param prompt the message to display to the user
     * @return the line of input entered by the user
     */
    public String promptLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    /**
     * Prompts the user to enter an integer within a specified range (inclusive).
     * The user will be repeatedly prompted until they enter a valid number within the range.
     *
     * @param prompt the message to display to the user
     * @param min the minimum acceptable value for the integer
     * @param max the maximum acceptable value for the integer
     * @return a valid integer entered by the user within the specified range
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
                sc.nextLine(); // Without this line, entering a letter will give infinite loop
            }
        }
    }

    /**
     * Prompts the user to enter a range or a list of integers. The input can be in the form of:
     * - A range (e.g., "1-5")
     * - A comma-separated list of integers (e.g., "1, 3, 5")
     * The numbers must be within the specified range and are returned as a list of integers.
     *
     * @param prompt the message to display to the user
     * @param min the minimum acceptable value for the integers
     * @param max the maximum acceptable value for the integers
     * @return a list of integers entered by the user, within the specified range
     */
    public List<Integer> promptRange(String prompt, int min, int max) {
        while (true){
            String input = promptLine(prompt);
            List<Integer> result = new ArrayList<>();
            
            if (input.contains("-")) {
                String[] parts = input.split("-");
                int start = Integer.parseInt(parts[0].trim());
                int end = Integer.parseInt(parts[1].trim());
                if (start < min || end > max) {
                    System.out.printf("Please enter a valid number from %d-%d.\n", min, max);
                    continue;
                }

                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else if (input.contains(",")) {
                String[] parts = input.split(",");
                for (String part : parts) {
                    int num = Integer.parseInt(part.trim());
                    if (num >= min && num <= max) {
                        result.add(num);
                    }else{
                        System.out.printf("%d not in range from &d-&d", num, min, max);
                        continue;
                    }
                }
            } else {
                int num = Integer.parseInt(input.trim());
                if (num >= min && num <= max) {
                    result.add(num);
                }
            }
            return result;
        }
    }

    /**
     * Returns the underlying Scanner instance used by this SafeScanner.
     *
     * @return the Scanner instance used by this SafeScanner
     */
    public Scanner getScanner() {
        return this.sc;
    }

    /**
     * Closes the Scanner used by this SafeScanner.
     * After this method is called, the SafeScanner cannot be used to read input anymore.
     */
    public void close() {
        this.sc.close();
    }
}
