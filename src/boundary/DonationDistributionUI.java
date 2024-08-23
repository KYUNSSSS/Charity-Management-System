/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import entity.Distribution;
import java.util.Scanner;

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
    
    public void listAllDistribute(String outputStr) {
        System.out.println("\nList of Donation Distribution:\n" + outputStr);
    }
    
    public String inputDistributionID() {
        System.out.print("Enter Distribution ID: ");
        String id = scanner.nextLine();
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
    
    public int inputQuantity(){
        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        return quantity;
    }
    
    public String inputStatus() {
        System.out.print("Enter Distribution Status (Pending, Delivered, Received): ");
        String status = scanner.nextLine();
        return status;
    }
    
    public LocalDate inputDistributionDate(){
        System.out.print("Enter Distbution Date (YYYY-MM-DD): ");
        String distributeDate = scanner.nextLine();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(distributeDate, formatter);
    try {
        System.out.println("You entered: " + date);
    } catch (Exception e) {
        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
    }
        return date;
    }
    
    public String inputDoneeID(){
        System.out.print("Enter Donee ID: ");
        String doneeId = scanner.nextLine();
        return doneeId;
    }

   public Distribution inputDistributionDetails() {
       String id = inputDistributionID();
       String itemName = inputItemName();
       String category = inputDonationCategories();
       int quantity = inputQuantity();
       String status = inputStatus();
       LocalDate distributionDate = inputDistributionDate();
       String doneeID = inputDoneeID();
       
       System.out.println();
       return new Distribution(id, itemName, category, quantity, status, distributionDate, doneeID);
    }
}
