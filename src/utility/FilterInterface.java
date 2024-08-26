/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utility;

import adt.*;
import entity.*;
import java.time.LocalDate;

/**
 *
 * @author xuan
 */
public interface FilterInterface<F> {

    public ListInterface<F> filterByType(ListInterface<F> list, String type);

    public ListInterface<F> filterByDate(ListInterface<F> list, LocalDate startDate, LocalDate endDate);

    public ListInterface<F> filterByAmountRange(ListInterface<F> list, double minAmount, double maxAmount);
    
    public ListInterface<F> filterByDonationItem(ListInterface<F> list, String item);
    
    public ListInterface<F> filterByEntityType(ListInterface<F> list, String type);

    public ListInterface<F> filterByLocation(ListInterface<F> list, String type);
    
    public ListInterface<Distribution> filterByDateAndDoneeID(ListInterface<Distribution> list, LocalDate startDate, LocalDate endDate, String doneeID);
    
    public ListInterface<F> filterByAmountAndDoneeID(ListInterface<F> list, double minAmount, double maxAmount, String doneeID);

    public ListInterface<F> filterByDateRange(ListInterface<F> list, LocalDate startDate, LocalDate endDate);
    
    public ListInterface<F> filterByDonationAmountRange(ListInterface<F> list, double minAmount, double maxAmount);

    public ListInterface<F> filterByVolunteerType(ListInterface<F> list, String type);
    
    public ListInterface<F> filterByEvent(ListInterface<F> list, String event);
    
}
