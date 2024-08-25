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
            if (entity instanceof Donor && isWithinDateRange(((Donor) entity).getDonationDate(), startDate, endDate)) {
                filteredList.add(entity);
            } 
//            else if (entity instanceof Donee && isWithinDateRange(((Donee) entity).getDonationDate(), startDate, endDate)) {
//                filteredList.add(entity);
//            }
        }
        return filteredList;
    }

    @Override
    public ListInterface<F> filterByAmountRange(ListInterface<F> list, double minAmount, double maxAmount) {
        ListInterface<F> filteredList = new LinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            F entity = list.getEntry(i);
            if (entity instanceof Donor && isWithinAmountRange(((Donor) entity).getDonations(), minAmount, maxAmount)) {
                filteredList.add(entity);
            } 
//            else if (entity instanceof Donee && isWithinAmountRange(((Donee) entity).getDonationAmount(), minAmount, maxAmount)) {
//                filteredList.add(entity);
//            }
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

    private boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        // Implement date comparison logic
        return true; // Placeholder
    }

    private boolean isWithinAmountRange(double amount, double minAmount, double maxAmount) {
        return amount >= minAmount && amount <= maxAmount;
    }
}
