/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import entity.Donor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import utility.MessageUI;

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

    public String inputDonorType() {
        String type = "";
        int choice = 0;
        do {
            System.out.print("Choose Donor Type: \n");
            System.out.println("1. Government");
            System.out.println("2. Private");
            System.out.println("3. Public");
            System.out.print("Enter choice(1-3): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    type = "Government";
                    break;
                case 2:
                    type = "Private";
                    break;
                case 3:
                    type = "Public";
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 1 && choice != 2 && choice != 3);

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

    public void inputDonation() {
        System.out.println("Donations: ");
        System.out.println("1. Donate Money");
        System.out.println("2. Donate Goods");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                inputDonationAmount();
                break;
            case 2:
                inputDonationItem();
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                break;
        }
    }

    public double inputDonationAmount() {
        System.out.print("Enter Donation Amount: RM ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        return amount;
    }

    public String inputDonationItem() {
        String item = "";
        int choice = 0;
        do {
            System.out.print("Choose Donation Item: ");
            System.out.println("1. Clothing");
            System.out.println("2. Furniture");
            System.out.println("3. Books");
            System.out.println("4. Electronic Devices");
            System.out.print("Enter choice(1-4): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    item = "clothing";
                    break;
                case 2:
                    item = "furniture";
                    break;
                case 3:
                    item = "books";
                    break;
                case 4:
                    item = "electronic devices";
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);

        return item;
    }

    public LocalDate inputDonationDate() { // can change to direct get System date
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String inputDate = scanner.nextLine();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);
        try {
            System.out.println("You entered: " + date);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
        return date;
    }

    public String inputEntityType() {
        String entity = "";
        System.out.println("Type of Entity: ");
        System.out.println("1. Organisation");
        System.out.println("2. Individual");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                entity = "Organisation";
                break;
            case 2:
                entity = "Individual";
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                break;
        }
        return entity;
    }

    public Donor inputDonorDetails() {
        String id = inputDonorID();
        String name = inputDonorName();
        int phone = inputPhoneNum();
        String email = inputDonorEmail();
//        double donation = inputDonationAmount();
//        LocalDate date = inputDonationDate();
        String type = inputDonorType();
        String entity = inputEntityType();
        System.out.println();
        return new Donor(id, name, phone, email, type, entity);
    }

    public int getFilterChoice() {
        System.out.println("Filter by: ");
        System.out.println("1. Donor Type");
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

    public void displayFilteredDonors(ListInterface<Donor> donors) {
        if (donors.isEmpty()) {
            System.out.println("No matching donors found.");
        } else {
            for (int i = 1; i <= donors.getNumberOfEntries(); i++) {
                System.out.println(donors.getEntry(i));
            }
        }
    }
}
