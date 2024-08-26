package entity;

import java.time.LocalDate;

public class Donation {
    private String donationID;
    private String donorID;
    private String itemCategory;
    private String item;
    private int amount;
    private double cashAmount;
    private LocalDate donationDate;

    // Constructor for non-cash donations
    public Donation(String donationID, String donorID, String itemCategory, String item, int amount, LocalDate donationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.itemCategory = itemCategory;
        this.item = item;
        this.amount = amount;
        this.donationDate = donationDate;
    }

    // Constructor for cash donations
    public Donation(String donationID, String donorID, String itemCategory, String item, double cashAmount, LocalDate donationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.itemCategory = itemCategory;
        this.item = item;
        this.cashAmount = cashAmount;
        this.donationDate = donationDate;
    }

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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    
    
    @Override
    public String toString() {
        return "Donation Date: " + donationDate.toString() + "\n" +
               "Donation ID: " + donationID + "\n" +
               "Donors ID: " + donorID + "\n" +
               "Donation Type: " + itemCategory + "\n" +
               "Item: " + item + "\n" +
               "Amount: " + (itemCategory.equalsIgnoreCase("cash") 
                   ? String.format("RM %.2f", cashAmount) 
                   : amount);
    }
}
