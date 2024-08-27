package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Distribution implements Serializable {
    private String distributionID;
    private String category;
    private String itemName;
    private int quantity;
    private double amount;
    private String doneeID;
    private String status;
    private LocalDate distributionDate;

    public Distribution(String distributionID, String category, String itemName, int quantity, double amount, String doneeID, String status, LocalDate distributionDate) {
        this.distributionID = distributionID;
        this.category = category;
        this.itemName = itemName;
        this.quantity = quantity;
        this.amount = amount;
        this.doneeID = doneeID;
        this.status = status;
        this.distributionDate = distributionDate;
    }

    // Getters and setters
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        return String.format("%5s, %s, %s, %d, %.2f, %s, %s, %s", 
                             distributionID, category, itemName, quantity, amount, doneeID, status, distributionDate);
    }
}

