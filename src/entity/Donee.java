package entity;

import java.io.Serializable;


/**
 *
 * @author Kow Yun Shen
 */
public class Donee implements Serializable{
  private String doneeID;
  private String doneeType;
  private String doneeName;
  private int doneePhoneNum;
  private String doneeEmail;
  private String doneeLocation;

  public Donee() {
  }

    public Donee(String doneeID, String doneeType, String doneeName, int doneePhoneNum, String doneeEmail, String doneeLocation) {
        this.doneeID = doneeID;
        this.doneeType = doneeType;
        this.doneeName = doneeName;
        this.doneePhoneNum = doneePhoneNum;
        this.doneeEmail = doneeEmail;
        this.doneeLocation = doneeLocation;
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

    public String getDoneeLocation() {
        return doneeLocation;
    }

    public void setDoneeLocation(String doneeLocation) {
        this.doneeLocation = doneeLocation;
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
    return String.format("%s, %s, %s,%d,%s,%s", doneeID, doneeType,doneeName, doneePhoneNum,doneeEmail,doneeLocation);
  }
  
  

}
