package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Kat Tan
 */
public class Donee implements Serializable{
  private String doneeID;
  private String doneeType;
  private String doneeName;
  private int doneePhoneNum;
  private String doneeEmail;
  private double donationAmount;
  private LocalDate donationDate;

  public Donee() {
  }

    public Donee(String doneeID, String doneeType, String doneeName, int doneePhoneNum, String doneeEmail, double donationAmount, LocalDate donationDate) {
        this.doneeID = doneeID;
        this.doneeType = doneeType;
        this.doneeName = doneeName;
        this.doneePhoneNum = doneePhoneNum;
        this.doneeEmail = doneeEmail;
        this.donationAmount = donationAmount;
        this.donationDate = donationDate;
    }

    public Donee(String doneeID) {
        this.doneeID = doneeID;
    }
    
  

    public String getDoneeID() {
        return doneeID;
    }

    public void setDoneeID(String doneeID) {
        this.doneeID = doneeID;
    }

    public String getDoneeType() {
        return doneeType;
    }

    public void setDoneeType(String doneeType) {
        this.doneeType = doneeType;
    }

    public String getDoneeName() {
        return doneeName;
    }

    public void setDoneeName(String doneeName) {
        this.doneeName = doneeName;
    }

    public int getDoneePhoneNum() {
        return doneePhoneNum;
    }

    public void setDoneePhoneNum(int doneePhoneNum) {
        this.doneePhoneNum = doneePhoneNum;
    }

    public String getDoneeEmail() {
        return doneeEmail;
    }

    public void setDoneeEmail(String doneeEmail) {
        this.doneeEmail = doneeEmail;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

 

//  @Override
//  public int hashCode() {
//    int hash = 3;
//    return hash;
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) {
//      return true;
//    }
//    if (obj == null) {
//      return false;
//    }
//    if (getClass() != obj.getClass()) {
//      return false;
//    }
//    final Product other = (Product) obj;
//    if (!Objects.equals(this.number, other.number)) {
//      return false;
//    }
//    return true;
//  }
//
  @Override
  public String toString() {
    return String.format("%s, %s, %s,%d,%s,%f,%s", doneeID, doneeType,doneeName, doneePhoneNum,doneeEmail,donationAmount,donationDate);
  }
  
  

}
