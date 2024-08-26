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
    private FilterInterface<Donor> filterDonor = new Filter<>();

    public DonorManagement() {
        donorList = donorDAO.retrieveFromFile();
        donationList = donationDAO.retrieveFromFile();
        categorisedDonors.put("government", new LinkedList<>());
        categorisedDonors.put("private", new LinkedList<>());
        categorisedDonors.put("public", new LinkedList<>());
        
        
    }

    public void runDonorManagement() {
        int choice = 0;
        do {
            choice = donorUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewDonor();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 2:
                    removeDonor();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 3:
                    updateDonorDetails();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 4:
                    searchDonorDetails();
                    break;
                case 5:
                    listDonorWithDonations();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 6:
                    filterDonor();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 7:
                    categoriseDonors();
                    printCategorisedDonors();
                    break;
                case 8:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addNewDonor() {
        Donor newDonor = donorUI.inputDonorDetails();
        donorList.add(newDonor);
        donorMap.put(newDonor.getDonorID(), newDonor);
        donorDAO.saveToFile(getAllDonors());
    }

    public String getAllDonors() {
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            outputStr += donorList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void displayDonors() {
        donorUI.listAllDonors(getAllDonors());
    }

    // Remove a donor by donorID
    public void removeDonor() {
        String donorID = donorUI.inputDonorID();
        donorMap.remove(donorID);
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
        } else {
            System.err.println("Donor ID not found.");
        }
    }

    // Update donor details referring to the donorID
    public void updateDonorDetails() {
        String donorID = donorUI.inputDonorID();
        Donor updatedDonor = donorUI.inputDonorDetails();
        donorMap.put(donorID, updatedDonor);
        boolean donorFound = false;
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            if (donorList.getEntry(i).getDonorID().equals(donorID)) {
                if (validateDonor(updatedDonor)) {
                    donorList.replace(i, updatedDonor);
                    donorFound = true;
                } else {
                    System.err.println("Invalid donor details.");
                    return;
                }
                break;
            }
        }
        if (donorFound) {
            donorDAO.saveToFile(getAllDonors());
            System.out.println("Donor detail updated successfully.");
        } else {
            System.err.println("Donor ID not found.");
        }
    }

    public void searchDonorDetails() {
        String donorID = donorUI.inputDonorID();
//        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
//            if (donorList.getEntry(i).getDonorID().equals(donorID)) {
//                return donorList.getEntry(i);
//            }
//        }
//        System.err.println("Donor ID not found.");
//        return null;
        if (donorMap.containsKey(donorID))
            System.out.println(donorMap.get(donorID));
        else
            System.err.println("Donor not found.");     
    }

    public void listDonorWithDonations() {

    }

    // Filter donor by type
    public void filterDonor() {
        int filterChoice = donorUI.getFilterChoice(); // Prompt user to choose filter type
        ListInterface<Donor> filteredDonors;

        switch (filterChoice) {
            case 1:
                String donorType = donorUI.inputDonorType();
                filteredDonors = filterDonor.filterByType(donorList, donorType);
                break;
            case 2:
                LocalDate startDate = donorUI.inputStartDate();
                LocalDate endDate = donorUI.inputEndDate();
                filteredDonors = filterDonor.filterByDate(donorList, startDate, endDate);
                break;
            case 3:
                double minAmount = donorUI.inputMinAmount();
                double maxAmount = donorUI.inputMaxAmount();
                filteredDonors = filterDonor.filterByAmountRange(donorList, minAmount, maxAmount);
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
        ListInterface<Donor> donors = donorList;

        LinkedList<Donor> governmentList = new LinkedList<>();
        LinkedList<Donor> publicList = new LinkedList<>();
        LinkedList<Donor> privateList = new LinkedList<>();

        // Iterate through the donor list
        for (int i = 1; i <= donors.getNumberOfEntries(); i++) {
            Donor donor = donors.getEntry(i);
            if (donor.getType().equalsIgnoreCase("Government")) {
                governmentList.add(donor);
            } else if (donor.getType().equalsIgnoreCase("Public")) {
                publicList.add(donor);
            } else if (donor.getType().equalsIgnoreCase("Private")) {
                privateList.add(donor);
            }
        }

        // Print the report
        System.out.println("Government Donors:");
        printDonors(governmentList);

        System.out.println("Public Donors:");
        printDonors(publicList);

        System.out.println("Private Donors:");
        printDonors(privateList);
    }

    // Helper method to print donor information
    private void printDonors(LinkedList<Donor> list) {
        if (list.getNumberOfEntries() == 0) {
            System.out.println("No donors in this category.");
            return;
        }
        for (int i = 0; i < list.getNumberOfEntries(); i++) {
            System.out.println(list.getEntry(i));
        }
    }

    private boolean validateDonor(Donor donor) {
        if (donor.getName() == null || donor.getName().isEmpty()) {
            return false;
        }
        if (donor.getDonorID() == null || donor.getDonorID().isEmpty()) {
            return false;
        }
        if (donor.getType() == null || donor.getType().isEmpty()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        DonorManagement DonorManagement = new DonorManagement();
        DonorManagement.runDonorManagement();
    }
}
