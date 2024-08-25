/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.*;
import adt.*;
import java.time.LocalDate;

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
    
    public String inputDoneeLocation() {
        System.out.print("Enter Donee Location: ");
        String location = scanner.nextLine();
        return location;
    }
    
    public Donee inputDoneeDetails() {
        String id = inputDoneeID();
        String type = inputDoneeType();
        String name = inputDoneeName();
        int phone = inputPhoneNum();
        String email = inputDoneeEmail();
        String location = inputDoneeLocation();
        System.out.println();
        return new Donee(id, type, name, phone, email,location);
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
    }

}
