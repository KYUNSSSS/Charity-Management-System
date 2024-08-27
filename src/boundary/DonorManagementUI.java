/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import entity.Donor;
import java.time.LocalDate;
import java.util.Scanner;
import utility.MessageUI;
import utility.Validator;

/**
 *
 * @author xuan
 */
public class DonorManagementUI {

    Scanner scanner = new Scanner(System.in);
    // ANSI escape code for blue text
    final String BLUE = "\033[34m";
    final String RESET = "\033[0m";

    public int getMenuChoice() {
        while (true) {
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
            String choice = scanner.nextLine();

            if (Validator.isValidPositiveInteger(choice)) {
                return Integer.parseInt(choice);
            }

            MessageUI.displayInvalidChoiceMessage();
        }
    }

    public void displayDonor(ListInterface<Donor> donorList) {
        StringBuilder outputStr = new StringBuilder();

        // Define table headers
        String header = String.format("%-10s %-20s %-15s %-15s %-15s %-15s",
                "DonorID", "Name", "Contact No", "Email", "Type", "Entity Type");

        // Add header and a line separator to the output string
        outputStr.append("=".repeat(header.length())).append("\n");
        outputStr.append(header).append("\n");
        outputStr.append("=".repeat(header.length())).append("\n");

        // Add each donor's details
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorDetails = String.format("%-10s %-20s %-15s %-15s %-15s %-15s",
                    donor.getDonorID(),
                    donor.getName(),
                    donor.getContactNo(),
                    donor.getEmail(),
                    donor.getType(),
                    donor.getEntityType());
            outputStr.append(donorDetails).append("\n");
        }
        outputStr.append("=".repeat(header.length())).append("\n");
        System.out.println("\nList of Donor:\n" + outputStr);
    }

    public void displayDonor(ListInterface<Donor> donorList, String id) {
        StringBuilder outputStr = new StringBuilder();

        // Define table headers
        String header = String.format("%-10s %-20s %-15s %-15s %-15s %-15s",
                "DonorID", "Name", "Contact No", "Email", "Type", "Entity Type");

        // Add header and a line separator to the output string
        outputStr.append("=".repeat(header.length())).append("\n");
        outputStr.append(header).append("\n");
        outputStr.append("=".repeat(header.length())).append("\n");

        // Add each donor's details
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor.getDonorID().equals(id)) {
                String donorDetails = String.format(BLUE + "%-10s %-20s %-15s %-15s %-15s %-15s",
                        donor.getDonorID(),
                        donor.getName(),
                        donor.getContactNo(),
                        donor.getEmail(),
                        donor.getType(),
                        donor.getEntityType() + RESET);
                outputStr.append(donorDetails).append("\n");
            } else {
                String donorDetails = String.format("%-10s %-20s %-15s %-15s %-15s %-15s",
                        donor.getDonorID(),
                        donor.getName(),
                        donor.getContactNo(),
                        donor.getEmail(),
                        donor.getType(),
                        donor.getEntityType());
                outputStr.append(donorDetails).append("\n");
            }
        }
        outputStr.append("=".repeat(header.length())).append("\n");
        System.out.println("\nList of Donor:\n" + outputStr);
    }

    public String inputDonorID() {
        while (true) {
            System.out.print("Enter Donor ID: ");
            String id = scanner.nextLine();

            if (Validator.isValidID(id)) {
                return id;
            }
            System.err.println("\nInvalid Input. Please enter a valid Donor ID.");
        }
    }

    public String inputDonorType() {
        String type = "";
        int choice = 0;
        do {
            System.out.print("Donor Type: \n");
            System.out.println("\t1. Government");
            System.out.println("\t2. Private");
            System.out.println("\t3. Public");
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
        while (true) {
            System.out.print("Enter Donor Name: ");
            String name = scanner.nextLine();

            if (Validator.isAlphabetic(name)) {
                return name;
            }
            System.out.println("Please enter alphabet only.");
        }
    }

    public String inputDonorEmail() {
        while (true) {
            System.out.print("Enter Donor Email: ");
            String email = scanner.nextLine();

            if (Validator.isValidEmail(email)) {
                return email;
            }
            System.out.println("Please email in correct format.");
        }
    }

    public int inputPhoneNum() {
        while (true) {
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();
            if (Validator.isValidPhoneNumber(phone)) {
                return Integer.parseInt(phone);
            }
            System.out.println("Please enter a valid phone number.");
        }
    }

//    public void inputDonation() {
//        System.out.println("Donations: ");
//        System.out.println("1. Donate Money");
//        System.out.println("2. Donate Goods");
//        System.out.print("Enter your choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//        switch (choice) {
//            case 1:
//                inputDonationAmount();
//                break;
//            case 2:
//                inputDonationItem();
//                break;
//            default:
//                MessageUI.displayInvalidChoiceMessage();
//                break;
//        }
//    }
//
//    public double inputDonationAmount() {
//        System.out.print("Enter Donation Amount: RM ");
//        int amount = scanner.nextInt();
//        scanner.nextLine();
//        return amount;
//    }
//
//    public String inputDonationItem() {
//        String item = "";
//        int choice = 0;
//        do {
//            System.out.print("Choose Donation Item: ");
//            System.out.println("1. Clothing");
//            System.out.println("2. Furniture");
//            System.out.println("3. Books");
//            System.out.println("4. Electronic Devices");
//            System.out.print("Enter choice(1-4): ");
//            choice = scanner.nextInt();
//            scanner.nextLine();
//            switch (choice) {
//                case 1:
//                    item = "clothing";
//                    break;
//                case 2:
//                    item = "furniture";
//                    break;
//                case 3:
//                    item = "books";
//                    break;
//                case 4:
//                    item = "electronic devices";
//                default:
//                    MessageUI.displayInvalidChoiceMessage();
//            }
//        } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);
//
//        return item;
//    }
//
//    public LocalDate inputDonationDate() { // can change to direct get System date
//        System.out.print("Enter the date (YYYY-MM-DD): ");
//        String inputDate = scanner.nextLine();
//
//        // Define the desired date format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(inputDate, formatter);
//        try {
//            System.out.println("You entered: " + date);
//        } catch (Exception e) {
//            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
//        }
//        return date;
//    }
    public String inputEntityType() {
        String entity = "";
        int choice = 0;
        do {
            System.out.println("Type of Entity: ");
            System.out.println("\t1. Organisation");
            System.out.println("\t2. Individual");
            System.out.print("Enter your choice (1-2): ");
            choice = scanner.nextInt();
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
        } while (choice != 1 && choice != 2);

        return entity;
    }

    public Donor inputDonorDetails() {
//        String id = inputDonorID();
        String name = inputDonorName();
        int phone = inputPhoneNum();
        String email = inputDonorEmail();
        String type = inputDonorType();
        String entity = inputEntityType();
        System.out.println();
        return new Donor("DNR00000", name, phone, email, type, entity);
    }

    public int getFilterChoice() {
        while (true) {
            System.out.println("Filter by: ");
            System.out.println("1. Donor Type");
            System.out.println("2. Entity Type (Organisation / Individual)");
            System.out.println("3. Date Range");
            System.out.println("4. Donation Amount Range");
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            if (Validator.isValidPositiveInteger(choice)) {
                return Integer.parseInt(choice);
            }

            MessageUI.displayInvalidChoiceMessage();
        }
    }

    public int getReportChoice() {
        while (true) {
            System.out.println("Summary Reports: ");
            System.out.println("1. Donor Type");
            System.out.println("2. Entity Type (Organisation / Individual)");
            System.out.println("3. Date Range");
            System.out.println("4. Donation Amount Range");
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            if (Validator.isValidPositiveInteger(choice)) {
                return Integer.parseInt(choice);
            }

            MessageUI.displayInvalidChoiceMessage();
        }
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
        while (true) {
            System.out.print("Enter minimum donation amount: ");
            String input = scanner.nextLine().trim();

            if (Validator.isValidAmount(input)) {
                return Double.parseDouble(input);
            }

            System.out.println("\nInvalid Input. Please enter a positive value.");
        }
    }

    public double inputMaxAmount() {
        while (true) {
            System.out.print("Enter maximum donation amount: ");
            String input = scanner.nextLine().trim();

            if (Validator.isValidAmount(input)) {
                return Double.parseDouble(input);
            }

            System.out.println("\nInvalid Input. Please enter a positive value.");
        }
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

    public void displayDonorDetails(Donor donor) {
        System.out.println("**********Donor Details**********");
        System.out.println("Donor ID      : " + donor.getDonorID());
        System.out.println("Donor Name    : " + donor.getName());
        System.out.println("Contact No    : " + donor.getContactNo());
        System.out.println("Email         : " + donor.getEmail());
        System.out.println("Type of Donor : " + donor.getType());
        System.out.println("Entity Type   : " + donor.getEntityType());
        System.out.println("-----------------------------------------");
    }

    public void printDonorTypeSummary(String type, int count, double percentage, double total, double average, double max, double min) {
        System.out.printf("%-10s | %-16d | %-10s | RM%-15.2f | RM%-17.2f | RM%-8.2f | RM%-8.2f\n", type, count, percentage + "%", total, average, max, min);
    }
}
