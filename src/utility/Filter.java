/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import adt.*;
import entity.*;
import java.time.LocalDate;

/**
 *
 * @author Team
 * @param <F>
 */
public class Filter<F> {

    //Donor and Donee Filter
    public ListInterface<F> filterByType(ListInterface<F> list, String type) { 
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F types = list.getEntry(i);
            if (types instanceof Donor && ((Donor) types).getType().equalsIgnoreCase(type)) {
                filteredList.add(types);
            } else if (types instanceof Donee && ((Donee) types).getDoneeType().equalsIgnoreCase(type)) {
                filteredList.add(types);
            }
        }
        return filteredList;
    }

    // Donor Filter
    public ListInterface<Donor> filterByDate(MapInterface<String, Donor> donorMap, MapInterface<String, ListInterface<Donation>> map, LocalDate startDate, LocalDate endDate) { 
        ListInterface<Donor> filteredList = new LinkedList<>();
        for (int i = 1; i <= map.capacity(); i++) {
            String donorID = map.getKey(i);

            if (donorID == null) {
                continue; // Skip null donorID
            }

            ListInterface<Donation> donations = map.get(donorID);
            boolean donorMatches = false;

            for (int j = 1; j <= donations.getNumberOfEntries(); j++) {

                Donation donation = donations.getEntry(j);
                LocalDate donationDate = donation.getDonationDate();

                if (isWithinDateRange(donationDate, startDate, endDate)) {
                    donorMatches = true;
                    break;
                }
            }
            if (donorMatches) {
                Donor donor = donorMap.get(donorID);
                if (donor != null) {
                    filteredList.add(donor);
                }
            }
        }
        return filteredList;
    }

    // Donor Filter
    public ListInterface<Donor> filterByAmountRange(MapInterface<String, Donor> donorMap, MapInterface<String, ListInterface<Donation>> map, double minAmount, double maxAmount) {
        ListInterface<Donor> filteredList = new LinkedList<>();
        for (int i = 1; i <= map.capacity(); i++) {
            String donorID = map.getKey(i);

            if (donorID == null) {
                continue; // Skip null donorID
            }

            ListInterface<Donation> donations = map.get(donorID);
            boolean donorMatches = false;

            for (int j = 1; j <= donations.getNumberOfEntries(); j++) {
                Donation donation = donations.getEntry(j);
                double donationAmount = donation.getCashAmount();

                if (isWithinAmountRange(donationAmount, minAmount, maxAmount)) {
                    donorMatches = true;
                    break;
                }
            }
            if (donorMatches) {
                Donor donor = donorMap.get(donorID);
                if (donor != null) {
                    filteredList.add(donor);
                }
            }
        }
        return filteredList;
    }

    // Donor Filter
    public ListInterface<F> filterByEntityType(ListInterface<F> list, String type) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (((Donor) entity).getEntityType().equalsIgnoreCase(type)) {
                filteredList.add(entity);
            }
        }
        return filteredList;
    }

    // Donee Filter
    public ListInterface<F> filterByLocation(ListInterface<F> list, String type) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (((Donee) entity).getDoneeLocation().equalsIgnoreCase(type)) {
                filteredList.add(entity);
            }
        }
        return filteredList;
    }

    // Helper method
    private boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null) {
            return false; // Handle null dates appropriately
        }

        // Check if the date is after or equal to startDate and before or equal to endDate
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }

    // Helper method
    private boolean isWithinAmountRange(double amount, double minAmount, double maxAmount) {
        return amount >= minAmount && amount <= maxAmount;
    }

    // Distribution Filter
    public ListInterface<Distribution> filterByDateAndDoneeID(ListInterface<Distribution> list, LocalDate startDate, LocalDate endDate, String doneeID) {
        ListInterface<Distribution> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Distribution distribution = list.getEntry(i);

            if (isWithinDateRange(distribution.getDistributionDate(), startDate, endDate) && distribution.getDoneeID().equals(doneeID)) {
                filteredList.add(distribution);
            }
        }
        return filteredList;
    }

    // Distribution Filter
    public ListInterface<F> filterByAmountAndDoneeID(ListInterface<F> list, double minAmount, double maxAmount, String doneeID) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);

            if (entity instanceof Distribution distribution) {
                if (distribution.getAmount() >= minAmount && distribution.getAmount() <= maxAmount && distribution.getDoneeID().equals(doneeID)) {
                    filteredList.add(entity);
                }
            }
        }
        return filteredList;
    }

    // Donation Filter
    public ListInterface<F> filterByDateRange(ListInterface<F> list, LocalDate startDate, LocalDate endDate) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation donation) {
                if (isWithinDateRange(donation.getDonationDate(), startDate, endDate)) {
                    filteredList.add(entity);
                }
            }
        }
        return filteredList;
    }

    // Donation Filter
    public ListInterface<F> filterByDonationAmountRange(ListInterface<F> list, double minAmount, double maxAmount) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation donation) {
                if (isWithinAmountRange(donation.getAmount(), minAmount, maxAmount)) {
                    filteredList.add(entity);
                }
                if (isWithinAmountRange(donation.getCashAmount(), minAmount, maxAmount)) {
                    filteredList.add(entity);
                }
            }
        }
        return filteredList;
    }
    
    // Donation Filter
    public ListInterface<F> filterByDateAndAmountRange(ListInterface<F> list, LocalDate startDate, LocalDate endDate, double minAmount, double maxAmount) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation donation) {
                if (isWithinDateRange(donation.getDonationDate(), startDate, endDate)) {
                    if (isWithinAmountRange(donation.getAmount(), minAmount, maxAmount)) {
                        filteredList.add(entity);
                    }
                    if (isWithinAmountRange(donation.getCashAmount(), minAmount, maxAmount)) {
                        filteredList.add(entity);
                    }
                }
            }
        
        }
        return filteredList;
    }

    // Volunteer Filter
    public ListInterface<F> filterByVolunteerType(ListInterface<F> list, String type) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F volunteer = list.getEntry(i);
            if (((Volunteer) volunteer).getVolunteerType().equalsIgnoreCase(type)) {
                filteredList.add(volunteer);
            }
        }
        return filteredList;
    }

    // Volunteer Filter
    public ListInterface<F> filterByEvent(ListInterface<F> list, String event) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F volunteer = list.getEntry(i);
            if (((Volunteer) volunteer).getEventAssigned().contains(event)) {
                filteredList.add(volunteer);
            }
        }
        return filteredList;
    }
}
