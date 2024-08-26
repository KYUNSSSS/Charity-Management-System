/**
 *
 * @author haojuan
 */
package boundary;

import control.DonationManagement;
import entity.Donation;
import utility.*;
import adt.*; 

import java.util.Scanner;
import utility.MessageUI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class DonationManagementUI {
    Scanner scanner = new Scanner(System.in);
    private DonationManagement controller;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    
    // Constructor that takes DonationManagement as a parameter
    public DonationManagementUI(DonationManagement controller) {
        this.controller = controller;
    } 
    // ANSI escape code for green text
    String greenText = "\u001B[32m";

    // ANSI escape code to reset to default color
    String resetText = "\u001B[0m";
    
    public int getMenuChoice() {
        System.out.println("*****Donation Management System Menu*****");
        System.out.println("1. Add Donation");
        System.out.println("2. Remove Donation");
        System.out.println("3. Search Donation by ID");
        System.out.println("4. Amend Donation details");
        System.out.println("5. Track Donated Items in Categories");
        System.out.println("6. List Donations by Donors");
        System.out.println("7. List Donations");
        System.out.println("8. Filter donations");
        System.out.println("9. Generate Donation Reports");
        System.out.println("0. Exit");
        System.out.print("Enter your choice : ");
        int choice = -1;
        while (choice < 0 || choice > 9) {
            System.out.print("Enter choice (0-9) : ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number between 0 and 9.");
            }
        }
        return choice;
    }
    
    public String DonationItemCategory() {
        String itemCategory = "";
        int choice = 0;
        do {
            System.out.println("Choose Donation Category : ");
            System.out.println("1. Clothing");
            System.out.println("2. Food and Beverage");
            System.out.println("3. Books");
            System.out.println("4. Electronic Devices");
            System.out.println("5. Cash");
            System.out.print("Enter choice(1-5) : ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> itemCategory = "Clothing";
                case 2 -> itemCategory = "Food and Beverage";
                case 3 -> itemCategory = "Books";
                case 4 -> itemCategory = "Electronic Devices";
                case 5 -> itemCategory = "Cash";
                default -> MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice < 1 || choice > 5);

        return itemCategory;
    }
    
    private String generateNextDonationID() {
    int nextID = 1; // Default starting ID
    LinkedList<Donation> donations = controller.listDonations();
    if (donations.getNumberOfEntries() > 0) {
        Donation lastDonation = donations.getEntry(donations.getNumberOfEntries());
        String lastID = lastDonation.getDonationID();
        nextID = Integer.parseInt(lastID.replace("DNT", "")) + 1;
    }
    return String.format("DNT%05d", nextID); // Format as DNT00001, DNT00002, etc.
}
    
    public void addDonation() {
        System.out.println("*****Add Donation*****");
            
        LocalDate donationDate = LocalDate.now();
        System.out.println("Date        : " + donationDate);
        
        String donationID = generateNextDonationID();
        System.out.println("Donation ID : " + donationID);
        
        System.out.print("Enter Donor ID    : ");
        String donorID = scanner.nextLine();

        String itemCategory = DonationItemCategory();

        String itemsInput = "";
        int choice = 0;
        if("Clothing".equals(itemCategory)){
            do {
                System.out.println("1. Clothes");
                System.out.println("2. Pants");
                System.out.println("3. Shoes");
                System.out.println("4. Mask");
                System.out.println("5. Hat");
                System.out.print("Enter Items (1-5) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> itemsInput = "Clothes";
                    case 2 -> itemsInput = "Pants";
                    case 3 -> itemsInput = "Shoes";
                    case 4 -> itemsInput = "Mask";
                    case 5 -> itemsInput = "Hat";
                    default -> MessageUI.displayInvalidChoiceMessage();
                }
            } while (choice < 1 || choice > 5);
            
        }else if("Food and Beverage".equals(itemCategory)){
            do {
                System.out.println("1. Noodles");
                System.out.println("2. Rice");
                System.out.println("3. Vegetable");
                System.out.println("4. Meat");
                System.out.println("5. Drinking Water");
                System.out.println("Enter Items (1-5) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> itemsInput = "Noodle";
                    case 2 -> itemsInput = "Rice";
                    case 3 -> itemsInput = "Vegetable";
                    case 4 -> itemsInput = "Meat";
                    case 5 -> itemsInput = "Drinking Water";
                    default -> MessageUI.displayInvalidChoiceMessage();
                }
            } while (choice < 1 || choice > 5);
            
        }else if("Books".equals(itemCategory)){
            do {
                System.out.println("1. Novel");
                System.out.println("2. Magazine");
                System.out.println("3. Textbook");
                System.out.println("4. Comics");
                System.out.println("5. Biography");
                System.out.println("Enter Items (1-5) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> itemsInput = "Novel";
                    case 2 -> itemsInput = "Magazine";
                    case 3 -> itemsInput = "Textbook";
                    case 4 -> itemsInput = "Comics";
                    case 5 -> itemsInput = "Biography";
                    default -> MessageUI.displayInvalidChoiceMessage();
                }
            } while (choice < 1 || choice > 5);
        }else if("Electrical Devices".equals(itemCategory)){
            do {
                System.out.println("1. Mobile Phone");
                System.out.println("2. Computer");
                System.out.println("3. Refrigerator");
                System.out.println("4. Washing Machine");
                System.out.println("5. Rice Cooker");
                System.out.println("Enter Items (1-5) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> itemsInput = "Mobile Phone";
                    case 2 -> itemsInput = "Computer";
                    case 3 -> itemsInput = "Refrigerator";
                    case 4 -> itemsInput = "Washing Machine";
                    case 5 -> itemsInput = "Rice Cooker";
                    default -> MessageUI.displayInvalidChoiceMessage();
                }
            } while (choice < 1 || choice > 5);
        }else if("Cash".equals(itemCategory)){
            itemsInput = "-";
        }
        double amount = 0.0;
        boolean validAmount = false;
        while (!validAmount) {
            System.out.print("Enter Amount : ");
            String amountInput = scanner.nextLine().trim();
            try {
                amount = Double.parseDouble(amountInput);
                validAmount = true;
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount. Please enter a valid number.");
            }
        }

        boolean success = controller.addDonation(donationID, donorID, itemCategory, itemsInput, amount, donationDate);
        if (success) {
            System.out.println(greenText + "Donation added successfully!" + resetText);
        } else {
            System.err.println("Failed to add donation.");
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    
    public void removeDonation() {
        System.out.println("*****Remove Donation*****");

        System.out.print("Enter Donation ID: ");
        String donationID = scanner.nextLine();

        Donation donation = controller.getDonationById(donationID);
        if (donation != null) {
            System.out.println("Donation ID : " + donation.getDonationID());

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Confirmation for remove (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y") || confirmation.equals("yes")) {
                    boolean success = controller.removeDonation(donationID);
                    if (success) {
                        System.out.println(greenText + "Donation removed successfully!" + resetText);
                    } else {
                        System.err.println("Failed to remove donation.");
                    }
                    validInput = true;
                } else if (confirmation.equals("n") || confirmation.equals("no")) {
                    System.err.println("Action canceled.");
                    validInput = true;
                } else {
                    System.err.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }

    public void searchDonationById() {
        System.out.println("*****Search Donation*****");
        System.out.print("Enter Donation ID: ");
        String donationID = scanner.nextLine();

        Donation donation = controller.getDonationById(donationID);
        if (donation != null) {
            System.out.println("Donation Date   : " + donation.getDonationDate());
            System.out.println("Donation ID     : " + donation.getDonationID());
            System.out.println("Donor ID        : " + donation.getDonorID());
            System.out.println("Donation Type   : " + donation.getItemCategory());
            System.out.println("Items           : " + donation.getItem());
            System.out.println("Amount          : " + donation.getAmount());
            System.out.println(greenText + "Done Searching ..." + resetText);
        } else {
            System.err.println("Donation not found.");
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }

    public void amendDonationDetails() {
        System.out.println("***** Amend Donation Details *****");

        System.out.print("Enter Donation ID to amend: ");
        String donationID = scanner.nextLine();

        Donation donation = controller.getDonationById(donationID);
        if (donation == null) {
            System.err.println("Donation ID not found.");
            return;
        }

        System.out.println("Leave blank and press Enter to keep the current value.");

        System.out.print("Current Donor ID: " + donation.getDonorID() + "\nEnter new Donor ID: ");
        String newDonorID = scanner.nextLine();

        System.out.print("Current Donation Type: " + donation.getItemCategory() + "\nEnter new Donation Type: ");
        String newItemCategory = scanner.nextLine();

        System.out.print("Current Item: " + donation.getItem() + "\nEnter new Item: ");
        String newItem = scanner.nextLine();

        System.out.print("Current Amount: " + donation.getAmount() + "\nEnter new Amount: ");
        String newAmountInput = scanner.nextLine();
        Double newAmount = !newAmountInput.isEmpty() ? Double.parseDouble(newAmountInput) : null;

        controller.amendDonationDetails(donationID, newDonorID, newItemCategory, newItem, newAmount);

        System.out.println(greenText + "Donation details amended successfully!" + resetText);
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }


    
    public void trackDonation() {
        System.out.println("*****Track Donated Items in Categories*****");
        String itemCategory = DonationItemCategory();

        LinkedList<String> items = controller.trackDonationByCategory(itemCategory);
        System.out.println("Tracking items for category: " + itemCategory);
        if (items.getNumberOfEntries() > 0) {
            System.out.println("Items in category " + itemCategory + ":");
            for (int i = 1; i <= items.getNumberOfEntries(); i++) {
                System.out.println(i + ". " + items.getEntry(i));
            }
            System.out.println(greenText + "Done Tracking ..." + resetText);
        } else {
            System.err.println("No items found in category " + itemCategory);
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    
    public void listDonationsByDonors() {
        System.out.println("*****List Donation by Different Donor*****");

        // Assuming the controller can list donations by donor
        System.out.print("Enter Donor ID: ");
        String donorID = scanner.nextLine();

        LinkedList<Donation> donations = controller.listDonationsByDonor(donorID);
        if (donations != null && donations.getNumberOfEntries() > 0) {
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Donation donation = donations.getEntry(i);
                System.out.println("Donation Date   : " + donation.getDonationDate());
                System.out.println("Donation ID     : " + donation.getDonationID());
                System.out.println("Donation Type   : " + donation.getItemCategory());
                System.out.println("Items           : " + donation.getItem());
                System.out.println("Amount          : " + donation.getAmount());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.err.println("No donations found for the specified donor.");
        }
    }

    public void listDonations() {
        System.out.println("***** List All Donations *****");

        LinkedList<Donation> donations = controller.listDonations();
        if (donations != null && donations.getNumberOfEntries() > 0) {
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Donation donation = donations.getEntry(i);
                System.out.println("Donation ID: " + donation.getDonationID());
                System.out.println("Donor ID: " + donation.getDonorID());
                System.out.println("Donation Type: " + donation.getItemCategory());
                System.out.println("Items: " + donation.getItem());
                System.out.println("Amount : " + donation.getAmount());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.err.println("No donations found.");
        }
    }
    

    public int displayFilterOptions() {
        System.out.println("**** Filter Donation *****");
        System.out.println("Filter by: ");
        System.out.println("1. Date Range");
        System.out.println("2. Donation Amount Range");
        System.out.println("3. Date and Amount Range");
        System.out.print("Enter your choice: ");
        int choice = 0;
        while (choice < 1 || choice > 3) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline
                if (choice < 1 || choice > 3) {
                    System.err.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return choice;
    }
    
    public void filterByDateRange() {
        LocalDate startDate = getValidDateInput("Enter start date (dd-MM-yyyy): ");
        LocalDate endDate = getValidDateInput("Enter end date (dd-MM-yyyy): ");
        
        ListInterface<Donation> donations = controller.listDonations();
        Filter<Donation> filter = new Filter<>();
        
        ListInterface<Donation> filtered = filter.filterByDateRange(donations, startDate, endDate);
        System.out.println("Filtered by Date Range : " + startDate + " until " + endDate);
        displayDonations(filtered);
    }

    public void filterByDonationAmountRange() {
        double minAmount = getValidDoubleInput("Enter minimum donation amount: ");
        double maxAmount = getValidDoubleInput("Enter maximum donation amount: ");
        
        ListInterface<Donation> donations = controller.listDonations();
        Filter<Donation> filter = new Filter<>();
        
        ListInterface<Donation> filtered = filter.filterByDonationAmountRange(donations, minAmount, maxAmount);
        System.out.println("Filtered by Donation Amount Range:");
        displayDonations(filtered);
    }

    public void filterByDateAndAmountRange() {
        LocalDate startDate = getValidDateInput("Enter start date (dd-MM-yyyy): ");
        LocalDate endDate = getValidDateInput("Enter end date (dd-MM-yyyy): ");
        double minAmount = getValidDoubleInput("Enter minimum donation amount: ");
        double maxAmount = getValidDoubleInput("Enter maximum donation amount: ");
        
        ListInterface<Donation> donations = controller.listDonations();
        Filter<Donation> filter = new Filter<>();
        
        ListInterface<Donation> filteredByDate = filter.filterByDateRange(donations, startDate, endDate);
        ListInterface<Donation> filteredByAmount = filter.filterByDonationAmountRange(donations, minAmount, maxAmount);

        System.out.println("Filtered by Date Range:");
        displayDonations(filteredByDate);
        
        System.out.println("Filtered by Donation Amount Range:");
        displayDonations(filteredByAmount);
    }

    private LocalDate getValidDateInput(String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                date = LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        return date;
    }

    private double getValidDoubleInput(String prompt) {
        double value = -1;
        while (value < 0) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                if (value < 0) {
                    System.err.println("Amount must be positive. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid amount format. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        scanner.nextLine(); // Clear the newline character
        return value;
    }

    private void displayDonations(ListInterface<Donation> donations) {
        for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
            Donation donation = donations.getEntry(i);
            System.out.println(donation);
        }
    }

    public void donationsReports() {
        System.out.println("*****Generate Summary Reports*****");
        String report = controller.generateReport();
        System.out.println(report);
    }
}
