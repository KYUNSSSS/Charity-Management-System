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

    public void categoriseDonor() {

    }

    public void generateReport() {

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
