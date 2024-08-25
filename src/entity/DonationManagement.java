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
public class DonationManagement {
    private String donationID;
    private String donorID;
    private LinkedList<String> items;
    private String donationType;
    private double amount;
    private LocalDate donationDate;

    public DonationManagement(String donationID, String donorID, LinkedList<String> items, String donationType) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.items = items;
        this.donationType = donationType;
    }
    
    
    public DonationManagement(String donationID, String donorID, LinkedList<String> items, String donationType, double amount, LocalDate donationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.items = items;
        this.donationType = donationType;
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

    public LinkedList<String> getItems() {
        return items;
    }

    public void setItems(LinkedList<String> items) {
        this.items = items;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
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
        return String.format("%5s, %15s, %15s, %15s, %10.2f, %s", donationID, donorID, items, donationType, amount, donationDate.toString());
    }
}
