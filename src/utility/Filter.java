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
 */
public class Filter<F> implements FilterInterface<F> {

    @Override
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

    @Override
    public ListInterface<F> filterByDate(ListInterface<F> list, LocalDate startDate, LocalDate endDate) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation && isWithinDateRange(((Donation) entity).getDonationDate(), startDate, endDate)) {
                filteredList.add(entity);
            } else if (entity instanceof Distribution && isWithinDateRange(((Distribution) entity).getDistributionDate(), startDate, endDate)) {
                filteredList.add(entity);
            }
        }
        return filteredList;
    }

    @Override
    public ListInterface<F> filterByAmountRange(ListInterface<F> list, double minAmount, double maxAmount) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation && isWithinAmountRange(((Donation) entity).getAmount(), minAmount, maxAmount)) {
                filteredList.add(entity);
            } else if (entity instanceof Distribution && isWithinAmountRange(((Distribution) entity).getAmount(), minAmount, maxAmount)) {
                filteredList.add(entity);
            }
        }
        return filteredList;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

        @Override
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

    @Override
    public ListInterface<F> filterByDonationAmountRange(ListInterface<F> list, double minAmount, double maxAmount) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donation donation) {
                if (isWithinAmountRange(donation.getAmount(), minAmount, maxAmount)) {
                    filteredList.add(entity);
                }
            }
        }
        return filteredList;
    }
}
