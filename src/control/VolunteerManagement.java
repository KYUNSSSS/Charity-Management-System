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
import java.io.File;
import java.io.IOException;
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
    
    private int lastVolunteerNumber = 0;
    
    public VolunteerManagement() {
        File file = new File("volunteer.txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File not found. A new file has been created.");
                } else {
                    System.out.println("Failed to create a new file.");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
        volunteerList = volunteerDAO.retrieveFromFile();
        for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = volunteerList.getEntry(i);
            volunteerMap.put(volunteer.getVolunteerID(), volunteer); // Populate HashMap
            updateLastVolunteerNumber(volunteer.getVolunteerID());
        }
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
                    break;
                case 2:                    
                    removeVolunteer();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 3:
                    searchVolunteerDetails();
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 4:
                    assignEvents();
                    break;
                case 5:
                    searchEventVolunteer(volunteerList);
                    MessageUI.pressAnyKeyToContinue();
                    break;
                case 6:
                    listAllVolunteers();
                    break;
                case 7:
                    filterVolunteers();
                    break;
                case 8:
                    generateReport();
                    break;
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
        removeVolunteerById(volunteerID);
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
            volunteerUI.listVolunteer(foundVolunteer);
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
        listAllVolunteers();
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
                System.out.println(volunteer.getVolunteerID()+"\n"+volunteer.getEventAssigned());
                volunteerDAO.updateVolunteer(volunteer);
                volunteerUI.listVolunteer(volunteer);
            }
        }
        
    }
    
    public void searchEventVolunteer(ListInterface list) {
        String volunteerID = volunteerUI.inputVolunteerID();
        if (volunteerMap.containsKey(volunteerID)) {
            volunteerUI.listEvent(volunteerMap.get(volunteerID));
        } else if(list.getNumberOfEntries() == 0){
           System.err.print("Volunteer does not exist");
        } else{
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
    
    public void listAllVolunteers() {
        volunteerUI.displayAllVolunteers(volunteerList);
    }
    
    public void filterVolunteers() {
        int filterChoice = volunteerUI.getFilterChoice(); // Prompt user to choose filter type
        ListInterface<Volunteer> filteredVolunteers = null;

        switch (filterChoice) {
            case 1:
                String volunteerType = volunteerUI.inputVolunteerType();
                filteredVolunteers = filterVolunteer.filterByVolunteerType(volunteerList,volunteerType);
                volunteerUI.displayFilteredVolunteers(filteredVolunteers);
                break;
            case 2:
                String event = volunteerUI.inputEvent();
                filteredVolunteers = filterVolunteer.filterByEvent(volunteerList, event);
                volunteerUI.displayFilteredVolunteers(filteredVolunteers);
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                return;
        }
    }
   
    public void generateReport() {
        String[] event = {"Acts of Kindness Fund", "Share the Love Project", "Together for Change"};
        String[] volunteerType = {"Registration", "Support", "Logistic", "Crowd Control"};
        int actRegCount = 0, actSupCount = 0, actLogCount = 0, actCrcCount = 0;
        int shareRegCount = 0, shareSupCount = 0, shareLogCount = 0, shareCrcCount = 0;
        int togetherRegCount = 0, togetherSupCount = 0, togetherLogCount = 0, togetherCrcCount = 0;
        int actTotal, shareTotal, togetherTotal;
        int totalVolunteer = volunteerList.getNumberOfEntries();
        int totalReg = 0, totalSup = 0, totalLog = 0, totalCrc = 0;
        for (int i = 1; i  <= totalVolunteer;i++) {
           Volunteer volunteer = volunteerList.getEntry(i); 
           if(volunteer.getVolunteerType().contains(volunteerType[0])){
               totalReg++;
           } else if(volunteer.getVolunteerType().contains(volunteerType[1])){
               totalSup++;
           } else if(volunteer.getVolunteerType().contains(volunteerType[2])) {
               totalLog++;
           } else if(volunteer.getVolunteerType().contains(volunteerType[3])) {
               totalCrc++;
           }
            if(volunteer.getVolunteerType().contains(volunteerType[0])) {
                if(volunteer.getEventAssigned().contains(event[0])) {
                    actRegCount++;
                }
                if(volunteer.getEventAssigned().contains(event[1])) {
                    shareRegCount++;
                } 
                if (volunteer.getEventAssigned().contains(event[2])) {
                    togetherRegCount++;
                }
            }
            
            else if(volunteer.getVolunteerType().contains(volunteerType[1])) {
                if(volunteer.getEventAssigned().contains(event[0])) {
                    actSupCount++;
                } 
                if(volunteer.getEventAssigned().contains(event[1])) {
                    shareSupCount++;
                } 
                
                if (volunteer.getEventAssigned().contains(event[2])) {
                    togetherSupCount++;
                }
            }
            
            else if(volunteer.getVolunteerType().contains(volunteerType[2])) {
                if(volunteer.getEventAssigned().contains(event[0])) {
                    actLogCount++;
                }
                if(volunteer.getEventAssigned().contains(event[1])) {
                    shareLogCount++;
                }
                if (volunteer.getEventAssigned().contains(event[2])) {
                    togetherLogCount++;
                }
            }
            
            else if(volunteer.getVolunteerType().contains(volunteerType[3])) {
                if(volunteer.getEventAssigned().contains(event[0])) {
                    actCrcCount++;
                } 
                if(volunteer.getEventAssigned().contains(event[1])) {
                    shareCrcCount++;
                } 
                if (volunteer.getEventAssigned().contains(event[2])) {
                    togetherCrcCount++;
                }
            }
        }
        actTotal = actRegCount + actSupCount + actLogCount + actCrcCount;
        shareTotal = shareRegCount + shareSupCount + shareLogCount + shareCrcCount;
        togetherTotal = togetherRegCount + togetherSupCount + togetherLogCount + togetherCrcCount;
        
        volunteerUI.generateSummaryReport(actRegCount, actSupCount, actLogCount, actCrcCount, shareRegCount, shareSupCount, shareLogCount, shareCrcCount, togetherRegCount, togetherSupCount, togetherLogCount, togetherCrcCount, actTotal, shareTotal, togetherTotal, totalVolunteer, totalReg, totalSup, totalLog, totalCrc);
        
    }
    
    private void updateLastVolunteerNumber(String volunteerID) {
        String numberPart = volunteerID.substring(1); // Extract the numeric part (e.g., "001" from "V001")
        int number = Integer.parseInt(numberPart);
        if (number > lastVolunteerNumber) {
            lastVolunteerNumber = number;
        }
    }
    
    private String generateNextVolunteerID() {
        lastVolunteerNumber++;
        return String.format("V%03d", lastVolunteerNumber); // Format as V001, V002, etc.
    }
    
    
    
    public static void main(String[] args) {
        VolunteerManagement volunteerMaintenance = new VolunteerManagement();
        volunteerMaintenance.runVolunteerManagement();
    }
}

