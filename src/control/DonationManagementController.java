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
import entity.Donation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DonationManagementController {

    private DonationManagementDAO donationDAO = new DonationManagementDAO();

    public boolean addDonation(String donationID, String donorID, String[] itemsArray, String donationType) {
        LinkedList<String> items = new LinkedList<>();
        for (String item : itemsArray) {
            items.add(item);
        }
        Donation donation = new Donation(donationID, donorID, items, donationType);
        return donationDAO.addDonation(donation);
    }

    public boolean removeDonation(String donationID) {
        return donationDAO.removeDonation(donationID);
    }

    public Donation getDonationById(String donationID) {
        return donationDAO.getDonationById(donationID);
    }
    
    public LinkedList<String> trackDonationByCategory(String category) {
        return donationDAO.getItemsByCategory(category);
    }

    public LinkedList<Donation> listDonationsByDonor(String donorID) {
        // Implement logic to filter donations by donor ID
        LinkedList<Donation> filteredDonations = new LinkedList<>();
        LinkedList<Donation> allDonations = donationDAO.getAllDonations();

        for (int i = 1; i <= allDonations.getNumberOfEntries(); i++) {
            Donation donation = allDonations.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                filteredDonations.add(donation);
            }
        }

        return filteredDonations;
    }

    public LinkedList<Donation> getDonationsByDonor(String donorID) {
        LinkedList<Donation> donations = donationDAO.getDonationsByDonor(donorID);
        return donations;
    }

    public LinkedList<Donation> getAllDonations() {
        return donationDAO.getAllDonations();
    }
    
    public LinkedList<Donation> listAllDonations() {
        return donationDAO.getAllDonations();
    }

    public String generateReport() { //The report saved in the user's Downloads folder and includes all relevant donation details.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String fileName = "donation_summary_report_" + dateFormatter.format(LocalDate.now()) + ".txt";
        StringBuilder reportContent = new StringBuilder();

        try {
            // Get the downloads folder path
            Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
            Path filePath = downloadsFolder.resolve(fileName);

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Fetch all donations from the DAO
            LinkedList<Donation> donations = donationDAO.getAllDonations();

            // Write the report content
            reportContent.append("*****Donation Summary Report*****\n");
            reportContent.append("Date Generated    : ").append(LocalDate.now().format(dateFormatter)).append("\n\n");

            reportContent.append("Summary Report :\n");
            reportContent.append("Total Number of Donations : ").append(donations.getNumberOfEntries()).append("\n\n");

            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Donation donation = donations.getEntry(i);
                reportContent.append("Donation ID   : ").append(donation.getDonationID()).append("\n");
                reportContent.append("Donor ID      : ").append(donation.getDonorID()).append("\n");
                reportContent.append("Donation Type : ").append(donation.getDonationType()).append("\n");
                reportContent.append("Items         : ").append(donation.getItems().toString()).append("\n");
                reportContent.append("-------------------------------------------------------\n");
            }

            // Write the report to the file
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
}
