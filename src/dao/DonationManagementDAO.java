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
import entity.DonationManagement;

public class DonationManagementDAO {
    private LinkedList<DonationManagement> donationList = new LinkedList<>();

    public boolean addDonation(DonationManagement donation) {
        return donationList.add(donation);
    }

    public boolean removeDonation(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            DonationManagement donation = donationList.getEntry(i);
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
            DonationManagement donation = donationList.getEntry(i);

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

    public DonationManagement getDonationById(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            DonationManagement donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                return donation;
            }
        }
        return null;
    }

    public LinkedList<DonationManagement> getDonationsByDonor(String donorID) {
        LinkedList<DonationManagement> result = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            DonationManagement donation = donationList.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                result.add(donation);
            }
        }
        return result;
    }

    public LinkedList<DonationManagement> getAllDonations() {
        return donationList;
    }
}
