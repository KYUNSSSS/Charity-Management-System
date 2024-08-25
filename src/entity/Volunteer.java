/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Asus
 */
public class Volunteer implements Serializable{
  private String volunteerID;
  private String volunteerType;
  private String volunteerName;
  private int volunteerPhoneNum;
  private String volunteerEmail;

  public Volunteer() {
  }

    public Volunteer(String volunteerID, String volunteerType, String volunteerName, int volunteerPhoneNum, String volunteerEmail) {
        this.volunteerID = volunteerID;
        this.volunteerType = volunteerType;
        this.volunteerName = volunteerName;
        this.volunteerPhoneNum = volunteerPhoneNum;
        this.volunteerEmail = volunteerEmail;
    }

    public String getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    public String getVolunteerType() {
        return volunteerType;
    }

    public void setVolunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public int getVolunteerPhoneNum() {
        return volunteerPhoneNum;
    }

    public void setVolunteerPhoneNum(int volunteerPhoneNum) {
        this.volunteerPhoneNum = volunteerPhoneNum;
    }

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
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
        return String.format("%s, %s, %s, %d, %s", volunteerID, volunteerType, volunteerName, volunteerPhoneNum, volunteerEmail);
    }
}


