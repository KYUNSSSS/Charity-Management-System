package control;

import adt.HashMap;
import adt.LinkedList;
import adt.ListInterface;
import adt.MapInterface;
import dao.DonationDAO;
import entity.Donation;
import boundary.DonationManagementUI;
import entity.Donor;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;
import utility.*;

public class DonationManagement {

    private DonationDAO donationDAO = new DonationDAO();
    public LinkedList<Donation> donationList;
    private DonationManagementUI ui;
    private Filter<Donation> filter;
    private HashMap<String, Double> categoryTotals = new HashMap<>();
    private HashMap<String, Double> itemTotals = new HashMap<>();
    private double cashTotal = 0.0;
    private Donation donation;

    public DonationManagement(DonationManagementUI ui) {
        this.ui = ui;
        this.donationList = donationDAO.loadDonationsFromFile(); // Load donations from file on initialization
    }

    public DonationManagement() {
        this.donationList = donationDAO.loadDonationsFromFile();
    }

    public void setUI(DonationManagementUI ui) {
        this.ui = ui;
    }

    public void runDonationManagement() {
        int choice;
        do {
            choice = ui.getMenuChoice();
            switch (choice) {
                case 1 ->
                    ui.addDonation();
                case 2 ->
                    ui.removeDonation();
                case 3 ->
                    ui.searchDonationById();
                case 4 ->
                    ui.amendDonationDetails();
                case 5 ->
                    ui.trackDonation();
                case 6 ->
                    ui.listDonationsByDonors();
                case 7 ->
                    ui.listDonations();
                case 8 ->
                    handleFilterChoice();//base on criteria
                case 9 ->
                    ui.donationsReports();
                case 0 ->
                    System.out.println("Exiting Donation Management System.");
                default ->
                    System.err.println("Invalid choice. Please select an option between 0 and 9.");
            }
        } while (choice != 0);
    }

    public String generateNextDonationID() {
        int nextID = 1; // Default starting ID
        LinkedList<Donation> donations = listDonations();
        if (donations.getNumberOfEntries() > 0) {
            Donation lastDonation = donations.getEntry(donations.getNumberOfEntries());
            String lastID = lastDonation.getDonationID();
            nextID = Integer.parseInt(lastID.replace("DNT", "")) + 1;
        }
        return String.format("DNT%05d", nextID); // Format as DNT00001, DNT00002, etc.
    }

    public boolean addDonation(String donationID, String donorID, String itemCategory, String item, double amount, LocalDate donationDate) {
        Donation donation;

        // Check if the itemCategory is cash
        if (itemCategory.equalsIgnoreCase("Cash")) {
            // For cash donations, directly use the double amount
            donation = new Donation(donationID, donorID, itemCategory, item, amount, donationDate);
        } else {
            // For non-cash donations, convert the amount to int
            int nonCashAmount = (int) amount;
            donation = new Donation(donationID, donorID, itemCategory, item, nonCashAmount, donationDate);
        }

        // Add the donation to the list and save if successful
        boolean isAdded = donationList.add(donation);
        if (isAdded) {
            donationDAO.saveDonationListToFile(donationList);
        }
        return isAdded;
    }

