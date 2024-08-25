/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author haojuan
 */
package dao;

import adt.LinkedList;
import entity.Donation;
import java.time.LocalDate;

public class DonationDAO {
    private LinkedList<Donation> donationList = new LinkedList<>();

    public boolean addDonation(Donation donation) {
        return donationList.add(donation);
    }

    public boolean removeDonation(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                donationList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public LinkedList<String> getItemsByCategory(String category) {
        LinkedList<String> itemsInCategory = new LinkedList<>();

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);

            // Assuming that each donation has a list of items
            LinkedList<String> items = donation.getItems();

            // Iterate through items and check if they belong to the specified category
            for (int j = 1; j <= items.getNumberOfEntries(); j++) {
                String item = items.getEntry(j);
                
                // Assuming that the category is part of the item's string (e.g., "Clothing - Jacket")
                if (item.contains(category)) {
                    itemsInCategory.add(item);
                }
            }
        }
        return itemsInCategory;
    }

    public Donation getDonationById(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                return donation;
            }
        }
        return null;
    }

    public LinkedList<Donation> getDonationsByDonor(String donorID) {
        LinkedList<Donation> result = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                result.add(donation);
            }
        }
        return result;
    }

    public LinkedList<Donation> getAllDonations() {
        return donationList;
    }
    
    public LinkedList<Donation> filterDonationsByAmount(double minAmount, double maxAmount) {
        LinkedList<Donation> filteredDonations = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getAmount() >= minAmount && donation.getAmount() <= maxAmount) {
                filteredDonations.add(donation);
            }
        }
        return filteredDonations;
    }
    
    public LinkedList<Donation> filterDonationsByDate(LocalDate startDate, LocalDate endDate) {
        LinkedList<Donation> filteredDonations = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if ((donation.getDonationDate().isEqual(startDate) || donation.getDonationDate().isAfter(startDate)) &&
                (donation.getDonationDate().isEqual(endDate) || donation.getDonationDate().isBefore(endDate))) {
                filteredDonations.add(donation);
            }
        }
        return filteredDonations;
    }
}
