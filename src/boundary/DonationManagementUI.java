/**
 *
 * @author haojuan
 */
package boundary;

import control.DonationManagement;
import entity.*;
import utility.*;
import adt.*; 
import dao.*;

import java.util.Scanner;
import utility.MessageUI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;

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
    
//All declaration
//------------------------------------------------------------------------------------
    
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
                case 1 ->
                    itemCategory = "Clothing";
                case 2 ->
                    itemCategory = "Food and Beverage";
                case 3 ->
                    itemCategory = "Books";
                case 4 ->
                    itemCategory = "Electronic Devices";
                case 5 ->
                    itemCategory = "Cash";
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice < 1 || choice > 5);

        return itemCategory;
    }
    
    private int getValidIntInput(String prompt) {
        int value = -1;
        while (value < 0) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value < 0) {
                    System.err.println("Value must be non-negative. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
        scanner.nextLine(); // Clear the newline character
        return value;
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

    private String getItemInput(String itemCategory) {
        int choice = 0;
        String itemsInput = "";
        switch (itemCategory) {
            case "Clothing":
                itemsInput = getCategoryItemInput("Clothes", "Pants", "Shoes", "Mask", "Hat");
                break;
            case "Food and Beverage":
                itemsInput = getCategoryItemInput("Noodles", "Rice", "Vegetable", "Meat", "Drinking Water");
                break;
            case "Books":
                itemsInput = getCategoryItemInput("Novel", "Magazine", "Textbook", "Comics", "Biography");
                break;
            case "Electronic Devices":
                itemsInput = getCategoryItemInput("Mobile Phone", "Computer", "Refrigerator", "Washing Machine", "Rice Cooker");
                break;
            case "Cash":
                itemsInput = "";
                break;
        }
        return itemsInput;
    }

    private String getCategoryItemInput(String... items) {
        int choice = 0;
        do {
            System.out.println("Choose Donation Item : ");
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i]);
            }
            System.out.print("Enter Items (1-" + items.length + ") : ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } while (choice < 1 || choice > items.length);
        return items[choice - 1];
    }
    
    private String getCategoryByChoice(int choice) {
        return switch (choice) {
            case 1 -> "Clothing";
            case 2 -> "Food and Beverage";
            case 3 -> "Books";
            case 4 -> "Electronic Devices";
            case 5 -> "Cash";
            default -> "";
        };
    }

    private List<String> getAvailableCategories() {
        return List.of("Clothing", "Food and Beverage", "Books", "Electronic Devices", "Cash");
    }
    
    private String getNewItemInput(String itemCategory) {
        int choice = 0;
        String itemsInput = "";
        switch (itemCategory) {
            case "Clothing":
                itemsInput = getNewCategoryItemInput("Clothes", "Pants", "Shoes", "Mask", "Hat");
                break;
            case "Food and Beverage":
                itemsInput = getNewCategoryItemInput("Noodles", "Rice", "Vegetable", "Meat", "Drinking Water");
                break;
            case "Books":
                itemsInput = getNewCategoryItemInput("Novel", "Magazine", "Textbook", "Comics", "Biography");
                break;
            case "Electronic Devices":
                itemsInput = getNewCategoryItemInput("Mobile Phone", "Computer", "Refrigerator", "Washing Machine", "Rice Cooker");
                break;
            case "Cash":
                itemsInput = "Cash";
                break;
        }
        return itemsInput;
    }

    private String getNewCategoryItemInput(String... newItem) {
        System.out.println("Choose Donation Item : ");
        for (int i = 0; i < newItem.length; i++) {
            System.out.println((i + 1) + ". " + newItem[i]);
        }
        return null;
    }
    
    public int displayFilterOptions() {
        System.out.println("**** Filter Donation *****");
        System.out.println("Filter by: ");
        System.out.println("1. Date Range");
        System.out.println("2. Donation Amount Range");
        System.out.println("3. Date and Amount Range");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        int choice = -1;
        while (choice < 0 || choice > 3) {
            System.out.print("Enter choice (0-3) : ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline
                if (choice < 0 || choice > 3) {
                    System.err.println("Invalid choice. Please enter a number between 0 and 3.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return choice;
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

    private void displayDonations(ListInterface<Donation> donations) {
        for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
            Donation donation = donations.getEntry(i);

            System.out.println("Donation ID     : " + donation.getDonationID());
            System.out.println("Donor ID        : " + donation.getDonorID());
            System.out.println("Donation Date   : " + donation.getDonationDate());
            System.out.println("Category        : " + donation.getItemCategory());
            System.out.println("Items           : " + donation.getItem());

            // Display the amount based on the type of donation
            if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                System.out.println("Amount (Cash)   : RM " + String.format("%.2f", donation.getCashAmount()));
            } else {
                System.out.println("Amount (Non-Cash): " + donation.getAmount());
            }

            System.out.println("-----------------------------------------");
        }
    }
    
//All method
//------------------------------------------------------------------------------- 
    private ListInterface<Donor> loadDonorData() {
        DonorDAO donorDAO = new DonorDAO();
        return donorDAO.retrieveFromFile();
    }

    public void addDonation() {
        ListInterface<Donor> donorList = loadDonorData();
        String donorID2="";
        System.out.println("*****Add Donation*****");

        LocalDate donationDate = LocalDate.now();
        System.out.println("Date        : " + donationDate);

        String donationID = controller.generateNextDonationID();
        System.out.println("Donation ID : " + donationID);
        while (true){
        System.out.print("Enter Donor ID    : ");
        String donorID = scanner.nextLine();
            if(isDonorIDValid(donorID, donorList)){
                donorID2 = donorID;
                break;
            }else{
                  System.out.println("Enter valid id.");      
                        }
        }

        String itemCategory = DonationItemCategory();
        String itemsInput = getItemInput(itemCategory);

        double amount = 0.0; // Initialize amount

        // Determine if the category is cash and handle accordingly
        if (itemCategory.equalsIgnoreCase("Cash")) {
            // For cash donations, amount is a double
            amount = getValidDoubleInput("Enter Cash Amount : ");
        } else {
            // For non-cash donations, amount is an integer
            int nonCashAmount = getValidIntInput("Enter Quantity : ");
            amount = nonCashAmount; // Or use another approach based on your logic
        }

        boolean success = controller.addDonation(donationID, donorID2, itemCategory, itemsInput, amount, donationDate);
        System.out.println(success ? greenText + "Donation added successfully!" + resetText : "Failed to add donation.");
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    private boolean isDonorIDValid(String donorID, ListInterface<Donor> donorList) {
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            if (donorList.getEntry(i).getDonorID().equalsIgnoreCase(donorID)) {
                return true;
            }
        }
        return false;
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
        System.out.println("***** Search Donation *****");
        System.out.print("Enter Donation ID: ");
        String donationID = scanner.nextLine();

        Donation donation = controller.getDonationById(donationID);
        if (donation != null) {
            System.out.println("Donation Date   : " + donation.getDonationDate());
            System.out.println("Donation ID     : " + donation.getDonationID());
            System.out.println("Donor ID        : " + donation.getDonorID());
            System.out.println("Donation Type   : " + donation.getItemCategory());
            System.out.println("Items           : " + donation.getItem());

            // Handle amount display based on donation type
            String amountDisplay = "";
            if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                amountDisplay = String.format("%.2f", donation.getCashAmount()); // Format as currency
            } else {
                amountDisplay = String.valueOf(donation.getAmount()); // Display integer value
            }
            System.out.println("Amount          : " + amountDisplay);

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

        // Display and select new Donor ID
        System.out.print("Enter new Donor ID : ");
        String newDonorID = scanner.nextLine();
        if (!newDonorID.isEmpty()) {
            donation.setDonorID(newDonorID);
        }

        // Display and select new Item Category
        System.out.println("Available Item Categories:");
        List<String> categories = getAvailableCategories(); // Method to get available categories
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.println("Current Donation Type : " + donation.getItemCategory());
        System.out.print("Enter new Donation Type : ");
        int categoryChoice = Integer.parseInt(scanner.nextLine());
        String newCategory = getCategoryByChoice(categoryChoice);

        // Update category if changed
        if (!newCategory.isEmpty()) {
            donation.setItemCategory(newCategory);
        }
        
        if(donation.getItemCategory().equalsIgnoreCase("Cash")){
            donation.setItem("Cash");
        }else{
            System.out.println("Current Donation Item : " + donation.getItem());
            getNewItemInput(newCategory);
            System.out.print("Enter new Item : ");
            String newItem = scanner.nextLine();
            if (!newItem.isEmpty()) {
                donation.setItem(newItem);
            }
        }
        
        // Handle amount or quantity based on category
        if (newCategory.equalsIgnoreCase("Cash")) {
            System.out.println("Current Amount : " + donation.getCashAmount());
            System.out.println("Enter new Amount : ");
            double newAmount = Double.parseDouble(scanner.nextLine());
            donation.setCashAmount(newAmount);
        } else {
            System.out.println("Current Quantity : " + donation.getAmount());
            System.out.println("Enter new Quantity : ");
            int newQuantity = Integer.parseInt(scanner.nextLine());
            donation.setAmount(newQuantity); // Assuming quantity is stored as amount for non-cash
        }

        System.out.println(greenText + "Donation details updated successfully!" + resetText);
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }

    
    public void trackDonation() {
        System.out.println("*****Track Donated Items in Categories*****");
        String itemCategory = DonationItemCategory();

        if (itemCategory.equals("Cash")) {
            // Track cash donations
            double totalCashAmount = controller.trackTotalCashDonations();
            System.out.println("Total cash donations: RM " + totalCashAmount);
        } else {
            // Track non-cash donations
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
        }

        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }

    
    public void listDonationsByDonors() {
            System.out.println("***** List Donation by Different Donor *****");

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

                    // Display the amount based on donation type
                    if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                        System.out.println("Cash Amount     : " + donation.getCashAmount());
                    } else {
                        System.out.println("Amount          : " + donation.getAmount());
                    }

                    System.out.println("-----------------------------------------");
                }
            } else {
                System.err.println("No donations found for the specified donor.");
            }
            System.out.println(greenText + "Press any key to continue..." + resetText);
            scanner.nextLine();
        }


    public void listDonations() {
        System.out.println("***** List All Donations *****");

        LinkedList<Donation> donations = controller.listDonations();
        if (donations != null && donations.getNumberOfEntries() > 0) {
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Donation donation = donations.getEntry(i);
                System.out.println("Donation ID     : " + donation.getDonationID());
                System.out.println("Donor ID        : " + donation.getDonorID());
                System.out.println("Donation Type   : " + donation.getItemCategory());
                System.out.println("Items           : " + donation.getItem());

                // Display the amount based on donation type
                if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                    System.out.println("Amount (Cash)   : " + donation.getCashAmount());
                } else {
                    System.out.println("Amount (Non-Cash): " + donation.getAmount());
                }

                System.out.println("-----------------------------------------");
            }
        } else {
            System.err.println("No donations found.");
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    
    public void filterByDateRange() {
        LocalDate startDate = getValidDateInput("Enter start date (dd-MM-yyyy): ");
        LocalDate endDate = getValidDateInput("Enter end date (dd-MM-yyyy): ");
        
        ListInterface<Donation> donations = controller.listDonations();
        Filter<Donation> filter = new Filter<>();
        
        ListInterface<Donation> filtered = filter.filterByDateRange(donations, startDate, endDate);
        System.out.println("Filtered by Date Range : " + startDate + " until " + endDate);
        displayDonations(filtered);
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }

    public void filterByDonationAmountRange() {
        double minAmount = getValidDoubleInput("Enter minimum donation amount: ");
        double maxAmount = getValidDoubleInput("Enter maximum donation amount: ");
        
        ListInterface<Donation> donations = controller.listDonations();
        Filter<Donation> filter = new Filter<>();
        
        ListInterface<Donation> filtered = filter.filterByDonationAmountRange(donations, minAmount, maxAmount);
        System.out.println("Filtered by Donation Amount Range:");
        displayDonations(filtered);
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
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
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    
    public void donationsReports() {
        // Generate and print summary report
        String summaryReport = controller.generateReport();
        System.out.println(summaryReport);

        // Generate and print detailed report
        String detailedReport = controller.generateDetailedReport();
        System.out.println(detailedReport);
    }
}
