/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.*;
import adt.*;
import java.time.LocalDate;

import java.util.Scanner;
import utility.MessageUI;
import utility.Validator;

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
        while (true) {
            System.out.print("Enter Donee ID: ");
            String id = scanner.nextLine();

            if (Validator.isValidID(id)) {
                return id;
            }
            System.out.println("\nInvalid Input. Please enter a valid Distribution ID without symbols. [Eg. A001].");
        }
    }

    public String inputDoneeType() {
        String type;
        while (true) {
            System.out.print("O - Organisation\nF - Family\nI - Individual\nEnter Donee Type(O/F/I): ");
            type = scanner.nextLine().trim(); 

            if (type.equalsIgnoreCase("O") || 
                type.equalsIgnoreCase("F") || 
                type.equalsIgnoreCase("I")) {
                break;
            } else {
                System.out.println("\nInvalid Type.Enter O/F/I only.");
            }
        }
        return type;
        
      
    }

    public String inputDoneeName() {
        while (true) {
            System.out.print("Enter  Name (Ali bin Abu): ");
            String name = scanner.nextLine();

            if (Validator.isAlphabetic(name)) {
                return name;
            }
            System.out.println("Please enter alphabet only.");
        }
    }

    public String inputDoneeEmail() {
        System.out.print("Enter Donee Email (example@gmail.com: ");
        String email = scanner.nextLine();
        return email;
    }

    public int inputPhoneNum() {
        while (true){
        System.out.print("Enter Phone Number(0123456789): ");
        String phone = scanner.nextLine();
        if (Validator.isValidPhoneNumber(phone)){
            return Integer.parseInt(phone);
        }
            System.out.println("Please enter a valid phone number.");
        }
    }
    
    public String inputDoneeLocation() {
        while (true) {
            System.out.print("Enter Donee Location (KL): ");
            String location = scanner.nextLine();

            if (Validator.isAlphabetic(location)) {
                return location;
            }
            System.out.println("Please enter alphabet only.");
        }
        
        
        
    }
    
    public Donee inputDoneeDetails() {
        //String id = inputDoneeID();
        String type = inputDoneeType();
        String name = inputDoneeName();
        int phone = inputPhoneNum();
        String email = inputDoneeEmail();
        String location = inputDoneeLocation();
        System.out.println("Donee Details Registered.");
        MessageUI.pressAnyKeyToContinue();
        return new Donee("DE000", type, name, phone, email,location);
    }

    public int getFilterChoice() {
        System.out.println("Filter by: ");
        System.out.println("1. Donee Type");
        System.out.println("2. Date Range");
        System.out.println("3. Donation Amount Range");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public LocalDate inputStartDate() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        return LocalDate.parse(scanner.next());
    }

    public LocalDate inputEndDate() {
        System.out.print("Enter end date (YYYY-MM-DD): ");
        return LocalDate.parse(scanner.next());
    }

    public double inputMinAmount() {
        System.out.print("Enter minimum donation amount: ");
        return scanner.nextDouble();
    }

    public double inputMaxAmount() {
        System.out.print("Enter maximum donation amount: ");
        return scanner.nextDouble();
    }

    public void displayFilteredDonees(ListInterface<Donee> donees) {
        if (donees.isEmpty()) {
            System.out.println("No matching donees found.");
        } else {
            for (int i = 1; i <= donees.getNumberOfEntries(); i++) {
                System.out.println(donees.getEntry(i));
            }
        }
        MessageUI.pressAnyKeyToContinue();
    }

}
