/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import boundary.*;
import dao.*;
import java.io.*;
import utility.MessageUI;
import java.time.LocalDate;

/**
 *
 * @author SCSM11
 */
public class DonationDistribution {

    private ListInterface<Donation> donationList = new LinkedList<>();
    private DonationDAO donationDAO = new DonationDAO();
    private ListInterface<Donee> doneeList = new LinkedList<>();
    private ListInterface<Distribution> distributeList = new LinkedList<>();
    private ListInterface<KeyValuePair> doneeLocationList = new LinkedList<>(); // Use LinkedList of KeyValuePair
    private MapInterface<String, Distribution> distributeMap = new HashMap<>();
    private MapInterface<String, Double> categoryTotals = new HashMap<>();
    private MapInterface<String, Double> itemTotals = new HashMap<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private TempDAO tempDAO = new TempDAO();
    private DistributionDAO distributeDAO = new DistributionDAO();
    private DonationDistributionUI distributeUI = new DonationDistributionUI();
    private DonationManagement donation = new DonationManagement();
    private int lastDistributionNumber = 0;
    private int quantity = 0;
    private double amount = 0.0;
    private double cashTotal;
    private double itemTotal;

    public DonationDistribution() {
        this.tempDAO.createFileIfNotExists();
        File file = new File("DonationDistribution.txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File not found. A new file has been created.");
                } else {
                    System.out.println("Failed to create a new file.");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
        doneeList = doneeDAO.retrieveFromFile();
        distributeList = distributeDAO.retrieveFromFile();
        donationList = donationDAO.loadDonationsFromFile();
        
        loadDoneeLocations();
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution distribution = distributeList.getEntry(i);
            distributeMap.put(distribution.getDistributionID(), distribution); // Populate HashMap
            updateLastDistributionNumber(distribution.getDistributionID());
        }
        
        // Initialize item totals from the donation list
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            String category = donation.getItemCategory();
            String item = donation.getItem();
            double amount = category.equalsIgnoreCase("Cash") ? donation.getCashAmount() : donation.getAmount();

            String key = (category + ": " + item).toUpperCase(); // Cash : cash

