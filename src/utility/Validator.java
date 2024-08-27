/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author SCSM11
 */
public class Validator {

    public static boolean isValidID(String id) {
        return id.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$");
    }

    public static boolean isAlphabetic(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    public static LocalDate isValidDate(String input, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isValidPositiveInteger(String input) {
        try {
            int number = Integer.parseInt(input.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidInteger(String input) {
        try {
            int number = Integer.parseInt(input.trim());
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidAmount(String input) {
        try {
            double value = Double.parseDouble(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String input) {
        // Remove any leading or trailing whitespace from the input
        String trimmedInput = input.trim();

        // Check if the input consists only of digits and has a length of 9 or 10
        return trimmedInput.matches("\\d{9,10}");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
