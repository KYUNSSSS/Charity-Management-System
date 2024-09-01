package control;

import adt.HashMap;
import adt.LinkedList;
import adt.ListInterface;
import adt.MapInterface;
import dao.DonationDAO;
import entity.Donation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utility.*;

/**
 *
 * @author Loh Hao Juan
 */

public class DonationManagement {

    private DonationDAO donationDAO = new DonationDAO();
    public LinkedList<Donation> donationList;
    private Filter<Donation> filter;
    private LinkedList<CategoryTotal> categoryTotalsList = new LinkedList<>();
    private LinkedList<ItemTotal> itemTotalsList = new LinkedList<>();
    private double cashTotal = 0.0;
    private Donation donation;

    public DonationManagement() {
        this.donationList = donationDAO.loadDonationsFromFile();
    }

    public String generateNextDonationID() {
        int nextID = 1;
        LinkedList<Donation> donations = listDonations();
        if (donations.getNumberOfEntries() > 0) {
            Donation lastDonation = donations.getEntry(donations.getNumberOfEntries());
            String lastID = lastDonation.getDonationID();
            nextID = Integer.parseInt(lastID.replace("DNT", "")) + 1;
        }
        return String.format("DNT%05d", nextID);
    }

    public boolean addDonation(String donationID, String donorID, String itemCategory, String item, double amount, LocalDate donationDate) {
        Donation donation;
        if (itemCategory.equalsIgnoreCase("Cash")) {
            donation = new Donation(donationID, donorID, itemCategory, item, amount, donationDate);
        } else {
            int nonCashAmount = (int) amount;
            donation = new Donation(donationID, donorID, itemCategory, item, nonCashAmount, donationDate);
        }
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
                donation.setAmount(newAmount.intValue()); 
            }
        }
        donationDAO.saveDonationListToFile(donationList);
    }

    public double trackTotalCashDonations() {
        double totalCashAmount = 0.0;
        LinkedList<Donation> allDonations = listDonations();

        for (int i = 1; i <= allDonations.getNumberOfEntries(); i++) {
            Donation donation = allDonations.getEntry(i);
            if (donation.getItemCategory().equalsIgnoreCase("Cash")) {
                totalCashAmount += donation.getCashAmount(); 
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

    public ListInterface<Donation> filterDonationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return filter.filterByDateRange(donationList, startDate, endDate);
    }

    public ListInterface<Donation> filterDonationsByAmountRange(double minAmount, double maxAmount) {
        return filter.filterByDonationAmountRange(donationList, minAmount, maxAmount);
    }

    public LinkedList<Donation> listDonations() {
        return donationList;
    }

    private MapInterface<String, Double> getItemTotalsByCategory(String category) {
        MapInterface<String, Double> itemTotals = new HashMap<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getItemCategory().equalsIgnoreCase(category)) {
                String item = donation.getItem();
                double amount;
                if (category.equalsIgnoreCase("Cash")) {
                    amount = donation.getCashAmount();
                } else {
                    amount = donation.getAmount();
                }
                double currentTotal = itemTotals.get(item) != null ? itemTotals.get(item) : 0;
                itemTotals.put(item, currentTotal + amount);
            }
        }
        return itemTotals;
    }
    
    private class CategoryTotal {
        String category;
        double total;

        public CategoryTotal(String category, double total) {
            this.category = category;
            this.total = total;
        }
    }

    private class ItemTotal {
        String category;
        String item;
        double total;

        public ItemTotal(String category, String item, double total) {
            this.category = category;
            this.item = item;
            this.total = total;
        }
    }

    public String generateReport() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("***** Donation Summary Report *****\n");
        reportContent.append("Date Generated    : ").append(LocalDate.now().format(dateFormatter)).append("\n\n");
        double cashTotal = calculateCategoryAndItemTotals(reportContent);
        reportContent.append("Total Amount of Cash : RM ").append(String.format("%.2f", cashTotal)).append("\n");

        return reportContent.toString();
    }

    private double calculateCategoryAndItemTotals(StringBuilder reportContent) {
        double cashTotal = 0.0;
        LinkedList<String> categories = new LinkedList<>();
        LinkedList<String> items = new LinkedList<>();
        LinkedList<Double> categoryAmounts = new LinkedList<>();
        LinkedList<Double> itemAmounts = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            String category = donation.getItemCategory().trim();
            String item = donation.getItem().trim();
            double amount = (category.equalsIgnoreCase("Cash")) ? donation.getCashAmount() : donation.getAmount();
            if (category.equalsIgnoreCase("Cash")) {
                cashTotal += amount;
            } else {
                int categoryIndex = categories.indexOf(category);
                if (categoryIndex == -1) {
                    categories.add(category);
                    categoryAmounts.add(amount);
                } else {
                    double updatedAmount = categoryAmounts.getEntry(categoryIndex) + amount;
                    categoryAmounts.set(categoryIndex, updatedAmount);
                }
                String itemKey = category + ": " + item;
                int itemIndex = items.indexOf(itemKey);
                if (itemIndex == -1) {
                    items.add(itemKey);
                    itemAmounts.add(amount);
                } else {
                    double updatedItemAmount = itemAmounts.getEntry(itemIndex) + amount;
                    itemAmounts.set(itemIndex, updatedItemAmount);
                }
            }
        }
        for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
            String category = categories.getEntry(i);
            double totalAmount = categoryAmounts.getEntry(i);

            if (!category.equalsIgnoreCase("Cash")) {
                reportContent.append("Total ").append(category).append(" : ").append((int) totalAmount).append("\n");
                for (int j = 1; j <= items.getNumberOfEntries(); j++) {
                    String itemKey = items.getEntry(j);
                    if (itemKey.startsWith(category + ": ")) {
                        double itemTotal = itemAmounts.getEntry(j);
                        String itemName = itemKey.substring(category.length() + 2);
                        reportContent.append("-> Total ").append(itemName).append(" : ").append((int) itemTotal).append("\n");
                    }
                }
                reportContent.append("--------------------------------------\n");
            }
        }

        return cashTotal;
    }

    public String generateDetailedReport() {
        StringBuilder report = new StringBuilder();
        report.append("***** Detailed Report *****\n");
        report.append("Date Generated: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n\n");
        report.append(String.format("%-4s | %-12s | %-12s | %-13s | %-18s | %-16s | %-10s | %-10s\n", 
                                "No.", "Donation ID", "Donor ID", "Donation Date", "Donation Type", "Item", "Quantity", "Cash Amount (RM)"));
        report.append("--------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            report.append(String.format("%4d. | %-12s | %-12s | %-13s | %-18s | %-16s | %-10s | %-10.2f\n", 
                                                i, 
                                                donation.getDonationID(), 
                                                donation.getDonorID(), 
                                                donation.getDonationDate(), 
                                                donation.getItemCategory(), 
                                                donation.getItem(), 
                                                donation.getAmount(), 
                                                donation.getCashAmount()));
        }
        report.append("--------------------------------------------------------------------------------------------------------------------------\n");
        return report.toString();
    }
}
