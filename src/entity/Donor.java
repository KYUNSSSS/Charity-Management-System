/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

// cannot use list and arraylist

/**
 *
 * @author xuan
 */
public class Donor {
    private String donorID;
    private String name;
    private String contact;
    private String type; //government, private, public
//    private List<Donation> donations;

    public Donor(String donorID, String name, String contact, String type) {
        this.donorID = donorID;
        this.name = name;
        this.contact = contact;
        this.type = type;
//        this.donations = new ArrayList<>();
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
//    public void addDonation(Donation donation) {
//        donations.add(donation);
//    }
//
//    public void removeDonation(Donation donation) {
//        donations.remove(donation);
//    }
//
//    public List<Donation> getDonations() {
//        return donations;
//    }

    @Override
    public String toString() {
        return String.format("%5s, %15s, %15s, %15s", donorID, name, contact, type);
    }
    
    
}
