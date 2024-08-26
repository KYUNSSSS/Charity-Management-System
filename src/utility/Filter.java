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
 * @author xuan
 * @param <F>
 */
public class Filter<F> {

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

    public ListInterface<Donor> filterByDate(MapInterface<String, Donor> donorMap, MapInterface<String, ListInterface<Donation>> map, LocalDate startDate, LocalDate endDate) {
        ListInterface<Donor> filteredList = new LinkedList<>();
        for (int i = 1; i <= map.capacity(); i++) {
            String donorID = map.getKey(i);

            if (donorID == null) {
//                System.out.println("Null donorID found at index " + i);
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
                double donationAmount = donation.getAmount();

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

    public ListInterface<F> filterByDonationItem(ListInterface<F> list, String item) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
//            F entity = list.getEntry(i);
//            if (entity instanceof Donor && ((Donor) entity).getDonationItem().equalsIgnoreCase(item)) {
//                filteredList.add(entity);
//            } else if (entity instanceof Donee && ((Donee) entity).getDonationItem().equalsIgnoreCase(item)) {
//                filteredList.add(entity);
//            }
        }
        return filteredList;
    }

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

    private boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null) {
            return false; // Handle null dates appropriately
        }

        // Check if the date is after or equal to startDate and before or equal to endDate
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }

    private boolean isWithinAmountRange(double amount, double minAmount, double maxAmount) {
        return amount >= minAmount && amount <= maxAmount;
    }

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
                }
