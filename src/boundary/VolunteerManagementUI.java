/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import entity.Volunteer;
import java.util.Scanner;
import utility.Validator;

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
        while(true) {
           System.out.print("Enter Volunteer ID: ");
            String id = scanner.nextLine();
            return id; 
        }
    }
    
    public String inputVolunteerType() {
        while(true) {
            System.out.println("""
                         Reg - Registration
                         Sup - Support
                         Log - Logistic
                         Crc - Crowd Control
                         Enter Volunteer Type(Eg: Reg/Sup/Log/Crc): 
                         """);
            String type = scanner.nextLine();
            if(type.equalsIgnoreCase("Reg")) {
                type = "Registration";
                return  type;
            } else if (type.equalsIgnoreCase("Sup")) {
                type = "Support";
                return  type;
            } else if (type.equalsIgnoreCase("Log")) {
                type = "Logistic";
                return  type;
            } else if (type.equalsIgnoreCase("Crc")) {
                type = "Crowd Control";
                return  type;
            } else {
                System.err.println("Enter Reg/Sup/Log/Crc only.");
            }
            
        }

    }

    public String inputVolunteerName() {
        while(true) {
           System.out.print("Enter Volunteer Name: ");
            String name = scanner.nextLine();
            
            if (Validator.isAlphabetic(name)) {
                return name;
            }
            System.err.println("Please enter alphabets only."); 
        }
    }
    
    public String inputVolunteerEmail() {
        while(true) {
            System.out.print("Enter Volunteer Email: ");
            String email = scanner.nextLine();
            
            if (Validator.isValidEmail(email)) {
                return email;
            }
            System.err.println("Please email in correct format.");
        }
    
  }

    public int inputPhoneNum() {
        while(true) {
            System.out.print("Enter Phone Number: ");
            int phone = scanner.nextInt();
            scanner.nextLine();
            return phone;        
        }
    }

    public String inputEventAssigned() {
        return "None";
    }
  
  public String inputEvent() {
      System.out.println("""
                        1. Acts of Kindness Fund
                        2. Share the Love Project
                        3. Together for Change
                       Enter event number(eg: 1/2/3): """);
      int event = scanner.nextInt();
      String eventName = null;
      switch(event) {
          case 0:
              break;
          case 1:
              eventName = "Acts of Kindness Fund";
              break;
          case 2:
              eventName = "Share the Love Project";
              break;
          case 3:
              eventName = "Together for Change";
              break;
          default:
                System.out.println("Invalid event number. Please enter 1, 2, or 3.");
      }
      return eventName;
  }
  
  public Volunteer inputVolunteerDetails() {
        String type = inputVolunteerType();
        String name = inputVolunteerName();
        int phone = inputPhoneNum();
        String email = inputVolunteerEmail();
        String eventAssigned = inputEventAssigned();
        System.out.println("Volunteer Details Registered.");
        return new Volunteer("V000", type, name, phone, email, eventAssigned);
  }
    public int getFilterChoice() {
        while(true) {
            System.out.println("Filter by: ");
            System.out.println("1. Volunteer Type");
            System.out.println("2. Event");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        }
   }
    
    public void displayFilteredVolunteers(ListInterface<Volunteer> volunteers) {
        if (volunteers.isEmpty()) {
            System.out.println("No matching volunteers found.");
        } else {
            for (int i = 1; i <= volunteers.getNumberOfEntries(); i++) {
                System.out.println(volunteers.getEntry(i));
            }
        }
    }
    
    public void listVolunteer(Volunteer volunteer) {
        System.out.println("***Profile***\nVolunteer ID: " + volunteer.getVolunteerID() + "\nName: " + volunteer.getVolunteerName() + "\nType: " + volunteer.getVolunteerType() + "\nPhone Number: " + volunteer.getVolunteerPhoneNum() + "\nEmail: " + volunteer.getVolunteerEmail() + "\nEvent Assigned: " + volunteer.getEventAssigned() +  "\n**********");
    }
    
    public void listEvent(Volunteer volunteer) {
        System.out.println("Event(s) under volunteer ID " + volunteer.getVolunteerID() + ": " + volunteer.getEventAssigned());
    }
    
    public void generateSummaryReport(int actRegCount, int actSupCount, int actLogCount, int actCrcCount, int shareRegCount, int shareSupCount, int shareLogCount, int shareCrcCount, int togetherRegCount, int togetherSupCount, int togetherLogCount, int togetherCrcCount, int actTotal, int shareTotal, int togetherTotal, int totalVolunteer) {
        
        
        System.out.printf("%75s", "Summary report for Number of Volunteer in Event\n");
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-25s | %-12s | %-12s | %-12s | %-15s | %-6s |\n","Event","Registration","Support", "Logistic", "Crowd Control", "Total");
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-25s | %-12d | %-12d | %-12d | %-15d | %-6s |\n","Act of Kindness Fund", actRegCount, actSupCount, actLogCount, actCrcCount, actTotal);
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-25s | %-12d | %-12d | %-12d | %-15d | %-6s |\n","Share the Love Project", shareRegCount, shareSupCount, shareLogCount, shareCrcCount, shareTotal);
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-25s | %-12d | %-12d | %-12d | %-15d | %-6s |\n","Together for Change", togetherRegCount, togetherSupCount, togetherLogCount, togetherCrcCount, togetherTotal);
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        
        System.out.println("\nTotal Number of Volunteer: " + totalVolunteer);
    }
}
