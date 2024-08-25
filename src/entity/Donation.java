/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import adt.LinkedList;
/**
 *
 * @author haojuan
 */
public class Donation {
    private String donationID;
    private String donorID;
    private LinkedList<String> items;
    private String donationType;

    public Donation(String donationID, String donorID, LinkedList<String> items, String donationType) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.items = items;
        this.donationType = donationType;
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
    
    @Override
    public String toString() {
        return String.format("%5s, %15s, %15s, %15s", donationID, donorID, items, donationType);
    }
}
