/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import adt.*;
import entity.*;
import boundary.*;
import dao.DistributionDAO;
import dao.DoneeDAO;
import utility.MessageUI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SCSM11
 */
public class DonationDistribution {
    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeDAO doneeDAO = new DoneeDAO(); 
    private Map<String, String> doneeLocationCache = new HashMap<>();
    private ListInterface<Distribution> distributeList = new LinkedList<>();
    private DistributionDAO distributeDAO = new DistributionDAO();
    private DonationDistributionUI distributeUI = new DonationDistributionUI();
    
    public DonationDistribution(){
        doneeList = doneeDAO.retrieveFromFile();
        distributeList = distributeDAO.retrieveFromFile();
        loadDoneeLocations();
    }

    private void loadDoneeLocations() {
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            doneeLocationCache.put(donee.getDoneeID(), donee.getDoneeLocation());
        }
    }

    public void runDonationDistribution() {
        int choice = 0;
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
                    //distributeDAO.retrieveFromFile();
                    break;
                case 3:
                    removeDistribute();
                    distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 4:
                    trackDistribute();
                    //distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 5:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    
    public void addNewDistribute(){
        Distribution newDistribute = distributeUI.inputDistributionDetails();
        distributeList.add(newDistribute);
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
        }else {
            System.out.println("Distribution ID not found.");
        }
    }
    
    public void updateDistribute() {
        String distributionID = distributeUI.inputDistributionID();
        int index = findDistributionIndexById(distributionID);
        if (index != -1) {
            Distribution updatedDistribute = distributeUI.inputDistributionDetails();
            distributeList.replace(index, updatedDistribute);
            distributeDAO.saveToFile(getAllDistribute());

            System.out.println("Distribution with ID " + distributionID + " has been updated successfully.");
        }else {
            System.out.println("Distribution with ID " + distributionID + " not found.");
        }
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
        int choices = 0;
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
            }
        } while (choices != 0); 
        
    }

   private void trackByCriteria(String criteria, String type) {
        StringBuilder result = new StringBuilder();
        String title = "";
        boolean includeDoneeID = false;

        // Set the title and header based on the type of criteria
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

        // Iterate through the distributeList
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
                    for (Map.Entry<String, String> entry : doneeLocationCache.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(criteria)) {
                            if (dist.getDoneeID().equalsIgnoreCase(entry.getKey())) {
                                match = true;
                                break;
                            }
                        }
                    }
                    break;
            }

            if (match) {
                hasMatchingRecords = true;

                if (includeDoneeID) {
                    result.append(String.format("%-20s%-15s%-15d%-15.2f%-20s%-20s%-20s\n",
                        dist.getItemName(),dist.getCategory(),dist.getQuantity(),dist.getAmount(),dist.getStatus(),dist.getDistributionDate(),dist.getDoneeID())); 
                }else {
                    result.append(String.format("%-20s%-15s%-15d%-15.2f%-20s%-20s%-50s\n",
                        dist.getItemName(),dist.getCategory(),dist.getQuantity(),dist.getAmount(),dist.getStatus(),dist.getDistributionDate(),
                        doneeLocationCache.getOrDefault(dist.getDoneeID(), "Unknown"))); 
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

        Map<String, Integer> locationItemCounts = new HashMap<>();
        Map<String, Double> locationCashCounts = new HashMap<>();

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

            // Track total distributed items and cash across all statuses
            if (isCash) {
                totalDistributedCash += dist.getAmount(); 
            } else {
                totalItemsDistributed += dist.getQuantity(); 
            }

            // Track item and cash distributions by location
            String location = doneeLocationCache.getOrDefault(dist.getDoneeID(), "Unknown");
            locationItemCounts.put(location, locationItemCounts.getOrDefault(location, 0) + dist.getQuantity());
            locationCashCounts.put(location, locationCashCounts.getOrDefault(location, 0.0) + dist.getAmount());

            if (dist.getQuantity() > highestQuantity) {
                highestQuantity = dist.getQuantity();
                highestCategory = dist.getCategory();
            }
        }

        // Find highest quantity and cash location
        for (Map.Entry<String, Integer> itemEntry : locationItemCounts.entrySet()) {
            String location = itemEntry.getKey();
            int itemCount = itemEntry.getValue();

            if (itemCount > highestItemQuantity) {
                highestItemLocation = location;
                highestItemQuantity = itemCount;
            }
        }

        for (Map.Entry<String, Double> cashEntry : locationCashCounts.entrySet()) {
            String location = cashEntry.getKey();
            double cashAmount = cashEntry.getValue();

            if (cashAmount > highestCashAmount) {
                highestCashLocation = location;
                highestCashAmount = cashAmount;
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
    
    private boolean isCash(Distribution dist) {
        return dist.getCategory().equalsIgnoreCase("cash");
    }

    public String getAllDistribute() {
        String outputStr = "";
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            outputStr += distributeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public static void main(String[] args) {
        DonationDistribution distribute = new DonationDistribution();
        distribute.runDonationDistribution();
  }
}

















