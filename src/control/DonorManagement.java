/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import boundary.DonorManagementUI;
import dao.*;
import java.time.LocalDate;
import java.util.Comparator;
import utility.*;

/**
 *
 * @author Ng Yin Xuan
 */
public class DonorManagement {

    private ListInterface<Donor> donorList = new LinkedList<>();
    private ListInterface<Donation> donationList = new LinkedList<>();
    private DonorDAO donorDAO = new DonorDAO();
    private DonationDAO donationDAO = new DonationDAO();
    private DonorManagementUI donorUI = new DonorManagementUI();
    private MapInterface<String, LinkedList<Donor>> categorisedDonors = new HashMap<>();
    private MapInterface<String, Donor> donorMap = new HashMap<>();
    private MapInterface<String, ListInterface<Donation>> donorDonations = new HashMap<>();
    private Filter donorFilter = new Filter<>();

    public DonorManagement() {
        donorList = donorDAO.retrieveFromFile();
        donationList = donationDAO.loadDonationsFromFile();
        categorisedDonors.put("government", new LinkedList<>());
        categorisedDonors.put("private", new LinkedList<>());
        categorisedDonors.put("public", new LinkedList<>());

        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorID = donor.getDonorID();

            // Check if the donorID already exists in the donorMap
            if (!donorMap.containsKey(donorID)) {
                donorMap.put(donorID, donor);
                donorDonations.put(donorID, new LinkedList<>());
            } else {
                System.out.println("Duplicate donor found: " + donorID + ", skipping...");
            }
        }

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);

            if (donation == null) {
                continue;
            }

            String donorID = donation.getDonorID();
            if (donorDonations.containsKey(donorID)) {
                donorDonations.get(donorID).add(donation); // Add donation to the correct donor's list
            } else {
                System.err.println("Warning: Donor with ID " + donorID + " not found in donorDonations.");
            }
        }
    }

    public void runDonorManagement() {
        donorList.sort(donorIDComparator);
        int choice = 0;
        do {
            choice = donorUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewDonor();
                    listAllDonors();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 2:
                    removeDonor();
                    listAllDonors();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 3:
                    listAllDonors();
                    updateDonorDetails();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 4:
                    searchDonorDetails();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 5:
                    listDonorsWithDonations();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 6:
                    filterDonor();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 7:
                    categoriseDonors();
                    printCategorisedDonors();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 8:
                    generateReport();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addNewDonor() {
        String newDonorID = generateNextDonorID();
        Donor newDonor = donorUI.inputDonorDetails();
        newDonor.setDonorID(newDonorID);
        donorList.add(newDonor);
        donorMap.put(newDonor.getDonorID(), newDonor);
        donorDonations.put(newDonor.getDonorID(), new LinkedList<>());
        donorDAO.saveToFile(getAllDonors());
        System.out.println("Donor added successfully.");
    }

    public String getAllDonors() {
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            outputStr += donorList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void listAllDonors() {
        donorUI.displayDonor(donorList);
    }

    // Remove a donor by donorID
    public void removeDonor() {
        String donorID = donorUI.inputDonorID();
        donorMap.remove(donorID);
        donorDonations.remove(donorID);
        boolean donorFound = false;
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            if (donorList.getEntry(i).getDonorID().equals(donorID)) {
                donorList.remove(i);
                donorFound = true;
                break;
            }
        }
        if (donorFound) {
            donorDAO.saveToFile(getAllDonors());
            System.out.println("Donor removed successfully.");
        } else {
            System.err.println("Donor ID not found.");
        }
    }

    // Update donor details referring to the donorID
    public void updateDonorDetails() {
        String donorID = donorUI.inputDonorID();
        if (!donorMap.containsKey(donorID)) {
            System.err.println("Donor ID not found.");
            updateDonorDetails();
        } else {
            Donor updatedDonor = donorUI.inputDonorDetails();
            updatedDonor.setDonorID(donorID);
            donorMap.put(donorID, updatedDonor);
            boolean donorFound = false;
            for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
                if (donorList.getEntry(i).getDonorID().equals(donorID)) {
                    donorList.replace(i, updatedDonor);
                    donorFound = true;
                    break;
                }
            }
            if (donorFound) {
                donorDAO.saveToFile(getAllDonors());
                System.out.println("Donor details updated successfully.");
            } else {
                System.err.println("Donor ID not found.");
            }
        }
        donorUI.displayUpdatedDonor(donorList, donorID);
    }

    public void searchDonorDetails() {
        String donorID = donorUI.inputDonorID();
        if (donorMap.containsKey(donorID)) {
            donorUI.displayDonorDetails(donorMap.get(donorID));
        } else {
            System.err.println("Donor not found.");
            searchDonorDetails();
        }
    }

    public void listDonorsWithDonations() {
        // Define table headers
        String header = String.format("%-10s %-20s %-15s %-20s %-10s %-15s %-10s %-20s %-15s %-10s %-15s",
                "DonorID", "Name", "Contact No", "Email",
                "Type", "Entity Type", "DonationID", "Donation Type", "Item", "Amount", "Donation Date");

        // Print table header
        System.out.println("=".repeat(header.length()));
        System.out.println(header);
        System.out.println("=".repeat(header.length()));

        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);

            // Display the donor details once
            String donorRow = String.format("%-10s %-20s %-15s %-20s %-10s %-15s",
                    donor.getDonorID(),
                    donor.getName(),
                    donor.getContactNo(),
                    donor.getEmail(),
                    donor.getType(),
                    donor.getEntityType());
            System.out.println(donorRow);

            ListInterface<Donation> donations = donorDonations.get(donor.getDonorID());
            // Display all donations for this donor
            if (!donations.isEmpty()) {
                for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                    Donation donation = donations.getEntry(j);
                    String donationRow;

                    // Format the donation details under the donor
                    if (donation.getItemCategory().equals("Cash")) {
                        donationRow = String.format("%-10s %-20s %-15s %-20s %-10s %-15s %-10s %-20s %-15s %-10.2f %-15s",
                                "", "", "", "", "", "", // Empty for donor details
                                donation.getDonationID(),
                                donation.getItemCategory(),
                                donation.getItem(),
                                donation.getCashAmount(),
                                donation.getDonationDate().toString());
                    } else {
                        donationRow = String.format("%-10s %-20s %-15s %-20s %-10s %-15s %-10s %-20s %-15s %-10s %-15s",
                                "", "", "", "", "", "", // Empty for donor details
                                donation.getDonationID(),
                                donation.getItemCategory(),
                                donation.getItem(),
                                donation.getAmount(),
                                donation.getDonationDate().toString());
                    }
                    System.out.println(donationRow);
                }
            } else {
                System.out.printf("%-10s %-20s %-15s %-20s %-10s %-15s %s", "", "", "", "", "", "", "No donations found for this donor.");
            }

            System.out.println();
        }
        System.out.println("=".repeat(header.length()));
    }

    // Filter donor by type
    public void filterDonor() {
        int filterChoice = donorUI.getFilterChoice(); // Prompt user to choose filter type
        ListInterface<Donor> filteredDonors;

        switch (filterChoice) {
            case 1:
                String donorType = donorUI.inputDonorType();
                filteredDonors = donorFilter.filterByType(donorList, donorType);
                break;
            case 2:
                String entityType = donorUI.inputEntityType();
                filteredDonors = donorFilter.filterByEntityType(donorList, entityType);
                break;
            case 3:
                LocalDate startDate = donorUI.inputStartDate();
                LocalDate endDate = donorUI.inputEndDate();
                System.out.println();
                filteredDonors = donorFilter.filterByDate(donorMap, donorDonations, startDate, endDate);

                break;
            case 4:
                double minAmount = donorUI.inputMinAmount();
                double maxAmount = donorUI.inputMaxAmount();
                filteredDonors = donorFilter.filterByAmountRange(donorMap, donorDonations, minAmount, maxAmount);
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                return;
        }

        donorUI.displayDonor(filteredDonors);
    }

    public MapInterface<String, LinkedList<Donor>> categoriseDonors() {
        ListInterface<Donor> donors = donorList;
        for (int i = 1; i <= donors.getNumberOfEntries(); i++) {
            Donor donor = donors.getEntry(i);

            String type = donor.getType().toLowerCase();
            if (categorisedDonors.containsKey(type)) {
                LinkedList<Donor> typeList = categorisedDonors.get(type);
                typeList.add(donor);
            } else {
                System.out.println("Invalid Type.");
            }
        }
        return categorisedDonors;
    }

    public void printCategorisedDonors() {
        for (String type : new String[]{"government", "private", "public"}) {
            switch (type) {
                case "government":
                    System.out.println("\nCategory: Government");
                    break;
                case "private":
                    System.out.println("Category: Private");
                    break;
                default:
                    System.out.println("Category: Public");
                    break;
            }

            LinkedList<Donor> donors = categorisedDonors.get(type);
            if (!donors.isEmpty()) {
                donorUI.displayDonor(donors);
            } else {
                System.out.println("No donors in this category.");
            }
            System.out.println();
        }
    }

    public void generateReport() {
        int choice = donorUI.getReportChoice();
        if (choice == 1) {
            generateDonorTypeSummaryReport();
        } else if (choice == 2) {
            int n = donorUI.inputTopValue();
            generateTopDonorsSummaryReport(n);
        }
    }

    public void generateDonorTypeSummaryReport() {
        int governmentCount = 0, privateCount = 0, publicCount = 0, totalCount = 0;
        int govDonations = 0, privateDonations = 0, publicDonations = 0, totalDonations = 0;
        double governmentTotal = 0, privateTotal = 0, publicTotal = 0, total = 0;
        double govPercentage = 0, privatePercentage = 0, publicPercentage = 0;
        double governmentAverage = 0, privateAverage = 0, publicAverage = 0, totalAverage = 0;
        double governmentMax = 0, privateMax = 0, publicMax = 0;
        double governmentMin = 0, privateMin = 0, publicMin = 0;

        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorID = donor.getDonorID();
            String type = donor.getType();
            ListInterface<Donation> donations = donorDonations.get(donorID);

            double donorTotal = 0.0;

            if (!donations.isEmpty()) {
                // Calculate total donation amount for this donor
                for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                    Donation donation = donations.getEntry(j);
                    donorTotal += donation.getCashAmount();

                    if (donation.getItemCategory().equals("Cash")) {
                        switch (type.toLowerCase()) {
                            case "government":
                                govDonations++;
                                break;
                            case "private":
                                privateDonations++;
                                break;
                            case "public":
                                publicDonations++;
                                break;
                        }
                    }
                }
            }

            // Update statistics based on donor type
            switch (type.toLowerCase()) {
                case "government":
                    governmentTotal += donorTotal;
                    governmentCount++;
                    if (donorTotal > governmentMax) {
                        governmentMax = donorTotal;
                    }
                    if (donorTotal < governmentMin) {
                        governmentMin = donorTotal;
                    }
                    break;
                case "private":
                    privateTotal += donorTotal;
                    privateCount++;
                    if (donorTotal > privateMax) {
                        privateMax = donorTotal;
                    }
                    if (donorTotal < privateMin) {
                        privateMin = donorTotal;
                    }
                    break;
                case "public":
                    publicTotal += donorTotal;
                    publicCount++;
                    if (donorTotal > publicMax) {
                        publicMax = donorTotal;
                    }
                    if (donorTotal < publicMin) {
                        publicMin = donorTotal;
                    }
                    break;
            }

        }
        totalCount = governmentCount + privateCount + publicCount;

        govPercentage = (double) governmentCount / totalCount * 100;
        privatePercentage = (double) privateCount / totalCount * 100;
        publicPercentage = (double) publicCount / totalCount * 100;

        governmentAverage = governmentTotal / governmentCount;
        privateAverage = privateTotal / privateCount;
        publicAverage = publicTotal / publicCount;

        total = governmentTotal + privateTotal + publicTotal;
        totalAverage = total / totalCount;
        totalDonations = govDonations + privateDonations + publicDonations;

        String header = "Donor Type | Number of Donors | Percentage | Number of Cash Donations | Total Cash Amount | Average Cash Amount | Max Amount | Min Amount ";
        System.out.println(" ".repeat(header.length() / 2 - 12) + "Donor Type Summary Report" + " ".repeat(header.length() / 2 - 12));
        System.out.println("-".repeat(header.length()));
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
        donorUI.printDonorTypeSummary("Government", governmentCount, govPercentage, govDonations, governmentTotal, governmentAverage, governmentMax, governmentMin);
        donorUI.printDonorTypeSummary("Private", privateCount, privatePercentage, privateDonations, privateTotal, privateAverage, privateMax, privateMin);
        donorUI.printDonorTypeSummary("Public", publicCount, publicPercentage, publicDonations, publicTotal, publicAverage, publicMax, publicMin);
        System.out.println("-".repeat(header.length()));
        System.out.println("Total Number of Donors  : " + totalCount);
        System.out.println("Total Cash Amount       : RM " + total);
        System.out.printf("Overall Average Amount  : RM %.2f\n", totalAverage);
        System.out.println("Number of Cash Donations: " + totalDonations);
        System.out.println("-".repeat(header.length()));
    }

    public void generateTopDonorsSummaryReport(int topN) {
        // Create a list to store donors with their total donation amounts and counts
        ListInterface<Donor> donorsWithCounts = new LinkedList<>();

        // Maps to store counts and amounts for each donor
        MapInterface<String, Integer> donationCounts = new HashMap<>();
        MapInterface<String, Double> donationAmounts = new HashMap<>();
        MapInterface<String, Integer> itemQuantity = new HashMap<>();
        MapInterface<String, Integer> cashCounts = new HashMap<>();
        MapInterface<String, Integer> itemCounts = new HashMap<>();

        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorID = donor.getDonorID();

            ListInterface<Donation> donations = donorDonations.get(donorID);
            int donationCount = donations.getNumberOfEntries();
            double totalAmount = 0.0;
            int cashCount = 0, itemCount = 0;
            int totalQty = 0;

            for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                Donation donation = donations.getEntry(j);
                totalAmount += donation.getCashAmount();
                totalQty += donation.getAmount();

                if (donation.getItemCategory().equals("Cash")) {
                    cashCount++;
                } else {
                    itemCount++;
                }
            }

            donationCounts.put(donorID, donationCount);
            donationAmounts.put(donorID, totalAmount);
            itemQuantity.put(donorID, totalQty);
            cashCounts.put(donorID, cashCount);
            itemCounts.put(donorID, itemCount);

            // Insert the donor in the sorted order by the total donation amount
            int k = 1;
            while (k <= donorsWithCounts.getNumberOfEntries()
                    && donationCount <= donationCounts.get(donorsWithCounts.getEntry(k).getDonorID())) {
                k++;
            }
            donorsWithCounts.add(k, donor);
        }

        // Print the top N donors
        String header = "DonorID    | Donor Name      | Cash Donations | Total Cash Amount | Item Donations | Quantity of Donated Item | Total Donations ";
        System.out.println(" ".repeat(header.length() / 2 - 12) + "Top Donors Summary Report" + " ".repeat(header.length() / 2 - 12));
        System.out.println(" ".repeat(header.length() / 2 - 17) + "Sorted by Total Number of Donations");
        System.out.println("-".repeat(header.length()));
        System.out.println(header);
        System.out.println("-".repeat(header.length()));

        for (int i = 1; i <= Math.min(topN, donorsWithCounts.getNumberOfEntries()); i++) {
            Donor donor = donorsWithCounts.getEntry(i);
            System.out.printf("%-10s | %-15s | %-14s | RM%-15.2f | %-14s | %-24s | %-15s \n", donor.getDonorID(), donor.getName(),
                    cashCounts.get(donor.getDonorID()), donationAmounts.get(donor.getDonorID()), itemCounts.get(donor.getDonorID()),
                    itemQuantity.get(donor.getDonorID()), donationCounts.get(donor.getDonorID()));
        }
        System.out.println("-".repeat(header.length()));
    }

    public String generateNextDonorID() {
        int nextID = 1; // Default starting ID
        if (donorList.getNumberOfEntries() > 0) {
            Donor lastDonor = donorList.getEntry(donorList.getNumberOfEntries());
            String lastID = lastDonor.getDonorID();
            nextID = Integer.parseInt(lastID.replace("DNR", "")) + 1;
        }
        return String.format("DNR%05d", nextID); // Format as DNR00001, DNR00002, etc.
    }

    // Comparator to sort donors by donorID
    Comparator<Donor> donorIDComparator = new Comparator<Donor>() {
        @Override
        public int compare(Donor d1, Donor d2) {
            return d1.getDonorID().compareTo(d2.getDonorID());
        }
    };

    public static void main(String[] args) {
        DonorManagement DonorManagement = new DonorManagement();
        driver driver = new driver();
        DonorManagement.runDonorManagement();
        driver.runDriver();
    }
}
