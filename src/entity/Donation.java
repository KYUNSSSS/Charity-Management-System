/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import adt.LinkedList;
import java.time.LocalDate;
/**
 *
 * @author haojuan
 */
public class Donation {
    private String donationID;
    private String donorID;
    private String itemCategory;
    private String item;
    private double amount;
    private LocalDate donationDate;
    
    
    public Donation(String donationID, String donorID, String itemCategory, String item, double amount, LocalDate donationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.itemCategory = itemCategory;
        this.item = item;
        this.amount = amount;
        this.donationDate = donationDate;
    }

    // Getters and Setters
    public String getDonationID() {
        return donationID;
    }

    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public double getAmount() {
        return amount; 
    }
    public void setAmount(double amount) { 
        this.amount = amount; 
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }
    
    
    @Override
    public String toString() {
        return String.format("Donation ID : %s\nDonor ID : %s\nCategory: %s\nItem : %s\nAmount : %.2f\nDate : %s",
                             donationID, donorID, itemCategory, item, amount, donationDate.toString());
    }

}
