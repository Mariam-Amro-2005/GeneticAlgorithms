package genetic.util;

import java.util.Scanner;
import java.util.function.Predicate;

public final class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtils() {}

    /**
     * Reads a double value with a default if the user presses Enter.
     */
    public static Double readDoubleOrDefault(String prompt, Double defaultValue) {
        System.out.printf("%s [default = %.3f]: ", prompt, defaultValue);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return defaultValue;

        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Using default value.");
            return defaultValue;
        }
    }

    /**
     * Reads an int value with a default if the user presses Enter.
     */
    public static Integer readIntOrDefault(String prompt, Integer defaultValue) {
        System.out.printf("%s [default = %d]: ", prompt, defaultValue);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return defaultValue;

        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer. Using default value.");
            return defaultValue;
        }
    }

    /**
     * Reads a menu selection between min and max inclusive, with a default option.
     */
    public static int readMenuChoice(String prompt, int min, int max, int defaultChoice) {
        System.out.printf("%s [%d-%d, default = %d]: ", prompt, min, max, defaultChoice);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return defaultChoice;

        try {
            int choice = Integer.parseInt(line);
            if (choice >= min && choice <= max)
                return choice;
        } catch (NumberFormatException ignored) {}

        System.out.println("Invalid choice. Using default option.");
        return defaultChoice;
    }

    /**
     * Reads a value validated by a predicate (optional).
     */
    public static <T> T readValue(String prompt, T defaultValue, java.util.function.Function<String, T> parser, Predicate<T> validator) {
        System.out.printf("%s [default = %s]: ", prompt, defaultValue);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return defaultValue;
        try {
            T value = parser.apply(line);
            if (validator.test(value)) return value;
        } catch (Exception ignored) {}
        System.out.println("Invalid input. Using default value.");
        return defaultValue;
    }
}
