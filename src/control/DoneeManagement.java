/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dao.*;
import adt.*;
import boundary.*;
import utility.*;
import entity.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Kow Yun Shen
 */
public class DoneeManagement {

    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeManagementUI doneeUI = new DoneeManagementUI();
    private ListInterface<Distribution> distributeList = new LinkedList<>();
    private DistributionDAO distributeDAO = new DistributionDAO();
    private MapInterface<String, Donee> doneeMap = new HashMap<>();
    private MapInterface<String, ListInterface<Distribution>> donationMap = new HashMap<>();
    private Filter<Donee> filterDonee = new Filter<>();
    private Filter<Distribution> filterDonation = new Filter<>();
    private int lastDoneeNumber = 0;

    public DoneeManagement() {
        File file = new File("Donee.txt");
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
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            doneeMap.put(donee.getDoneeID(), donee); // Populate HashMap
            updateLastDoneeNumber(donee.getDoneeID());
        }
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution distribution = distributeList.getEntry(i);
            String doneeID = distribution.getDoneeID();
            if (!donationMap.containsKey(doneeID)) {
                donationMap.put(doneeID, new LinkedList<Distribution>());
            }
            donationMap.get(doneeID).add(distribution); // Populate HashMap for Donations
        }
    }

    public void runDoneeManagement() {
        int choice = 0;
        do {
            choice = doneeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    driver driver = new driver();
                    driver.runDriver();
                    break;
                case 1:
                    addNewDonee();//done
                    break;
                case 2:
                    updateDoneeDetails();//done
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 3:
                    searchDoneeDetails();//done
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 4:
                    listDoneeDonation();//done
                    break;
                case 5:
                    filterDonees();
                    break;
                case 6:
                    removeDonee();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 7:
                    generateSummaryReport();
                    break;
                case 8:
                    generateDetailReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addNewDonee() {
        String newDoneeID = generateNextDoneeID();
        Donee newDonee = doneeUI.inputDoneeDetails();
        newDonee.setDoneeID(newDoneeID);
        doneeList.add(newDonee);
        doneeUI.listDonee(newDonee);
        MessageUI.pressAnyKeyToContinue();
        doneeMap.put(newDonee.getDoneeID(), newDonee);
        doneeDAO.saveToFile(getAllDonee());
    }

    public void removeDonee() {
        String doneeID = doneeUI.inputDoneeID();
        boolean result = removeDoneeById(doneeID);
    }

    public boolean removeDoneeById(String doneeID) {
        boolean removed = false;

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
                doneeList.remove(i);
                doneeMap.remove(doneeID);
                removed = true;
                break;
            }
        }

        if (removed) {
            doneeDAO.saveToFile(getAllDonee());
            System.out.println("Donee with ID " + doneeID + " has been removed.");
        } else {
            System.out.println("Donee with ID " + doneeID + " not found.");
        }

        return removed;
    }

    public void updateDoneeDetails() {
        String doneeID = doneeUI.inputDoneeID();
        boolean result = updateDoneeDetails(doneeID);
    }

    public boolean updateDoneeDetails(String doneeID) {
        boolean updated = false;
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
                doneeUI.updateDonee(donee); // Prompt user for new details
                System.out.println("Donee Infomation Updated.");
                doneeUI.listDonee(donee);
                updated = true;
                break;
            }
        }

        if (updated) {
            doneeDAO.saveToFile(getAllDonee());
            System.out.print("");
        } else {
            System.out.println("Donee with ID " + doneeID + " not found.");
        }
        return updated;
    }

    public Donee searchDoneeByID(String doneeID) {
        return doneeMap.get(doneeID);
    }
    
    public void searchDoneeDetails() {
        String doneeID = doneeUI.inputDoneeID();
        Donee foundDonee = searchDoneeByID(doneeID.toUpperCase());
        if (foundDonee != null) {
            doneeUI.listDonee(foundDonee);
        } else {
            System.out.println("Donee not found.");
        }
    }

    public void listDoneeDonation() {
        String doneeID = doneeUI.inputDoneeID();
        listDonationsByDoneeID(doneeID);
    }

    public void listDonationsByDoneeID(String doneeID) {
        ListInterface<Distribution> donations = donationMap.get(doneeID);
        if (donations != null && !donations.isEmpty()) {
            System.out.println("\nDonation for " + doneeMap.get(doneeID).getDoneeName());
            doneeUI.DoneeDonationHeader();
            double totalDonations = 0;
            int totalGoods = 0;
            double totalCash = 0;
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Distribution donation = donations.getEntry(i);
                System.out.printf("%-15s %-20s %-15s %-10d %-10.2f %-10s %-20s\n",
                        donation.getDistributionID(),
                        donation.getItemName(),
                        donation.getCategory(),
                        donation.getQuantity(),
                        donation.getAmount(),
                        donation.getStatus(),
                        donation.getDistributionDate().toString());
                totalDonations++;
                totalGoods += donation.getQuantity();
                totalCash += donation.getAmount();
            }
            System.out.println("*********************************************************************************************************");
            System.out.println("Total Donations: " + totalDonations);
            System.out.println("Total Donated Goods: " + totalGoods);
            System.out.println("Total Donated Cash: " + String.format("%.2f", totalCash));
            System.out.println("**********************************");
            MessageUI.pressAnyKeyToContinue();
        } else {
            System.out.println("No donations found for Donee ID: " + doneeID);
        }
    }


    public String getAllDonee() {
        String outputStr = "";
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            outputStr += doneeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void filterDonees() {
        String doneeID;
        int filterChoice = doneeUI.getFilterChoice(); // Prompt user to choose filter type
        ListInterface<Donee> filteredDonees;
        ListInterface<Distribution> filteredInfoByDoneeID = null;
        switch (filterChoice) {
            case 1:
                String doneeType = doneeUI.inputDoneeType();
                filteredDonees = filterDonee.filterByType(doneeList, doneeType);
                System.out.println("Donation for " + doneeType + " Category");
                doneeUI.displayFilteredDonees(filteredDonees);
                break;
            case 2:
                while (true) {
                    doneeID = doneeUI.inputDoneeID();
                    if (doneeUI.isDoneeIDValid(doneeID, doneeList)) {
                        LocalDate startDate = doneeUI.inputStartDate();
                        LocalDate endDate = doneeUI.inputEndDate();
                        filteredInfoByDoneeID = filterDonation.filterByDateAndDoneeID(distributeList, startDate, endDate, doneeID);
                        System.out.println("\nDonation for " + doneeMap.get(doneeID).getDoneeName() + " between " + startDate + " to " + endDate);
                        doneeUI.displayFilteredDoneesByDoneeID(filteredInfoByDoneeID);
                        break;
                    } else {
                        System.err.println("Donee ID not found.");
                    }
                }
                break;
            case 3:
                while (true) {
                    doneeID = doneeUI.inputDoneeID();
                    if (doneeUI.isDoneeIDValid(doneeID, doneeList)) {
                        double minAmount = doneeUI.inputMinAmount();
                        double maxAmount = doneeUI.inputMaxAmount();
                        filteredInfoByDoneeID = filterDonation.filterByAmountAndDoneeID(distributeList, minAmount, maxAmount, doneeID);
                        System.out.println("\nDonation for " + doneeMap.get(doneeID).getDoneeName() + " between " + minAmount + " to " + maxAmount);
                        doneeUI.displayFilteredDoneesByDoneeID(filteredInfoByDoneeID);
                        break;
                    } else {
                        System.err.println("Donee ID not found.");
                    }
                }
                break;
            case 4:
                while (true) {
                    String doneeLocation = doneeUI.inputDoneeLocation();
                    if (doneeUI.isLocationValid(doneeLocation, doneeList)) {
                        filteredDonees = filterDonee.filterByLocation(doneeList, doneeLocation);
                        doneeUI.displayFilteredDonees(filteredDonees);
                        break;
                    } else {
                        System.err.println("Donee ID not found.");
                    }
                }
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                filterDonees();
        }

    }

    public void generateSummaryReport() {
        int totalDonees = doneeList.getNumberOfEntries();
        int totalFamilies = 0;
        int totalOrganizations = 0;
        int totalIndividuals = 0;
        ListInterface<String> uniqueLocations = new LinkedList<>();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            switch (donee.getDoneeType()) {
                case "---FAMILY---":
                    totalFamilies++;
                    break;
                case "ORGANISATION":
                    totalOrganizations++;
                    break;
                case "-INDIVIDUAL-":
                    totalIndividuals++;
                    break;
            }
            String location = donee.getDoneeLocation();
            boolean locationExists = false;
            for (int j = 1; j <= uniqueLocations.getNumberOfEntries(); j++) {
                if (uniqueLocations.getEntry(j).equalsIgnoreCase(location)) {
                    locationExists = true;
                    break;
                }
            }
            if (!locationExists) {
                uniqueLocations.add(location);
            }
        }
        // Display the summary report
        System.out.println("=== Donee Summary Report ===");
        System.out.println("Total Number of Donees: " + totalDonees);
        System.out.println("\nCategory Breakdown:");
        System.out.println("Total Number of Families: " + totalFamilies);
        System.out.println("Total Number of Organizations: " + totalOrganizations);
        System.out.println("Total Number of Individuals: " + totalIndividuals);

        // Display each location and its count
        System.out.println("\nLocation Breakdown:");
        System.out.println("Total Number of Locations: " + uniqueLocations.getNumberOfEntries());
        for (int i = 1; i <= uniqueLocations.getNumberOfEntries(); i++) {
            String location = uniqueLocations.getEntry(i);
            int locationCount = 0;
            for (int j = 1; j <= doneeList.getNumberOfEntries(); j++) {
                if (doneeList.getEntry(j).getDoneeLocation().equalsIgnoreCase(location)) {
                    locationCount++;
                }
            }
            System.out.println(location + ": " + locationCount);
        }
        System.out.println("============================");
        MessageUI.pressAnyKeyToContinue();
    }

    public void generateDetailReport() {
        System.out.printf("%50s\n", "DETAILED REPORT");
        doneeUI.displayFilteredDonees(doneeList);
    }

    private void updateLastDoneeNumber(String doneeID) {
        String numberPart = doneeID.substring(2); // Extract the numeric part (e.g., "001" from "DE001")
        int number = Integer.parseInt(numberPart);
        if (number > lastDoneeNumber) {
            lastDoneeNumber = number;
        }
    }

    private String generateNextDoneeID() {
        lastDoneeNumber++;
        return String.format("DE%03d", lastDoneeNumber); // Format as DE001, DE002, etc.
    }

    public static void main(String[] args) {
        DoneeManagement doneeMaintenance = new DoneeManagement();
        doneeMaintenance.runDoneeManagement();
    }
}
