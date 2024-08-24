/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author SCSM11
 */
public class Distribution implements Serializable{
    private String distributionID;
    private String itemName;
    private String category;
    private int quantity;
    private String doneeID;
    private String status;
    private LocalDate distributionDate; 

    public Distribution(String distributionID, String itemName, String category, int quantity, String doneeID, String status, LocalDate distributionDate) {
        this.distributionID = distributionID;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.doneeID = doneeID;
        this.status = status;
        this.distributionDate = distributionDate;
    }

    public String getDistributionID() {
        return distributionID;
    }

    public void setDistributionID(String distributionID) {
        this.distributionID = distributionID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(LocalDate distributionDate) {
        this.distributionDate = distributionDate;
    }

    public String getDoneeID() {
        return doneeID;
    }

    public void setDoneeID(String doneeID) {
        this.doneeID = doneeID;
    }

   @Override
    public String toString() {
        return String.format("%5s, %s, %s, %d, %s, %s, %s", distributionID , itemName, category, quantity, doneeID, status, distributionDate);
    }
    
}
