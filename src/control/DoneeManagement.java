/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import dao.*;
import adt.*;
import boundary.*;
import utility.*;
import entity.Donee;
import java.time.LocalDate;
/**
 *
 * @author Hp
 */
public class DoneeManagement {
    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeManagementUI doneeUI = new DoneeManagementUI();
    
    public DoneeManagement() {
    doneeList = doneeDAO.retrieveFromFile();
  }
    
    public void runDoneeManagement() {
        int choice = 0;
        do {
          choice = doneeUI.getMenuChoice();
          switch(choice) {
            case 0:
              MessageUI.displayExitMessage();
              break;
            case 1:
              addNewDonee();
              doneeUI.listAllDonees(getAllDonee());
              break;
            case 2:
              updateDoneeDetails();
              doneeUI.listAllDonees(getAllDonee());
              break;
             case 3:
             searchDoneeDetails();
             //doneeUI.listAllDonees(getAllDonee());
              break;
             case 4:
             // doneeUI.listAllDonees(getAllDonee());
              break;
             case 5:
              filterDonees();
             // doneeUI.listAllDonees(getAllDonee());
              break;
              case 6:
              removeDonee();
              doneeUI.listAllDonees(getAllDonee());
              break;
            default:
              MessageUI.displayInvalidChoiceMessage();
          } 
        } while (choice != 0);
      }
        
    public void addNewDonee() {
    Donee newDonee = doneeUI.inputDoneeDetails();
    doneeList.add(newDonee);
    doneeDAO.saveToFile(getAllDonee());
  }
    
    public void removeDonee() {
        String doneeID = doneeUI.inputDoneeID();
        boolean result = removeDoneeById(doneeID);
    }
    public boolean removeDoneeById(String doneeID) {
    boolean removed = false;
    
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
        Donee donee = doneeList.getEntry(i);
        if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
            doneeList.remove(i);
            removed = true;
            break;
        }
    }

    if (removed) {
        doneeDAO.saveToFile(getAllDonee());
        System.out.println("Donee with ID " + doneeID + " has been removed.");
    } else {
        System.out.println("Donee with ID " + doneeID + " not found.");
    }
    
    return removed;
}
    public void updateDoneeDetails() {
        String doneeID = doneeUI.inputDoneeID();
        boolean result = updateDoneeDetails(doneeID);
    }
    public boolean updateDoneeDetails(String doneeID) {
    boolean updated = false;
    
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
        Donee donee = doneeList.getEntry(i);
        if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
            Donee updatedDonee = doneeUI.inputDoneeDetails(); // Prompt user for new details
            doneeList.replace(i, updatedDonee);
            updated = true;
            break;
        }
    }

    if (updated) {
        doneeDAO.saveToFile(getAllDonee());
        System.out.println("Donee with ID " + doneeID + " has been updated.");
    } else {
        System.out.println("Donee with ID " + doneeID + " not found.");
    }
    
    return updated;
}
        public void searchDoneeDetails() {
        String doneeID = doneeUI.inputDoneeID();
        Donee foundDonee = searchDoneeByID(doneeID);
        if (foundDonee != null) {
            System.out.println(foundDonee);
        } else {
            System.out.println("Donee not found.");
        }
    }
    
    public Donee searchDoneeByID(String doneeID) {
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
        Donee donee = doneeList.getEntry(i);
        if (donee.getDoneeID().equals(doneeID)) {
            return donee;
        }
    }
    return null; // Return null if not found
}
    public String getAllDonee() {
    String outputStr = "";
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
      outputStr += doneeList.getEntry(i) + "\n";
    }
    return outputStr;
  }
    public void filterDonees() {
    int filterChoice = doneeUI.getFilterChoice(); // Prompt user to choose filter type
    ListInterface<Donee> filteredDonees = null;

    switch (filterChoice) {
        case 1:
            String doneeType = doneeUI.inputDoneeType();
            filteredDonees = doneeList.filterByDoneeType(doneeType);
            break;
        case 2:
            LocalDate startDate = doneeUI.inputStartDate();
            LocalDate endDate = doneeUI.inputEndDate();
            filteredDonees = doneeList.filterByDateRange(startDate, endDate);
            break;
        case 3:
            double minAmount = doneeUI.inputMinAmount();
            double maxAmount = doneeUI.inputMaxAmount();
            filteredDonees = doneeList.filterByAmountRange(minAmount, maxAmount);
            break;
        default:
            MessageUI.displayInvalidChoiceMessage();
            return;
    }

    doneeUI.displayFilteredDonees(filteredDonees);
}
   

//    public void updateNewProduct() {
//    Donee newProduct = DoneeManagementUI.inputDoneeDetails();
//    productList.add(newProduct);
//    productDAO.saveToFile(productList);
//  }
      
    public static void main(String[] args) {
    DoneeManagement doneeMaintenance = new DoneeManagement();
    doneeMaintenance.runDoneeManagement();
  }
}
