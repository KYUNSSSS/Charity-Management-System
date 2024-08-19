/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author xuan
 */
public class DonorManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\n******DONOR MANAGEMENT******");
        System.out.println("1. Add a New Donor");
        System.out.println("2. Remove a Donor");
        System.out.println("3. Update Donors Details");
        System.out.println("4. Search Donors Details");
        System.out.println("5. List Donors with Donations Made");
        System.out.println("6. Filter Donor"); //based on criteria
        System.out.println("7. Categorise Donors"); // government, private, public
        System.out.println("8. Generate Summary Reports");
        System.out.println("0. Quit");
        System.out.print("Enter choice(0-8): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    public void listAllDonors(String outputStr) {
        System.out.println("\nList of Donor:\n" + outputStr);
    }

    public String inputDonorID() {
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        return id;
    }

    public String inputDonorType() { //TODO: change to menu form
        System.out.print("Enter Donor Type (Government / Private / Public): ");
        String type = scanner.nextLine();
        return type;
    }

    public String inputDonorName() {
        System.out.print("Enter Donor Name: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputDonorEmail() {
        System.out.print("Enter Donor Email: ");
        String email = scanner.nextLine();
        return email;
    }

    public int inputPhoneNum() {
        System.out.print("Enter Phone Number: ");
        int phone = scanner.nextInt();
        scanner.nextLine();
        return phone;
    }

    public double inputDonationAmount() {
        System.out.print("Enter Donation Amount: RM ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        return amount;
    }

//    public LocalDate inputDonationDate() {
//        System.out.print("Enter the date (YYYY-MM-DD): ");
//        String inputDate = scanner.nextLine();
//
//        // Define the desired date format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(inputDate, formatter);
//    try {
//        
//        System.out.println("You entered: " + date);
//    } catch (Exception e) {
//        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
//    }
//        return date;
//    }

    public Donor inputDonorDetails() {
        String id = inputDonorID();
        String type = inputDonorType();
        String name = inputDonorName();
        int phone = inputPhoneNum();
        String email = inputDonorEmail();
        double donation = inputDonationAmount();
//        LocalDate date = inputDonationDate();
        System.out.println();
        return new Donor(id, name, phone, email, type, donation);
    }
}
