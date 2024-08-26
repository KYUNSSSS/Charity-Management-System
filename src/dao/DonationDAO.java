package dao;

import adt.LinkedList;
import entity.Donation;
import java.io.*;
import java.time.LocalDate;

public class DonationDAO {
    private String fileName = "donations.txt";
    // Save the donation list to file in the specified format
    public void saveDonationListToFile(LinkedList<Donation> donationList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                Donation donation = donationList.getEntry(i);
                writer.write(donationToString(donation));
                writer.newLine();
                writer.write("-------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving donations to file: " + e.getMessage());
        }
    }

    // Load the donation list from file
    public LinkedList<Donation> loadDonationsFromFile() {
        LinkedList<Donation> donationList = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String donationID = "", donorID = "", item = "", itemCategory = "";
            LocalDate donationDate = null;
            double amount = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Donation Date:")) {
                    donationDate = LocalDate.parse(line.substring(14).trim());
                } else if (line.startsWith("Donation ID:")) {
                    donationID = line.substring(12).trim();
                } else if (line.startsWith("Donors ID:")) {
                    donorID = line.substring(10).trim();
                } else if (line.startsWith("Donation Type:")) {
                    itemCategory = line.substring(14).trim();
                } else if (line.startsWith("Item:")) {
                    item = line.substring(6).trim(); // Changed
                } else if (line.startsWith("Amount:")) {
                    amount = Double.parseDouble(line.substring(8).trim());
                } else if (line.startsWith("-------------------------------------------")) {
                    Donation donation = new Donation(donationID, donorID,itemCategory,item, amount, donationDate);
                    donationList.add(donation);

                    // Reset variables for the next donation
                    donationID = "";
                    donorID = "";
                    itemCategory = "";
                    item = "";
                    donationDate = null;
                    amount = 0.0;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading donations from file: " + e.getMessage());
        }
        return donationList;
    }

    // Convert Donation object to string in the specified format
    private String donationToString(Donation donation) {
        return "Donation Date: " + donation.getDonationDate().toString() + "\n" +
               "Donation ID: " + donation.getDonationID() + "\n" +
               "Donors ID: " + donation.getDonorID() + "\n" +
               "Donation Type: " + donation.getItemCategory() + "\n" +
               "Item: " + donation.getItem() + "\n" +
               "Amount: " + donation.getAmount();
    }
}
