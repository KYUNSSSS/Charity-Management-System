/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utility;

import adt.*;

/**
 *
 * @author xuan
 */
public interface FilterInterface<F> {
    ListInterface<F> filterByType(LinkedList<F> list, String type);
    ListInterface<F> filterByDate(LinkedList<F> list, String startDate, String endDate);
}
