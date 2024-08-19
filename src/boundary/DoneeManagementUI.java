/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Yunshen
 */
public class DoneeManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        try {
            System.out.println("\n******DONEE MANAGEMENT******");
            System.out.println("1. Add New Donee");
            System.out.println("2. Update Donee Details");
            System.out.println("3. Search Donee Details");
            System.out.println("4. List Donee Donations");
            System.out.println("5. Filter Donee");
            System.out.println("6. Remove Donee");
            System.out.println("7. Generate Summary Report");
            System.out.println("0. Quit");
            System.out.print("Enter choice(0-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            return choice;
        } catch (Exception ex) {
            System.out.println("Invalid input");
        }
        return 0;
    }

    public void listAllDonees(String outputStr) {
        System.out.println("\nList of Donee:\n" + outputStr);
    }

    public String inputDoneeID() {
        System.out.print("Enter Donee ID: ");
        String id = scanner.nextLine();
        return id;
    }

    public String inputDoneeType() {
        System.out.print("Enter Donee Type(Organisation/Family/Individual): ");
        String type = scanner.nextLine();
        return type;
    }

    public String inputDoneeName() {
        System.out.print("Enter Donee Name: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputDoneeEmail() {
        System.out.print("Enter Donee Email: ");
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
        System.out.print("Enter Donation Amount:RM ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        return amount;
    }

    public LocalDate inputDonationDate() {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String inputDate = scanner.nextLine();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);
//    try {
//        
//        System.out.println("You entered: " + date);
//    } catch (Exception e) {
//        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
//    }
        return date;
    }

    public Donee inputDoneeDetails() {
        String id = inputDoneeID();
        String type = inputDoneeType();
        String name = inputDoneeName();
        int phone = inputPhoneNum();
        String email = inputDoneeEmail();
        double amount = inputDonationAmount();
        LocalDate date = inputDonationDate();
        System.out.println();
        return new Donee(id, type, name, phone, email, amount, date);
    }

}
