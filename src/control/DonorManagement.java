/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import boundary.DonorManagementUI;
import dao.DonorDAO;
import utility.MessageUI;

/**
 *
 * @author xuan
 */
public class DonorManagement {

    private ListInterface<Donor> donorList = new LinkedList<>();
    private DonorDAO donorDAO = new DonorDAO();
    private DonorManagementUI donorUI = new DonorManagementUI();

    public DonorManagement() {
        donorList = donorDAO.retrieveFromFile();
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
                    donorUI.listAllDonors(getAllDonors());
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
                    categoriseDonor();
                    donorUI.listAllDonors(getAllDonors());
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
        donorDAO.saveToFile(getAllDonors());
    }

    public String getAllDonors() {
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            outputStr += donorList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public String getAllDonors(ListInterface<Donor> donorList) {
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
        } else {
            System.err.println("Donor ID not found.");
        }
    }

    public Donor searchDonorDetails() {
        String donorID = donorUI.inputDonorID();
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            if (donorList.getEntry(i).getDonorID().equals(donorID)) {
                return donorList.getEntry(i);
            }
        }
        System.err.println("Donor ID not found.");
        return null;
    }

    public void listDonorWithDonations() {

    }

    // Filter donor by type
    public String filterDonor() {
        String donorType = donorUI.inputDonorType();
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor.getType().equalsIgnoreCase(donorType)) {
                outputStr += donorList.getEntry(i) + "\n";
            }
        }
        return outputStr;
    }

    private void categoriseDonor() {
        // LinkedLists to hold categorized donors
        ListInterface<Donor> governmentDonors = new LinkedList<>();
        ListInterface<Donor> privateDonors = new LinkedList<>();
        ListInterface<Donor> publicDonors = new LinkedList<>();

        // Categorize donors
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            Donor donor = donorList.getEntry(i);
            String donorType = donor.getType().toLowerCase(); // Convert to lower case for consistent comparison

            switch (donorType) {
                case "government":
                    governmentDonors.add(donor);
                    break;
                case "private":
                    privateDonors.add(donor);
                    break;
                case "public":
                    publicDonors.add(donor);
                    break;
                default:
                    System.err.println("Unknown donor type: " + donorType);
                    break;
            }
        }

        // Display categorized donors
        System.out.println("Categorized Donors by Type:");
        System.out.println("Government Donors:");
        System.out.println(getAllDonors(governmentDonors));

        System.out.println("Private Donors:");
        System.out.println(getAllDonors(privateDonors));

        System.out.println("Public Donors:");
        System.out.println(getAllDonors(publicDonors));
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
