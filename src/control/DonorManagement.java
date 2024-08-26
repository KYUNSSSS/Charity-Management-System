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
import utility.*;

/**
 *
 * @author xuan
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
            donorMap.put(donor.getDonorID(), donor);
            donorDonations.put(donor.getDonorID(), new LinkedList<>());
        }

        for (int i = 1; i <= donorDonations.capacity(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor != null) {
                Donation donation = donationList.getEntry(i);

                if (donation == null) {
                    continue;
                }
                String donorID = donation.getDonorID();
                if (donorDonations.containsKey(donorID)) {
                    donorDonations.get(donorID).add(donation); //get the donation list from the key and add donation
                }
            }
        }
    }

    public void runDonorManagement() {
        int choice = 0;
        do {
//            listAllDonors();
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
                    listAllDonors();
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
    }

    public void searchDonorDetails() {
        String donorID = donorUI.inputDonorID();
        if (donorMap.containsKey(donorID)) {
            donorUI.displayDonorDetails(donorMap.get(donorID));
        } else {
            System.err.println("Donor not found.");
        }
    }

    public void listDonorsWithDonations() {
        // Define table headers
        String header = String.format("%-10s %-20s %-15s %-15s %-10s %-15s %-10s %-15s %-10s %-10s %-15s",
                "DonorID", "Name", "Contact No", "Email",
                "Type", "Entity Type", "DonationID", "Donation Type", "Item", "Amount", "Donation Date");

        // Print table header
        System.out.println(header);
        System.out.println("=".repeat(header.length()));

        for (int i = 1; i <= donorDonations.capacity(); i++) {
            String donorID = donorDonations.getKey(i);

            if (donorID == null) {
//                System.out.println("Warning: Found null donorID at index " + i);
                continue; // Skip to the next iteration
            }

            Donor donor = donorMap.get(donorID);

            // Check if the donor is null
            if (donor == null) {
                System.out.println("Warning: Donor not found for donorID " + donorID);
                continue; // Skip to the next iteration
            }

            // Display the donor details once
            String donorRow = String.format("%-10s %-20s %-15s %-15s %-10s %-15s",
                    donor.getDonorID(),
                    donor.getName(),
                    donor.getContactNo(),
                    donor.getEmail(),
                    donor.getType(),
                    donor.getEntityType());
            System.out.println(donorRow);

            ListInterface<Donation> donations = donorDonations.get(donorID);
            // Display all donations for this donor
            if (!donations.isEmpty()) {
                for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                    Donation donation = donations.getEntry(j);

                    // Format the donation details under the donor
                    String donationRow = String.format("%-10s %-20s %-15s %-15s %-10s %-15s %-10s %-15s %-10s %-10.2f %-15s",
                            "", "", "", "", "", "", // Empty for donor details
                            donation.getDonationID(),
                            donation.getItemCategory(),
                            donation.getItem(),
                            donation.getAmount(),
                            donation.getDonationDate().toString());

                    System.out.println(donationRow);
                }
            } else {
                System.out.printf("%-10s %-20s %-15s %-15s %-10s %-15s %s", "", "", "", "", "", "", "No donations found for this donor.");
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

        donorUI.displayFilteredDonors(filteredDonors);
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
                    System.out.println("Category: Government");
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
                System.out.println(donors);
            } else {
                System.out.println("No donors in this category.");
            }
            System.out.println();
        }
    }

    public void generateReport() {
//        LinkedList<Donor> governmentList = categorisedDonors.get("government");
//        LinkedList<Donor> privateList = categorisedDonors.get("private");
//        LinkedList<Donor> publicList = categorisedDonors.get("public");
//
//        int governmentCount = governmentList.getNumberOfEntries();
//        int privateCount = privateList.getNumberOfEntries();
//        int publicCount = publicList.getNumberOfEntries();

        int governmentCount = 0, privateCount = 0, publicCount = 0, totalCount = 0;
        double governmentTotal = 0, privateTotal = 0, publicTotal = 0;
        double govPercentage = 0, privatePercentage = 0, publicPercentage = 0;
        double governmentAverage = 0, privateAverage = 0, publicAverage = 0;
        double governmentMax = Double.MIN_VALUE, privateMax = Double.MIN_VALUE, publicMax = Double.MIN_VALUE;
        double governmentMin = Double.MAX_VALUE, privateMin = Double.MAX_VALUE, publicMin = Double.MAX_VALUE;

        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorID = donor.getDonorID();
            String type = donor.getType();
            ListInterface<Donation> donations = donorDonations.get(donorID);

            double donorTotal = 0.0;

            // Calculate total donation amount for this donor
            for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                Donation donation = donations.getEntry(j);
                donorTotal += donation.getAmount();
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
            
            totalCount = governmentCount + privateCount + publicCount;
            System.out.println(totalCount);
            govPercentage = (double)governmentCount / totalCount * 100;
            privatePercentage = (double)privateCount / totalCount * 100;
            publicPercentage = (double)publicCount / totalCount * 100;

            governmentAverage = governmentTotal / governmentCount;
            privateAverage = privateTotal / privateCount;
            publicAverage = publicTotal / publicCount;

        }
        System.out.println("*********Donor Type Summary Report*********");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("Donor Type | Number of Donors | Percentage |    Total    |   Average   | Max Amount | Min Amount ");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-16d | %-10s | RM%-9.2f | RM%-9.2f | RM%-8.2f | RM%-8.2f\n", "Government", governmentCount, govPercentage + "%", governmentTotal, governmentAverage, governmentMax, governmentMin);
        System.out.printf("%-10s | %-16d | %-10s | RM%-9.2f | RM%-9.2f | RM%-8.2f | RM%-8.2f\n", "Private", privateCount, privatePercentage + "%", privateTotal, privateAverage, privateMax, privateMin);
        System.out.printf("%-10s | %-16d | %-10s | RM%-9.2f | RM%-9.2f | RM%-8.2f | RM%-8.2f\n", "Public", publicCount, publicPercentage + "%", publicTotal, publicAverage, publicMax, publicMin);
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

    public static void main(String[] args) {
        DonorManagement DonorManagement = new DonorManagement();
        DonorManagement.runDonorManagement();
    }
}
