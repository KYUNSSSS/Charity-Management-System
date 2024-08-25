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
    
    public static boolean isValidQuantity(String input) {
        try {
            int quantity = Integer.parseInt(input.trim());
            return quantity > 0; 
        } catch (NumberFormatException e) {
            return false; 
        }
    }
    
    public static LocalDate isValidDate(String input, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(input, formatter); 
        } catch (DateTimeParseException e) {
            return null; 
        }
    }
    
}