    public boolean removeDonation(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                donationList.remove(i);
                donationDAO.saveDonationListToFile(donationList); // Save the updated list to file
                return true;
            }
        }
        return false;
    }

    public Donation getDonationById(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                return donation;
            }
        }
        return null;
    }

    public void amendDonationDetails(String donationID, String newDonorID, String newItemCategory, String newItem, Double newAmount) {
        Donation donation = getDonationById(donationID);
        if (donation == null) {
            System.err.println("Donation not found.");
            return;
        }

        // Update the details if new values are provided
        if (newDonorID != null && !newDonorID.isEmpty()) {
            donation.setDonorID(newDonorID);
        }
        if (newItemCategory != null && !newItemCategory.isEmpty()) {
            donation.setItemCategory(newItemCategory);
        }
        if (newItem != null && !newItem.isEmpty()) {
            donation.setItem(newItem);
        }
        if (newAmount != null) {
            if (newItemCategory.equalsIgnoreCase("Cash")) {
                donation.setCashAmount(newAmount);
            } else {
                donation.setAmount(newAmount.intValue()); // Convert Double to int for non-cash donations
            }
        }

        // Save the updated donation list back to the file
        donationDAO.saveDonationListToFile(donationList);
    }

    public double trackTotalCashDonations() {
        double totalCashAmount = 0.0;
        LinkedList<Donation> allDonations = listDonations();

        for (int i = 1; i <= allDonations.getNumberOfEntries(); i++) {
            Donation donation = allDonations.getEntry(i);

            if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                totalCashAmount += donation.getCashAmount(); // Use getCashAmount() for cash donations
            }
        }

        return totalCashAmount;
    }

    public LinkedList<String> trackDonationByCategory(String itemCategory) {
        LinkedList<String> itemsInCategory = new LinkedList<>();

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);

            if (donation.getItemCategory().equalsIgnoreCase(itemCategory)) {
                String itemWithAmount;
                if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                    itemWithAmount = donation.getItem() + " (Amount: RM " + String.format("%.2f", donation.getCashAmount()) + ")";
                } else {
                    itemWithAmount = donation.getItem() + " (Amount: " + donation.getAmount() + ")";
                }
                itemsInCategory.add(itemWithAmount);
            }
        }
        return itemsInCategory;
    }

    public LinkedList<Donation> listDonationsByDonor(String donorID) {
        LinkedList<Donation> result = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                result.add(donation);
            }
        }
        return result;
    }

    public void handleFilterChoice() {
        int choice;
        do {
            choice = ui.displayFilterOptions();
            switch (choice) {
                case 1:
                    ui.filterByDateRange();
                    break;
                case 2:
                    ui.filterByDonationAmountRange();
                    break;
                case 3:
                    ui.filterByDateAndAmountRange();
                    break;
                case 0:
                    runDonationManagement();
                    break;
                default:
                    System.err.println("Invalid choice. Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    public ListInterface<Donation> filterDonationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return filter.filterByDateRange(donationList, startDate, endDate);
    }

    public ListInterface<Donation> filterDonationsByAmountRange(double minAmount, double maxAmount) {
        return filter.filterByDonationAmountRange(donationList, minAmount, maxAmount);
    }

    public LinkedList<Donation> listDonations() {
        return donationList;
    }

    // Get donations categorized by item within a specific category
    private MapInterface<String, Double> getItemTotalsByCategory(String category) {
        MapInterface<String, Double> itemTotals = new HashMap<>();

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);

            if (donation.getItemCategory().equalsIgnoreCase(category)) {
                String item = donation.getItem();
                double amount;

                // Check if the category is "cash" or non-cash
                if (category.equalsIgnoreCase("Cash")) {
                    amount = donation.getCashAmount(); // Use cash amount for cash donations
                } else {
                    amount = donation.getAmount(); // Use non-cash amount for other categories
                }

                double currentTotal = itemTotals.get(item) != null ? itemTotals.get(item) : 0;
                itemTotals.put(item, currentTotal + amount);
            }
        }
        return itemTotals;
    }

    public String generateReport() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fileName = "donation_summary_report_" + dateFormatter.format(LocalDate.now()) + ".txt";
        StringBuilder reportContent = new StringBuilder();

        try {
            Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
            Path filePath = downloadsFolder.resolve(fileName);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            reportContent.append("*****Donation Summary Report*****\n");
            reportContent.append("Date Generated    : ").append(LocalDate.now().format(dateFormatter)).append("\n\n");

            // Calculate totals
            calculateCategoryTotals();
            calculateItemTotals();

            ListInterface<Donor> filteredList = new LinkedList<>();
            for (int i = 1; i <= categoryTotals.capacity(); i++) {
                String category = categoryTotals.getKey(i);

                if (category == null) {
    //                System.out.println("Null donorID found at index " + i);
                    continue; // Skip null donorID
                }

                double totalAmount = categoryTotals.get(category);
                reportContent.append("Total ").append(category).append(" : ").append(totalAmount).append("\n");

                for (int j = 1; i <= itemTotals.capacity(); j++) {
                    String item = itemTotals.getKey(j);
                    if (item.startsWith(category)) {
                        double itemTotal = itemTotals.get(item);
                        reportContent.append("1. Total ").append(item.substring(category.length() + 2)).append(" : ").append(itemTotal).append("\n");
                    }
                }
                reportContent.append("--------------------------------------\n");
                
            }

            // Output total cash donations
            reportContent.append("Total Amount of Cash : RM ").append(String.format("%.2f", cashTotal)).append("\n");

            Files.write(filePath, reportContent.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public String generateDetailedReport() {
        StringBuilder report = new StringBuilder();
        report.append("***** Detailed Report *****\n");
        report.append("Date Generated: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n\n");

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            report.append("Donation ID     : ").append(donation.getDonationID()).append("\n");
            report.append("Donor ID        : ").append(donation.getDonorID()).append("\n");
            report.append("Donation Date   : ").append(donation.getDonationDate()).append("\n");
            report.append("Category        : ").append(donation.getItemCategory()).append("\n");
            report.append("Items           : ").append(donation.getItem()).append("\n");

            // Display amount based on donation category
            if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                report.append("Cash Amount     : RM ").append(String.format("%.2f", donation.getCashAmount())).append("\n");
            } else {
                report.append("Amount          : ").append(donation.getAmount()).append("\n");
            }

            report.append("-----------------------------------------\n");
        }

        return report.toString();
    }

    // Method to calculate totals by category
    private void calculateCategoryTotals() {
        cashTotal = 0.0; // Reset cash total
        categoryTotals.clear(); // Clear previous totals

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            String category = donation.getItemCategory();
            double amount;

            // Determine amount based on category
            if (category.equalsIgnoreCase("Cash")) {
                amount = donation.getCashAmount(); // Use cash amount for cash donations
                cashTotal += amount;
            } else {
                amount = donation.getAmount(); // Use regular amount for non-cash donations
            }

            // Update category totals
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }

        // Ensure the total cash amount is included in the category totals
        categoryTotals.put("Total Amount of Cash", cashTotal);
    }

    private void calculateItemTotals() {
        itemTotals.clear(); // Clear previous totals

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            String category = donation.getItemCategory();
            String item = donation.getItem();
            double amount;

            // Determine amount based on category
            if (category.equalsIgnoreCase("Cash")) {
                amount = donation.getCashAmount(); // Use cash amount for cash donations
            } else {
                amount = donation.getAmount(); // Use regular amount for non-cash donations
            }

            String itemKey = category + ": " + item;
            itemTotals.put(itemKey, itemTotals.getOrDefault(itemKey, 0.0) + amount);
        }
    }

    public static void main(String[] args) {
        DonationManagement controller = new DonationManagement();
        DonationManagementUI ui = new DonationManagementUI(controller);
        controller.setUI(ui);
        controller.runDonationManagement();
    }
}
