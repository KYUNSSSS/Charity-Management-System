/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.HashMap;
import adt.LinkedList;
import adt.ListInterface;
import boundary.VolunteerManagementUI;
import dao.VolunteerDAO;
import entity.Volunteer;
import java.util.Scanner;
import utility.Filter;
import utility.MessageUI;

/**
 *
 * @author Asus
 */
public class VolunteerManagement {
    private ListInterface<Volunteer> volunteerList = new LinkedList<>();
    private VolunteerDAO volunteerDAO = new VolunteerDAO();
    private VolunteerManagementUI volunteerUI = new VolunteerManagementUI();
    private HashMap<String, Volunteer> volunteerMap = new HashMap<>();
    private Filter<Volunteer> filterVolunteer = new Filter<>();
    
    private int lastDoneeNumber = 0;
    
    public VolunteerManagement() {
        volunteerList = volunteerDAO.retrieveFromFile();
    }
    
    public void runVolunteerManagement() {
        int choice = 0;
        do {
            choice = volunteerUI.getMenuChoice();
            switch(choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewVolunteer();
                    volunteerUI.listAllVolunteers(getAllVolunteer());
                    break;
                case 2:
                    removeVolunteer();
                    volunteerUI.listAllVolunteers(getAllVolunteer());
                    break;
                case 3:
                    searchVolunteerDetails();
                    break;
                case 4:
                    assignEvents();
                    break;
                case 5:
                    searchEventVolunteer();
                    break;
                case 6:
                    volunteerUI.listAllVolunteers(getAllVolunteer());
                    break;
                case 7:
                    filterVolunteers();
                    break;
                case 8:
//                    generateReport();
                default:
                    MessageUI.displayInvalidChoiceMessage();
            } 
        } while (choice != 0);
      }
    
    public void addNewVolunteer() {
        String newVolunteerID = generateNextVolunteerID();
        Volunteer newVolunteer = volunteerUI.inputVolunteerDetails();
        newVolunteer.setVolunteerID(newVolunteerID);
        volunteerList.add(newVolunteer);
        volunteerUI.listVolunteer(newVolunteer);
        MessageUI.pressAnyKeyToContinue();
        volunteerMap.put(newVolunteer.getVolunteerID(), newVolunteer);
        volunteerDAO.saveToFile(getAllVolunteer());
  }
    
    public void removeVolunteer() {
        String volunteerID = volunteerUI.inputVolunteerID();
        boolean result = removeVolunteerById(volunteerID);
    }
    
    public boolean removeVolunteerById(String volunteerID) {
        boolean removed = false;

        for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = volunteerList.getEntry(i);
            if (volunteer.getVolunteerID().equalsIgnoreCase(volunteerID)) {
                volunteerList.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            volunteerDAO.saveToFile(getAllVolunteer());
            System.out.println("Volunteer with ID " + volunteerID + " has been removed.");
        } else {
            System.out.println("Volunteer with ID " + volunteerID + " not found.");
        }

        return removed;
    }
    
    public void searchVolunteerDetails() {
        String volunteerID = volunteerUI.inputVolunteerID();
        Volunteer foundVolunteer = searchVolunteerByID(volunteerID);
        if (foundVolunteer != null) {
            System.out.println(foundVolunteer);
        } else {
            System.out.println("Volunteer not found.");
        }
    }
    
    public Volunteer searchVolunteerByID(String volunteerID) {
        for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = volunteerList.getEntry(i);
            if (volunteer.getVolunteerID().equals(volunteerID)) {
                return volunteer;
            }
        }
        return null; // Return null if not found
    }
    
    public void assignEvents(){
        Scanner scanner = new Scanner(System.in);
        volunteerUI.listAllVolunteers(getAllVolunteer());
        System.out.print("Enter ID to assign event: ");
        String volunteerID = scanner.nextLine();
        for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = volunteerList.getEntry(i);
            if (volunteer.getVolunteerID().equals(volunteerID)) {
                System.out.println("""
                                   
                                   1. Acts of Kindness Fund
                                   2. Share the Love Project
                                   3. Together for Change
                                   Choose event to assign: """);
                int eventChoice = scanner.nextInt();
                switch(eventChoice){
                    case 1:
                        if (volunteer.getEventAssigned().equals("None")) {
                            volunteer.setEventAssigned("Acts of Kindness Fund");  
                        } else if(volunteer.getEventAssigned().contains("Acts of Kindness Fund") || volunteer.getEventAssigned().equals("Acts of Kindness Fund")){
                            System.out.println("\nEvent exist.\n");
                        } else {
                            volunteer.addEventAssigned("Acts of Kindness Fund");
                        }
                        break;
                    case 2:
                        if (volunteer.getEventAssigned().equals("None")) {
                            volunteer.setEventAssigned("Share the Love Project");
                        } else if (volunteer.getEventAssigned().contains("Share the Love Project") || volunteer.getEventAssigned().equals("Share the Love Project")) {
                            System.out.println("\nEvent exist.\n");
                        } else {
                            volunteer.addEventAssigned("Share the Love Project");
                        }
                        break;
                    case 3:
                        if (volunteer.getEventAssigned().equals("None")) {
                            volunteer.setEventAssigned("Together for Change");
                        } else if(volunteer.getEventAssigned().contains("Together for Change") || volunteer.getEventAssigned().equals("Together for Change")){
                            System.out.println("\nEvent exist.\n");
                        } else {
                            volunteer.addEventAssigned("Together for Change");
                        }
                        break;
                    default:
                        break;
                }
                volunteerUI.listVolunteer(volunteer);
            }
        }
        
    }
    
    public void searchEventVolunteer() {
        String volunteerID = volunteerUI.inputVolunteerID();
        if (volunteerMap.containsKey(volunteerID)) {
            volunteerUI.listEvent(volunteerMap.get(volunteerID));
        } else {
            System.err.println("No event assigned.");
        }
    }
    
    public String getAllVolunteer() {
        String outputStr = "";
        for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
          outputStr += volunteerList.getEntry(i) + "\n";
        }
        return outputStr;
    }
    
    public void filterVolunteers() {
        int filterChoice = volunteerUI.getFilterChoice(); // Prompt user to choose filter type
        ListInterface<Volunteer> filteredVolunteers = null;

        switch (filterChoice) {
            case 1:
                String volunteerType = volunteerUI.inputVolunteerType();
                filteredVolunteers = filterVolunteer.filterByVolunteerType(volunteerList,volunteerType);
                break;
            case 2:
                String event = volunteerUI.inputEvent();
                filteredVolunteers = filterVolunteer.filterByEvent(volunteerList, event);
                break;
    //        case 3:
    //            double minAmount = doneeUI.inputMinAmount();
    //            double maxAmount = doneeUI.inputMaxAmount();
    //            filteredDonees = doneeList.filterByAmountRange(minAmount, maxAmount);
    //            break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                return;
        }

//        volunteerUI.displayFilteredVolunteers(filteredVolunteers);
    }
   
    
    private String generateNextVolunteerID() {
        lastDoneeNumber++;
        return String.format("V%03d", lastDoneeNumber); // Format as DE001, DE002, etc.
    }
    
    
    
    public static void main(String[] args) {
        VolunteerManagement volunteerMaintenance = new VolunteerManagement();
        volunteerMaintenance.runVolunteerManagement();
    }
}

