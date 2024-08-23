/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import entity.Volunteer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class VolunteerManagementUI {
    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\n******VOLUNTEER MANAGEMENT******");
        System.out.println("1. Add a New Volunteer");
        System.out.println("2. Remove a Volunteer");
        System.out.println("3. Search Volunteer Details");
        System.out.println("4. Assign Volunteer to Events");
        System.out.println("5. Search Events under Volunteer"); 
        System.out.println("6. List Volunteers"); 
        System.out.println("7. Filter Volunteer");
        System.out.println("8. Generate Summary Reports");
        System.out.println("0. Quit");
        System.out.print("Enter choice(0-8): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    public void listAllVolunteers(String outputStr) {
    System.out.println("\nList of Volunteer:\n" + outputStr);
  }
    
 
  public String inputVolunteerID() {
    System.out.print("Enter Volunteer ID: ");
    String id = scanner.nextLine();
    return  id;
  }
  
  public String inputVolunteerType() {
    System.out.print("Enter Volunteer Type(Registration/Support/Logistic/Crowd Control): ");
    String type = scanner.nextLine();
    return  type;
  }

  public String inputVolunteerName() {
    System.out.print("Enter Volunteer Name: ");
    String name = scanner.nextLine();
    return name;
  }
  public String inputVolunteerEmail() {
    System.out.print("Enter Volunteer Email: ");
    String email = scanner.nextLine();
    return email;
  }

  public int inputPhoneNum() {
    System.out.print("Enter Phone Number: ");
    int phone = scanner.nextInt();
    scanner.nextLine();
    return phone;
  }
  
  public Volunteer inputVolunteerDetails() {
    String id = inputVolunteerID();
    String type = inputVolunteerType();
    String name = inputVolunteerName();
    int phone = inputPhoneNum();
    String email = inputVolunteerEmail();
    System.out.println();
    return new Volunteer(id,type,name,phone,email);
  }
    public int getFilterChoice() {
        System.out.println("Filter by: ");
        System.out.println("1. Volunteer Type");
//      System.out.println("2. Date Range");
//      System.out.println("3. Donation Amount Range");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
   }
    
    public void displayFilteredDonees(ListInterface<Volunteer> volunteers) {
        if (volunteers.isEmpty()) {
            System.out.println("No matching volunteers found.");
        } else {
            for (int i = 1; i <= volunteers.getNumberOfEntries(); i++) {
                System.out.println(volunteers.getEntry(i));
            }
        }
    }
}
