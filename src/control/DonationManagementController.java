/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author haojuan
 */
package control;

import adt.LinkedList;
import dao.DonationManagementDAO;
import entity.DonationManagement;
import boundary.DonationManagementUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utility.MessageUI;

public class DonationManagementController {
    private DonationManagementDAO donationDAO = new DonationManagementDAO();
    private DonationManagementUI ui;

    // Constructor with DonationManagementUI
    public DonationManagementController(DonationManagementUI ui) {
        this.ui = ui;
    }

    // Default constructor
    public DonationManagementController() {
        // Default constructor
    }

    // Set the UI (if needed)
    public void setUI(DonationManagementUI ui) {
        this.ui = ui;
    }
    
    public void runDonationManagement() {
        int choice;
        
        do {
            choice = ui.getMenuChoice();

            switch (choice) {
                case 1:
                    ui.addDonation();
                    break;
                case 2:
                    ui.removeDonation();
                    break;
                case 3:
                    ui.searchDonationById();
                    break;
                case 4:
                    ui.amendDonorsDetails();
                case 5:
                    ui.trackDonation();
                    break;
                case 6:
                    ui.listDonationsByDonors();
                    break;
                case 7:
                    ui.listDonations();
                    break;
                case 8:
                    ui.donationsReports();
                    break;
                case 0:
                    System.out.println("Exiting Donation Management System.");
                    break;
                default:
                    System.err.println("Invalid choice. Please select an option between 0 and 8.");
            }
        } while (choice != 0);
      }

    public boolean addDonation(String donationID, String donorID, String[] itemsArray, String donationType) {
        LinkedList<String> items = new LinkedList<>();
        for (String item : itemsArray) {
            items.add(item);
        }
        DonationManagement donation = new DonationManagement(donationID, donorID, items, donationType);
        return donationDAO.addDonation(donation);
    }

    public boolean removeDonation(String donationID) {
        return donationDAO.removeDonation(donationID);
    }

    public DonationManagement getDonationById(String donationID) {
        return donationDAO.getDonationById(donationID);
    }
    
    public LinkedList<String> trackDonationByCategory(String category) {
        return donationDAO.getItemsByCategory(category);
    }

    public LinkedList<DonationManagement> listDonationsByDonor(String donorID) {
        LinkedList<DonationManagement> filteredDonations = new LinkedList<>();
        LinkedList<DonationManagement> allDonations = donationDAO.getAllDonations();

        for (int i = 1; i <= allDonations.getNumberOfEntries(); i++) {
            DonationManagement donation = allDonations.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                filteredDonations.add(donation);
            }
        }

        return filteredDonations;
    }

    public LinkedList<DonationManagement> getDonationsByDonor(String donorID) {
        return donationDAO.getDonationsByDonor(donorID);
    }

    public LinkedList<DonationManagement> getAllDonations() {
        return donationDAO.getAllDonations();
    }
    
    public LinkedList<DonationManagement> listAllDonations() {
        return donationDAO.getAllDonations();
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

            LinkedList<DonationManagement> donations = donationDAO.getAllDonations();

            reportContent.append("*****Donation Summary Report*****\n");
            reportContent.append("Date Generated    : ").append(LocalDate.now().format(dateFormatter)).append("\n\n");

            reportContent.append("Summary Report :\n");
            reportContent.append("Total Number of Donations : ").append(donations.getNumberOfEntries()).append("\n\n");

            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                DonationManagement donation = donations.getEntry(i);
                reportContent.append("Donation ID   : ").append(donation.getDonationID()).append("\n");
                reportContent.append("Donor ID      : ").append(donation.getDonorID()).append("\n");
                reportContent.append("Donation Type : ").append(donation.getDonationType()).append("\n");
                reportContent.append("Items         : ").append(donation.getItems().toString()).append("\n");
                reportContent.append("-------------------------------------------------------\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write(reportContent.toString());
                System.out.println("Summary report is downloaded to : " + filePath);
            } catch (IOException e) {
                System.err.println("Error writing summary report to file : " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error creating summary report file : " + e.getMessage());
        }

        return reportContent.toString();
    }

    public static void main(String[] args) {
        // Create the controller first
        DonationManagementController controller = new DonationManagementController();
        
        // Now pass the controller to UI
        DonationManagementUI ui = new DonationManagementUI(controller);
        
        // Create the controller with the UI
        controller.setUI(ui); // This method should be implemented if needed

        // Run the donation management system
        controller.runDonationManagement();
    }
}