            // Aggregate the totals for each key
            itemTotals.put(key, itemTotals.getOrDefault(key, 0.0) + amount);
        }

        // Subtract distributed totals from the donation totals
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution distribution = distributeList.getEntry(i);
            String category = distribution.getCategory();
            String item = distribution.getItemName();
            double amount = distribution.getAmount();
            int quantity = distribution.getQuantity(); 
            String key = (category + ": " + item).toUpperCase();

            // Handle cash category separately
            if (category.equalsIgnoreCase("Cash")) {
                itemTotals.put(key, itemTotals.getOrDefault(key, 0.0) - amount);
            } else {
                // Subtract the quantity for non-cash items
                if (itemTotals.containsKey(key)) {
                    double currentTotal = itemTotals.get(key);
                    itemTotals.put(key, currentTotal - quantity); // Subtract the quantity
                } else {
                    System.out.println("Error: Distribution item not found in donation totals.");
                }
                distribution.setAmount(0.0);
            }
        }
        double remainingCashTotal = itemTotals.getOrDefault("CASH: CASH", 0.0);
        tempDAO.saveTotals(itemTotals, remainingCashTotal);
    }

    public void runDonationDistribution() {
        int choice = -1;
        do {
            choice = distributeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewDistribute();
                    distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 2:
                    updateDistribute();
                    break;
                case 3:
                    removeDistribute();
                    distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 4:
                    trackDistribute();
                    break;
                case 5:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    choice = -1;
            }
        } while (choice != 0);
    }

    public void addNewDistribute() {
        String newDistributionID = generateNextDistributionID();
        Distribution newDistribute = inputDistributionDetails();
        newDistribute.setDistributionID(newDistributionID);
        distributeList.add(newDistribute);
        distributeUI.listDistribute(newDistribute);
        MessageUI.pressAnyKeyToContinue();
        distributeMap.put(newDistribute.getDistributionID(), newDistribute);
        distributeDAO.saveToFile(getAllDistribute());
    }

    public void removeDistribute() {
        String distributionID = distributeUI.inputDistributionID();
        boolean isRemoved = false;

        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution currentDistribution = distributeList.getEntry(i);

            if (currentDistribution.getDistributionID().equals(distributionID)) {
                distributeList.remove(i);
                isRemoved = true;
                break;
            }
        }

        if (isRemoved) {
            distributeDAO.saveToFile(getAllDistribute());
            System.out.println("Distribution removed successfully.");
        } else {
            System.out.println("Distribution ID not found.");
        }
    }

    public void updateDistribute() {
        String distributionID = distributeUI.inputDistributionID();
        int index = findDistributionIndexById(distributionID);
        if (index != -1) {
            Distribution updatedDistribute = inputDistributionDetails();
            distributeList.replace(index, updatedDistribute);
            distributeDAO.saveToFile(getAllDistribute());

            System.out.println("Distribution with ID " + distributionID + " has been updated successfully.");
        } else {
            System.out.println("Distribution with ID " + distributionID + " not found.");
        }
    }
    
    private void loadDoneeLocations() {
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            doneeLocationList.add(new KeyValuePair(donee.getDoneeID(), donee.getDoneeLocation()));
        }
    }
    
    private void updateLastDistributionNumber(String distributionID) {
        String numberPart = distributionID.substring(2); // Extract the numeric part (e.g., "001" from "DE001")
        int number = Integer.parseInt(numberPart);
        if (number > lastDistributionNumber) {
            lastDistributionNumber = number;
        }
    }

    private String getDoneeLocationByID(String doneeID) {
        for (int i = 1; i <= doneeLocationList.getNumberOfEntries(); i++) {
            KeyValuePair<String, String> pair = doneeLocationList.getEntry(i);
            if (pair.getKey().equalsIgnoreCase(doneeID)) {
                return pair.getValue();
            }
        }
        return "Unknown";
    }

    private int findDistributionIndexById(String distributionID) {
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution currentDistribute = distributeList.getEntry(i);
            if (currentDistribute.getDistributionID().equalsIgnoreCase(distributionID)) {
                return i;
            }
        }
        return -1;
    }

    public void trackDistribute() {
        int choices = -1;
        do {
            choices = distributeUI.getTrackMenuChoice();
            switch (choices) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    trackByDoneeID();
                    break;
                case 2:
                    trackByDistributionDate();
                    break;
                case 3:
                    trackByCategory();
                    break;
                case 4:
                    trackByStatus();
                    break;
                case 5:
                    trackByLocation();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    choices = -1;
            }
        } while (choices != 0);
    }

    private void trackByCriteria(String criteria, String type) {
        StringBuilder result = new StringBuilder();
        String title = "";
        boolean includeDoneeID = false;

        switch (type) {
            case "DoneeID":
                title = "Donation Details for Donee ID: " + criteria;
                break;
            case "DistributionDate":
                title = "Donation Details for Distribution Date: " + criteria;
                break;
            case "Category":
                title = "Donation Details for Category: " + criteria;
                break;
            case "Status":
                title = "Donation Details for Status: " + criteria;
                break;
            case "Location":
                title = "Donation Details for Location: " + criteria;
                includeDoneeID = true; // Set flag to include Donee ID
                break;
            default:
                System.out.println("Unknown type.");
                return;
        }

        result.append(DonationDistributionUI.formatHeader(title, includeDoneeID));
        boolean hasMatchingRecords = false;

        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution dist = distributeList.getEntry(i);

            boolean match = false;
            switch (type) {
                case "DoneeID":
                    match = dist.getDoneeID().equalsIgnoreCase(criteria);
                    break;
                case "DistributionDate":
                    match = dist.getDistributionDate().isEqual(LocalDate.parse(criteria));
                    break;
                case "Category":
                    match = dist.getCategory().equalsIgnoreCase(criteria);
                    break;
                case "Status":
                    match = dist.getStatus().equalsIgnoreCase(criteria);
                    break;
                case "Location":
                    String doneeLocation = getDoneeLocationByID(dist.getDoneeID());
                    if (doneeLocation.equalsIgnoreCase(criteria)) {
                        match = true;
                    }
                    break;
            }

            if (match) {
                hasMatchingRecords = true;

                if (includeDoneeID) {
                    result.append(String.format("%-18s%-25s%-15s%-15s%-15s%-25s%-5s\n",
                            dist.getItemName(), dist.getCategory(), dist.getQuantity(), dist.getAmount(),
                            dist.getStatus(), dist.getDistributionDate(), dist.getDoneeID()));
                } else {
                    result.append(String.format("%-18s%-25s%-15s%-15s%-15s%-25s%-5s\n",
                            dist.getItemName(), dist.getCategory(), dist.getQuantity(), dist.getAmount(),
                            dist.getStatus(), dist.getDistributionDate(), getDoneeLocationByID(dist.getDoneeID()).toUpperCase()));
                }
            }
        }

        if (!hasMatchingRecords) {
            result.append("No matching records found.\n");
        }

        distributeUI.listAllDistribute(result.toString());
    }

    private void trackByDoneeID() {
        String doneeID = distributeUI.inputDoneeID();
        trackByCriteria(doneeID, "DoneeID");
    }

    private void trackByDistributionDate() {
        LocalDate date = distributeUI.inputDistributionDate();
        trackByCriteria(date.toString(), "DistributionDate");
    }

    private void trackByCategory() {
        String category = distributeUI.inputDonationCategories();
        trackByCriteria(category, "Category");
    }

    private void trackByStatus() {
        String status = distributeUI.inputStatus();
        trackByCriteria(status, "Status");
    }

    private void trackByLocation() {
        String location = distributeUI.inputLocation();
        trackByCriteria(location, "Location");
    }

    public void generateReport() {
        int totalPendingItems = 0;
        int totalDeliveredItems = 0;
        int totalReceivedItems = 0;
        double totalPendingCash = 0.0;
        double totalDeliveredCash = 0.0;
        double totalReceivedCash = 0.0;
        double totalDistributedCash = 0.0;
        int totalItemsDistributed = 0;
        int highestQuantity = 0;
        String highestCategory = "";
        String highestItemLocation = "";
        int highestItemQuantity = 0;
        String highestCashLocation = "";
        double highestCashAmount = 0.0;

        ListInterface<KeyValuePair<String, Integer>> locationItemCounts = new LinkedList<>();
        ListInterface<KeyValuePair<String, Double>> locationCashCounts = new LinkedList<>();

        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution dist = distributeList.getEntry(i);
            boolean isCash = dist.getCategory().equalsIgnoreCase("cash");

            switch (dist.getStatus().toLowerCase()) {
                case "pending":
                    if (isCash) {
                        totalPendingCash += dist.getAmount();
                    } else {
                        totalPendingItems += dist.getQuantity();
                    }
                    break;
                case "delivered":
                    if (isCash) {
                        totalDeliveredCash += dist.getAmount();
                    } else {
                        totalDeliveredItems += dist.getQuantity();
                    }
                    break;
                case "received":
                    if (isCash) {
                        totalReceivedCash += dist.getAmount();
                    } else {
                        totalReceivedItems += dist.getQuantity();
                    }
                    break;
            }

            if (isCash) {
                totalDistributedCash += dist.getAmount();
            } else {
                totalItemsDistributed += dist.getQuantity();
            }

            String location = getDoneeLocationByID(dist.getDoneeID());
            if (isCash) {
                updateLocationCash(locationCashCounts, location, dist.getAmount());
            } else {
                updateLocationItems(locationItemCounts, location, dist.getQuantity());
            }
            // Track highest quantity and category
            if (dist.getQuantity() > highestQuantity) {
                highestQuantity = dist.getQuantity();
                highestCategory = dist.getCategory();
            }
        }
        // Find the location with the highest item quantity
        for (int i = 1; i <= locationItemCounts.getNumberOfEntries(); i++) {
            KeyValuePair<String, Integer> itemEntry = locationItemCounts.getEntry(i);
            if (itemEntry.getValue() > highestItemQuantity) {
                highestItemLocation = itemEntry.getKey();
                highestItemQuantity = itemEntry.getValue();
            }
        }
        // Find the location with the highest cash amount
        for (int i = 1; i <= locationCashCounts.getNumberOfEntries(); i++) {
            KeyValuePair<String, Double> cashEntry = locationCashCounts.getEntry(i);
            if (cashEntry.getValue() > highestCashAmount) {
                highestCashLocation = cashEntry.getKey();
                highestCashAmount = cashEntry.getValue();
            }
        }

        distributeUI.displaySummaryReport(
                totalPendingItems, totalDeliveredItems, totalReceivedItems,
                totalPendingCash, totalDeliveredCash, totalReceivedCash,
                totalDistributedCash, totalItemsDistributed, highestQuantity,
                highestCategory, highestItemLocation, highestItemQuantity,
                highestCashLocation, highestCashAmount
        );
    }

    private void updateLocationItems(ListInterface<KeyValuePair<String, Integer>> locationItemCounts, String location, int quantity) {
        boolean locationFound = false;

        for (int i = 1; i <= locationItemCounts.getNumberOfEntries(); i++) {
            KeyValuePair<String, Integer> entry = locationItemCounts.getEntry(i);
            if (entry.getKey().equalsIgnoreCase(location)) {
                entry.setValue(entry.getValue() + quantity);
                locationFound = true;
                break;
            }
        }

        if (!locationFound) {
            locationItemCounts.add(new KeyValuePair<>(location, quantity));
        }
    }

    private void updateLocationCash(ListInterface<KeyValuePair<String, Double>> locationCashCounts, String location, double amount) {
        boolean locationFound = false;

        for (int i = 1; i <= locationCashCounts.getNumberOfEntries(); i++) {
            KeyValuePair<String, Double> entry = locationCashCounts.getEntry(i);
            if (entry.getKey().equalsIgnoreCase(location)) {
                entry.setValue(entry.getValue() + amount);
                locationFound = true;
                break;
            }
        }
        if (!locationFound) {
            locationCashCounts.add(new KeyValuePair<>(location, amount));
        }
    }

    private boolean isCategoryValid(String category, LinkedList<Donation> donationList) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            if (donationList.getEntry(i).getItemCategory().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    private boolean isItemValid(String item, String category, LinkedList<Donation> donationList) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getItemCategory().equalsIgnoreCase(category) && donation.getItem().equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoneeIDValid(String doneeID, ListInterface<Donee> doneeList) {
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
                return true;
            }
        }
        return false;
    }

    private ListInterface<Donee> loadDoneeData() {
        DoneeDAO doneeDAO = new DoneeDAO();
        return doneeDAO.retrieveFromFile();
    }

    private LinkedList<Donation> loadDonationData() {
        DonationDAO donationDAO = new DonationDAO();
        return donationDAO.loadDonationsFromFile();
    }

    public Distribution inputDistributionDetails() {
        LinkedList<Donation> donationList = loadDonationData();
        ListInterface<Donee> doneeList = loadDoneeData();

        String category = distributeUI.inputDonationCategories();
        while (!isCategoryValid(category, donationList)) {
            System.out.println("\nInvalid Category. Please enter a category that exists in the donation file.");
            category = distributeUI.inputDonationCategories();
        }

        String itemName = distributeUI.inputItemName();
        while (!isItemValid(itemName, category, donationList)) {
            System.out.println("\nInvalid Item. Please enter an item that exists for the selected category in the donation file.");
            itemName = distributeUI.inputItemName();
        }

        if (category.equalsIgnoreCase("cash")) {
            if (!itemTotals.containsKey("CASH: CASH")) {
                itemTotals.put("CASH: CASH", 0.0);
            }

            cashTotal = itemTotals.get("CASH: CASH"); // Retrieve the current cash total
            
            do {
                amount = distributeUI.inputAmount();
                if (cashTotal == 0) {
                    System.out.print("Insufficient cash available in the donation file. Please waiting for donation.\n\n");
                   runDonationDistribution();
                }else if (amount > cashTotal ){
                   System.out.println("\nError: Insufficient cash available. Please enter a valid amount.");
                }
            } while (amount > cashTotal);
            
            cashTotal -= amount;
            itemTotals.put("CASH: CASH", cashTotal);  // Update cash total in the map
            tempDAO.saveTotals(itemTotals, cashTotal);

        } else {
            // Create the key for the item category and name
            String key = category + ": " + itemName;

            if (!itemTotals.containsKey(key)) {
                itemTotals.put(key, 0.0);
            }
            double availableQuantity = itemTotals.get(key);

            do {
                quantity = distributeUI.inputQuantity();
                if (quantity > availableQuantity) {
                    System.out.println("\nError: Insufficient quantity available. Please enter a valid quantity.");
                }
            } while (quantity > availableQuantity);

            availableQuantity -= quantity;
            itemTotals.put(key, availableQuantity);  
            tempDAO.saveTotals(itemTotals, availableQuantity);
        }

        String doneeID = distributeUI.inputDoneeID();
        while (!isDoneeIDValid(doneeID, doneeList)) {
            System.out.println("\nInvalid ID. Please enter a Donee ID from the donee file.");
            doneeID = distributeUI.inputDoneeID();
        }

        String status = distributeUI.inputStatus();

        LocalDate distributionDate = distributeUI.inputDistributionDate();
        LocalDate currentDate = LocalDate.now(); // Get the current date
        while (distributionDate.isBefore(currentDate)) {
            System.out.println("\nInvalid Date. Please enter a valid date.");
            distributionDate = distributeUI.inputDistributionDate();
        }

        return new Distribution("DD001", category, itemName, quantity, amount, doneeID, status, distributionDate);
    }

    public String getAllDistribute() {
        String outputStr = "";
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            outputStr += distributeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    private String generateNextDistributionID() {
        lastDistributionNumber++;
        return String.format("DD%03d", lastDistributionNumber);
    }

    public static void main(String[] args) {
        DonationDistribution distribute = new DonationDistribution();
        distribute.runDonationDistribution();

    }
}
