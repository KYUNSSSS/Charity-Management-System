/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import entity.Distribution;
import java.util.Scanner;
import java.util.InputMismatchException;
import utility.Validator;

/**
 *
 * @author SCSM11
 */
public class DonationDistributionUI {
    Scanner scanner = new Scanner(System.in);
    
    public int getMenuChoice() {
        System.out.println("\n********** DONATION DISTRIBUTION **********");
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
    
    public String inputDistributionID() {
        while (true) {
            System.out.print("Enter Distribution ID: ");
            String id = scanner.nextLine();

            if (Validator.isValidID(id)) {
                return id;
            }
            System.out.println("\nInvalid Input. Please enter a valid Distribution ID without symbols. [Eg. A001].");
        }
    }

    public String inputItemName(){
        while (true) {
            System.out.print("Enter Item Name: ");
            String itemName = scanner.nextLine();

            if (Validator.isAlphabetic(itemName)) {
                return itemName;
            }
            System.out.println("\nInvalid Input. Please enter a valid Item Name containing only letters and spaces.");
        }
    }
    
    public String inputDonationCategories(){
        while (true) {
            System.out.print("Enter Item Category: ");
            String category = scanner.nextLine();

            if (Validator.isAlphabetic(category)) {
                return category;
            }
            System.out.println("\nInvalid Input. Please enter a valid Item Category containing only letters and spaces.");
        }
    }
    
    public int inputQuantity() {
         while (true) {
            System.out.print("Enter Item Quantity: ");
            String input = scanner.nextLine().trim();

            if (Validator.isValidQuantity(input)) {
                return Integer.parseInt(input); 
            }

            System.out.println("\nInvalid Input. Please enter a valid number greater than zero.");
        }
    }
    
    public String inputDoneeID(){
        while (true) {
            System.out.print("Enter Donee ID: ");
            String doneeId = scanner.nextLine();

            if (Validator.isValidID(doneeId)) {
                return doneeId;
            }
            System.out.println("\nInvalid Input. Please enter a valid Distribution ID without symbols. [Eg. A001].");
        }
    }
   
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
            System.out.print("Enter Distribution Date (YYYY-MM-DD): ");
            String distributeDate = scanner.nextLine().trim();

            date = Validator.isValidDate(distributeDate, formatter);

            if (date == null) {
                System.out.println("\nInvalid Date Format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }
    
    public static String formatHeader(String title) {
        StringBuilder header = new StringBuilder();
        header.append(title).append("\n");
        header.append("========================================================================================================\n");
        header.append(String.format("%-20s%-15s%-15s%-15s%-20s%-20s\n", "Item", "Category", "Quantity", "Amount", "Status", "Distribution Date"));
        header.append("========================================================================================================\n");
        return header.toString();
    }
    
    public Distribution inputDistributionDetails() {
        String id = inputDistributionID();
        String itemName = inputItemName();
        String category = inputDonationCategories();

        // Declare variables for quantity and amount
        int quantity = 0;
        double amount = 0.0;
        
        if (category.equalsIgnoreCase("cash")) {
            amount = inputAmount(); 
            quantity = 0; 
        } else {
            quantity = inputQuantity(); 
            amount = 0; 
        }

        String doneeID = inputDoneeID();
        String status = inputStatus();
        LocalDate distributionDate = inputDistributionDate();

        System.out.println();
        return new Distribution(id, itemName, category, quantity, amount, doneeID, status, distributionDate); 
    }

    public double inputAmount() {
        double amount = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter Amount: ");
            try {
                amount = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                if (amount > 0) { // Ensure amount is a positive number
                    isValid = true;
                } else {
                    System.out.println("\nInvalid Input. Please enter a valid amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input. Please enter a valid amount.");
                scanner.next(); // Clear the invalid input
            }
        }
        return amount;
    }

    public void displaySummaryReport(int totalPendingItems, int totalDeliveredItems, int totalReceivedItems, double totalPendingCash, double totalDeliveredCash, double totalReceivedCash, double totalReceivedAmount, int totalItemsDistributed, int highestQuantity, String highestCategory) {
        StringBuilder report = new StringBuilder();
        report.append("============= Distribution Summary Report =============\n");
        report.append("Total Pending Distribution Items: ").append(totalPendingItems).append("\n");
        report.append("Total Delivered Distribution Items: ").append(totalDeliveredItems).append("\n");
        report.append("Total Received Distribution Items: ").append(totalReceivedItems).append("\n");
        report.append("\nTotal Cash Pending: RM ").append(String.format("%.2f", totalPendingCash)).append("\n");
        report.append("Total Cash Delivered: RM ").append(String.format("%.2f", totalDeliveredCash)).append("\n");
        report.append("Total Cash Received: RM ").append(String.format("%.2f", totalReceivedCash)).append("\n");
        report.append("\nTotal Cash Distributed: RM ").append(String.format("%.2f", totalReceivedAmount)).append("\n");
        report.append("Total Items Distributed: ").append(totalItemsDistributed).append("\n");
        report.append("Category with Highest Quantity: ").append(highestCategory).append(" (").append(highestQuantity).append(" items)").append("\n");
        report.append("=======================================================\n");
        System.out.println(report.toString());
    }
}





