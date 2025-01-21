package util;

import entity.Category;
import entity.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Method to get String input
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Method to get int input
    public static Integer getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Method to get double input
    public static Double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Method to get long input
    public static Long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    public static LocalDate getLocalDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date in the format dd-MM-yyyy.");
            }
        }
    }

    public static Category getCategoryInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase(); // Convert to uppercase for case-insensitive comparison
            try {
                return Category.valueOf(input); // Try to convert the input to a Category enum
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid category. Available categories: " + String.join(", ", Category.HORROR.toString(), Category.SCIFI.toString(), Category.FANTASY.toString(),Category.HISTORY.toString(),Category.LITERATURE.toString(),Category.ACTION.toString(),Category.NOVEL.toString(),Category.COMICS.toString(),Category.SCIENCE.toString(), Category.HOBBY.toString(), Category.LEARNING.toString()));
            }
        }
    }

    public static Status getStatusInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase(); // Convert to uppercase for case-insensitive comparison
            try {
                return Status.valueOf(input); // Try to convert the input to a Category enum
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid status. Available statuses: " + String.join(", ", Status.AVAILABLE.toString(), Status.BORROWED.toString()));
            }
        }
    }
}
