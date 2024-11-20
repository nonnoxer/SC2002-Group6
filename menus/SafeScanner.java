package menus;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
                sc.nextLine(); // Without this line, entering a letter will give infinite loop
            }
        }
    }

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

    public Scanner getScanner() {
        return this.sc;
    }

    public void close() {
        this.sc.close();
    }
}
