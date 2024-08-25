/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import dao.*;
import adt.*;
import boundary.*;
import utility.*;
import entity.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
/**
 *
 * @author Hp
 */
public class DoneeManagement {
    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeManagementUI doneeUI = new DoneeManagementUI();
    private ListInterface<Distribution> distributeList = new LinkedList<>();
    private DistributionDAO distributeDAO = new DistributionDAO();
    private HashMap<String, Donee> doneeMap = new HashMap<>();
    private HashMap<String, ListInterface<Distribution>> donationMap = new HashMap<>();
    private FilterInterface<Donee> filterDonee = new Filter<>();
    private FilterInterface<Distribution> filterDonation = new Filter<>();

     private int lastDoneeNumber = 0;
    
    public DoneeManagement() {
     File file = new File("Donee.txt");
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
    doneeList = doneeDAO.retrieveFromFile();
     distributeList = distributeDAO.retrieveFromFile();
     for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            doneeMap.put(donee.getDoneeID(), donee); // Populate HashMap
            updateLastDoneeNumber(donee.getDoneeID());
        }
     for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
        Distribution distribution = distributeList.getEntry(i);
        String doneeID = distribution.getDoneeID();
        
        if (!donationMap.containsKey(doneeID)) {
            donationMap.put(doneeID, new LinkedList<Distribution>());
        }
        
        donationMap.get(doneeID).add(distribution); // Populate HashMap for Donations
    }
  }
    
    public void runDoneeManagement() {
        int choice = 0;
        do {
          choice = doneeUI.getMenuChoice();
          switch(choice) {
            case 0:
             driver driver = new driver();
              driver.runDriver();
              break;
            case 1:
              addNewDonee();//done
              doneeUI.listAllDonees(getAllDonee());
              break;
            case 2:
              updateDoneeDetails();//done
              doneeUI.listAllDonees(getAllDonee());
              break;
             case 3:
             searchDoneeDetails();//done
             //doneeUI.listAllDonees(getAllDonee());
              break;
             case 4:
             listDoneeDonation();//done
              break;
             case 5:
              filterDonees();
             // doneeUI.listAllDonees(getAllDonee());
              break;
              case 6:
              removeDonee();
              doneeUI.listAllDonees(getAllDonee());
              break;
              case 7:
              generateSummaryReport();
              break;
            default:
              MessageUI.displayInvalidChoiceMessage();
          } 
        } while (choice != 0);
      }
        
    public void addNewDonee() {
    String newDoneeID = generateNextDoneeID();
    Donee newDonee = doneeUI.inputDoneeDetails();
    newDonee.setDoneeID(newDoneeID);
    doneeList.add(newDonee);
    doneeUI.listDonee(newDonee);
    MessageUI.pressAnyKeyToContinue();
    doneeMap.put(newDonee.getDoneeID(), newDonee);
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
            doneeMap.remove(doneeID);
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
           doneeUI.listDonee(foundDonee);
        } else {
            System.out.println("Donee not found.");
            MessageUI.pressAnyKeyToContinue();
        }
    }
         public void listDoneeDonation() {
        String doneeID = doneeUI.inputDoneeID();
        listDonationsByDoneeID(doneeID);
    }
  public void listDonationsByDoneeID(String doneeID) {
    ListInterface<Distribution> donations = donationMap.get(doneeID);
    
    if (donations != null && !donations.isEmpty()) {
        System.out.println("Donation For "+doneeMap.get(doneeID).getDoneeName()+"\n**************");
        System.out.printf("%-15s %-20s %-15s %-10s %-10s %-10s %-20s\n", 
                          "Distribution ID", "Item Name", "Category", 
                          "Quantity", "Amount", "Status", "Distribution Date");
        System.out.println("**************");

        double totalDonations = 0;
        int totalGoods = 0;
        double totalCash = 0;

        for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
            Distribution donation = donations.getEntry(i);

            System.out.printf("%-15s %-20s %-15s %-10d %-10.2f %-10s %-20s\n", 
                              donation.getDistributionID(), 
                              donation.getItemName(), 
                              donation.getCategory(), 
                              donation.getQuantity(), 
                              donation.getAmount(), 
                              donation.getStatus(), 
                              donation.getDistributionDate().toString());

            totalDonations++;
            totalGoods += donation.getQuantity();
            totalCash += donation.getAmount();
        }

        System.out.println("**************");
        System.out.println("Total Donations: " + totalDonations);
        System.out.println("Total Donated Goods: " + totalGoods);
        System.out.println("Total Donated Cash: " + String.format("%.2f", totalCash));
        System.out.println("**************");
        MessageUI.pressAnyKeyToContinue();

    } else {
        System.out.println("No donations found for Donee ID: " + doneeID);
    }
}
    public Donee searchDoneeByID(String doneeID) {
     return doneeMap.get(doneeID);
}
    public String getAllDonee() {
    String outputStr = "";
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
      outputStr += doneeList.getEntry(i) + "\n";
    }
    return outputStr;
  }
    public void filterDonees() {
        String doneeID;
    int filterChoice = doneeUI.getFilterChoice(); // Prompt user to choose filter type
    ListInterface<Donee> filteredDonees ;
    ListInterface<Distribution> filteredInfoByDoneeID = null;
    

    switch (filterChoice) {
        case 1:
            String doneeType = doneeUI.inputDoneeType();
            filteredDonees = filterDonee.filterByType(doneeList,doneeType);
            doneeUI.displayFilteredDonees(filteredDonees);
            break;
        case 2:
            doneeID = doneeUI.inputDoneeID();
            LocalDate startDate = doneeUI.inputStartDate();
            LocalDate endDate = doneeUI.inputEndDate();
            filteredInfoByDoneeID = filterDonation.filterByDateAndDoneeID(distributeList,startDate, endDate,doneeID);
            doneeUI.displayFilteredDoneesByDoneeID(filteredInfoByDoneeID);
            break;
        case 3:
            doneeID = doneeUI.inputDoneeID();
            double minAmount = doneeUI.inputMinAmount();
            double maxAmount = doneeUI.inputMaxAmount();
            filteredInfoByDoneeID = filterDonation.filterByAmountAndDoneeID(distributeList,minAmount, maxAmount,doneeID);
            doneeUI.displayFilteredDoneesByDoneeID(filteredInfoByDoneeID);
            break;
        case 4:
            String doneeLocation = doneeUI.inputDoneeLocation();
            filteredDonees = filterDonee.filterByLocation(doneeList,doneeLocation);
            doneeUI.displayFilteredDonees(filteredDonees);
            break;
        default:
            MessageUI.displayInvalidChoiceMessage();
            filterDonees();
    }

    
}
    
  public void generateSummaryReport() {
    int totalDonees = doneeList.getNumberOfEntries();
    
    System.out.println("=== Donee Summary Report ===");
    System.out.println("Total Number of Donees: " + totalDonees);


    System.out.println("============================");
}
    private void updateLastDoneeNumber(String doneeID) {
        String numberPart = doneeID.substring(2); // Extract the numeric part (e.g., "001" from "DE001")
        int number = Integer.parseInt(numberPart);
        if (number > lastDoneeNumber) {
            lastDoneeNumber = number;
        }
    }
      private String generateNextDoneeID() {
        lastDoneeNumber++;
        return String.format("DE%03d", lastDoneeNumber); // Format as DE001, DE002, etc.
    }
    public static void main(String[] args) {
    DoneeManagement doneeMaintenance = new DoneeManagement();
    doneeMaintenance.runDoneeManagement();
  }
}
