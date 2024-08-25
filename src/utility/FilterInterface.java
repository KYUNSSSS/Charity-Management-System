/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utility;

import adt.*;
import entity.Donee;
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

    
}
