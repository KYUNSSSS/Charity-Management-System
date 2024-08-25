/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author xuan
 */
public class Donor implements Serializable {

    private String donorID;
    private String name;
    private int contactNo;
    private String email;
    private String type; //government, private, public
    private String entityType; //Organisation, individual
//    private Donation donations; // one donor can have more than one donations
//    private LocalDate donationDate;

    public Donor(String donorID, String name, int contactNo, String email, String type, String entityType) {
        this.donorID = donorID;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.type = type;
        this.entityType = entityType;
//        this.donations = donations;
//        this.donationDate = donationDate;
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

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
        
//    public double getDonations() {
//        return donations;
//    }
//
//    public void setDonations(double donations) {
//        this.donations = donations;
//    }

//    public LocalDate getDonationDate() {
//        return donationDate;
//    }
//
//    public void setDonationDate(LocalDate donationDate) {
//        this.donationDate = donationDate;
//    }
           
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Donor other = (Donor) obj;
        return Objects.equals(this.donorID, other.donorID);
    }

    @Override
    public String toString() {
        return String.format("%-10s, %-15s, %-15d, %-15s, %-10s, %-15s", donorID, name, contactNo, email, type, entityType);
    }
}
