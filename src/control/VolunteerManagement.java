/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedList;
import adt.ListInterface;
import boundary.VolunteerManagementUI;
import dao.VolunteerDAO;
import entity.Volunteer;
import utility.MessageUI;

/**
 *
 * @author Asus
 */
public class VolunteerManagement {
    private ListInterface<Volunteer> volunteerList = new LinkedList<>();
    private VolunteerDAO VolunteerDAO = new VolunteerDAO();
    private VolunteerManagementUI volunteerUI = new VolunteerManagementUI();
    
    public VolunteerManagement() {
        volunteerList = VolunteerDAO.retrieveFromFile();
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
//                    assignVolunteer();
                    break;
                case 5:
//                     searchEventVolunteer();
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
    Volunteer newVolunteer = volunteerUI.inputVolunteerDetails();
    volunteerList.add(newVolunteer);
    VolunteerDAO.saveToFile(getAllVolunteer());
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
            VolunteerDAO.saveToFile(getAllVolunteer());
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
                filteredVolunteers = volunteerList.filterByVolunteerType(volunteerType);
                break;
    //        case 2:
    //            LocalDate startDate = doneeUI.inputStartDate();
    //            LocalDate endDate = doneeUI.inputEndDate();
    //            filteredDonees = doneeList.filterByDateRange(startDate, endDate);
    //            break;
    //        case 3:
    //            double minAmount = doneeUI.inputMinAmount();
    //            double maxAmount = doneeUI.inputMaxAmount();
    //            filteredDonees = doneeList.filterByAmountRange(minAmount, maxAmount);
    //            break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                return;
        }

        volunteerUI.displayFilteredVolunteers(filteredVolunteers);
    }
    
    public static void main(String[] args) {
        VolunteerManagement volunteerMaintenance = new VolunteerManagement();
        volunteerMaintenance.runVolunteerManagement();
    }
}

