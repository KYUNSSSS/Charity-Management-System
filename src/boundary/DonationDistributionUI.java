/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import entity.Distribution;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author SCSM11
 */
public class DonationDistributionUI {
    Scanner scanner = new Scanner(System.in);
    
    public int getMenuChoice() {
        System.out.println("\n****** DONATION DISTRIBUTION SYSTEM ******");
        System.out.println("1. Add New Donation Distribution");
        System.out.println("2. Update Donation Distribution Details");
        System.out.println("3. Remove Donation Distribution");
        System.out.println("4. Monitor Distributed Items");
        System.out.println("5. Generate Summary Report");
        System.out.println("0. Quit");
        System.out.print("Enter your choice (0-5): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println();
        return choice;
    }
    
    public int getTrackMenuChoice() {
        System.out.println("\n****** TRACK DISTRIBUTION ******");
        System.out.println("1. Track by Donee ID");
        System.out.println("2. Track by Distribution Date");
        System.out.println("3. Track by Category");
        System.out.println("4. Track by Status");
        System.out.println("5. Track by Location");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice (0-5): ");
        int choices = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println();
        return choices;
    }
    
    public void listAllDistribute(String outputStr) {
        System.out.println("\nList of Donation Distribution:\n" + outputStr);
    }
    
//    public String inputDistributionID() {
////        System.out.print("Enter Distribution ID: ");
////        String id = scanner.nextLine();
////        return id;
//    
//    }
  
    public String inputDistributionID() {
        String id = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter Distribution ID: ");
            id = scanner.nextLine();
            // Check if the input contains only alphanumeric characters
            if (id.matches("[a-zA-Z0-9]+")) {
                isValid = true; 
            } else {
                System.out.println("\nInvalid Input. Please enter a valid Distribution ID exclude symbols. [Eg. A001].");
            }
        }
        return id;
    }

    public String inputItemName(){
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        return itemName;
    }
    
    public String inputDonationCategories(){
        System.out.print("Enter Item Category: ");
        String category = scanner.nextLine();
        return category;
    }
    
//    public int inputQuantity(){
//        System.out.print("Enter Item quantity: ");
//        int quantity = scanner.nextInt();
//        scanner.nextLine();
//        return quantity;
//    }
    
    public int inputQuantity() {
        int quantity = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter Item quantity: ");
            try {
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (quantity > 0) { // Ensure quantity is a non-negative integer
                    isValid = true;
                } else {
                    System.out.println("\nInvalid Input. Please enter valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return quantity;
    }
    
    public String inputDoneeID(){
        System.out.print("Enter Donee ID: ");
        String doneeId = scanner.nextLine();
        return doneeId;
    }
    
//    public String inputStatus() {
//        System.out.print("Enter Distribution Status (Pending, Delivered, Received): ");
//        String status = scanner.nextLine();
//        return status;
//    }
    public String inputStatus() {
        String status;
        while (true) {
            System.out.print("Enter Distribution Status (Pending, Delivered, Received): ");
            status = scanner.nextLine().trim(); 

            if (status.equalsIgnoreCase("Pending") || 
                status.equalsIgnoreCase("Delivered") || 
                status.equalsIgnoreCase("Received")) {
                break;
            } else {
                System.out.println("\nInvalid Status. Please enter one of the following: Pending, Delivered, Received.");
            }
        }
        return status;
    }
    
    public LocalDate inputDistributionDate() {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (date == null) {
            try {
                System.out.print("Enter Distribution Date (YYYY-MM-DD): ");
                String distributeDate = scanner.nextLine();

                // Parse the input date string into a LocalDate object
                date = LocalDate.parse(distributeDate, formatter);
                System.out.println();

            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid Date Format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }
    
    public static String formatHeader(String title) {
        StringBuilder header = new StringBuilder();
        header.append(title).append("\n");
        header.append("===================================================================================================\n");
        header.append(String.format("%-20s%-15s%-15s%-20s%-20s\n", "Item", "Category", "Quantity", "Status", "Distribution Date"));
        header.append("===================================================================================================\n");
        return header.toString();
    }

    public Distribution inputDistributionDetails() {
       String id = inputDistributionID();
       String itemName = inputItemName();
       String category = inputDonationCategories();
       int quantity = inputQuantity();
       String doneeID = inputDoneeID();
       String status = inputStatus();
       LocalDate distributionDate = inputDistributionDate();
       
       System.out.println();
       return new Distribution(id, itemName, category, quantity, doneeID, status, distributionDate);
    }
   
    public void displaySummaryReport(int totalPending, int totalDelivered, int totalReceived, int highestQuantity, String highestStatus, String highestCategory) {
        StringBuilder report = new StringBuilder();

        report.append("============= Distribution Summary Report =============\n");
        report.append("Total Quantity of Pending Distributions: ").append(totalPending).append("\n");
        report.append("Total Quantity of Delivered Distributions: ").append(totalDelivered).append("\n");
        report.append("Total Quantity of Received Distributions: ").append(totalReceived).append("\n");
//        report.append("---------------------------------------------\n");
        report.append("\nHighest Quantity Status: ").append(highestStatus).append("\n");
        report.append("Category with Highest Quantity: ").append(highestCategory).append(" (").append(highestQuantity).append(" items)").append("\n");
        report.append("=======================================================\n");

        System.out.println(report.toString());
    }
}
