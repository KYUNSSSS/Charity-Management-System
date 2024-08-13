/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author xuan
 */
class Donation {
    private String donationID;
    private Donor donor;
    private double cashAmount;
    private Donee donee;

    public Donation(String donationId, Donor donor, double cashAmount) {
        this.donationID = donationID;
        this.donor = donor;
        this.cashAmount = cashAmount;
    }

}
