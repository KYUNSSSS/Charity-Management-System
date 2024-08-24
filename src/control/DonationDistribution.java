/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import adt.*;
import entity.*;
import boundary.*;
import dao.DistributionDAO;
import dao.DoneeDAO;
import utility.MessageUI;
import java.time.LocalDate;

/**
 *
 * @author SCSM11
 */
public class DonationDistribution {
    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeDAO doneeDAO = new DoneeDAO(); 
    private ListInterface<Distribution> distributeList = new LinkedList<>();
    private DistributionDAO distributeDAO = new DistributionDAO();
    private DonationDistributionUI distributeUI = new DonationDistributionUI();
    
    public DonationDistribution(){
        doneeList = doneeDAO.retrieveFromFile();
        distributeList = distributeDAO.retrieveFromFile();
        
////        for (Donee donee : doneeList) {
//            String doneeID = donee.getDoneeID(); 
////            String doneeLocation = donee.getLocation(); 
//            System.out.println("Donee ID: " + doneeID);
////            System.out.println("Donee Location: " + doneeLocation);
//        }
    }
    public void runDonationDistribution() {
        int choice = 0;
        do {
            choice = distributeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewDistribute();
                    distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 2:
                    updateDistribute();
                    //distributeDAO.retrieveFromFile();
                    break;
                case 3:
                    removeDistribute();
                    distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 4:
                    trackDistribute();
                    //distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 5:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    
    public void addNewDistribute(){
        Distribution newDistribute = distributeUI.inputDistributionDetails();
        distributeList.add(newDistribute);
        distributeDAO.saveToFile(getAllDistribute());
    }
    
    public void removeDistribute() {
        String distributionID = distributeUI.inputDistributionID();
        boolean isRemoved = false;

        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution currentDistribution = distributeList.getEntry(i);

            if (currentDistribution.getDistributionID().equals(distributionID)) {
                distributeList.remove(i); 
                isRemoved = true;
                break; 
            }
        }

        if (isRemoved) {
            distributeDAO.saveToFile(getAllDistribute());
            System.out.println("Distribution removed successfully.");
        }else {
            System.out.println("Distribution ID not found.");
        }
    }
    
    public void updateDistribute() {
        String distributionID = distributeUI.inputDistributionID();
        int index = findDistributionIndexById(distributionID);
        if (index != -1) {
            Distribution updatedDistribute = distributeUI.inputDistributionDetails();
            distributeList.replace(index, updatedDistribute);
            distributeDAO.saveToFile(getAllDistribute());

            System.out.println("Distribution with ID " + distributionID + " has been updated successfully.");
        }else {
            System.out.println("Distribution with ID " + distributionID + " not found.");
        }
    }
    
    private int findDistributionIndexById(String distributionID) {
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution currentDistribute = distributeList.getEntry(i);
            if (currentDistribute.getDistributionID().equalsIgnoreCase(distributionID)) {
                return i; 
            }
        }
        return -1; 
    }
    
    public void trackDistribute() {
        int choices = 0;
        do {
            choices = distributeUI.getTrackMenuChoice();
            switch (choices) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    trackByDoneeID();
                    break;
                case 2:
                    trackByDistributionDate();
                    break;
                case 3:
                    trackByCategory();
                    break;
                case 4:
                    trackByStatus();
                    break;
                case 5:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choices != 0); 
        
    }
    
    private void trackByCriteria(String criteria, String type) {
        StringBuilder result = new StringBuilder();
        String title = "";

        switch (type) {
            case "DoneeID":
                title = "Donation Details for Donee ID: " + criteria;
                break;
            case "DistributionDate":
                title = "Donation Details for Distribution Date: " + criteria;
                break;
            case "Category":
                title = "Donation Details for Category: " + criteria;
                break;
            case "Status":
                title = "Donation Details for Status: " + criteria;
                break;
            default:
                System.out.println("Unknown type.");
                return;
        }
      
        result.append(DonationDistributionUI.formatHeader(title));
        
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution dist = distributeList.getEntry(i);

            boolean match = false;
            switch (type) {
                case "DoneeID":
                    match = dist.getDoneeID().equalsIgnoreCase(criteria);
                    break;
                case "DistributionDate":
                    match = dist.getDistributionDate().isEqual(LocalDate.parse(criteria));
                    break;
                case "Category":
                    match = dist.getCategory().equalsIgnoreCase(criteria);
                    break;
                case "Status":
                    match = dist.getStatus().equalsIgnoreCase(criteria);
                    break;
            }

            if (match) {
                result.append(String.format("%-20s%-15s%-15d%-20s%-20s\n",
                        dist.getItemName(),
                        dist.getCategory(),
                        dist.getQuantity(),
                        dist.getStatus(),
                        dist.getDistributionDate()));
            }
        }

        result.append("===================================================================================================\n");

        // If no matching records are found, inform the user
        if (result.length() == 0) {
            result.append("No matching records found.\n");
        }

        // Display the final formatted result
        distributeUI.listAllDistribute(result.toString());
    }


    private void trackByDoneeID() {
        String doneeID = distributeUI.inputDoneeID();
        trackByCriteria(doneeID, "DoneeID");
    }

    private void trackByDistributionDate() {
        LocalDate date = distributeUI.inputDistributionDate();
        trackByCriteria(date.toString(), "DistributionDate");
    }

    private void trackByCategory() {
        String category = distributeUI.inputDonationCategories();
        trackByCriteria(category, "Category");
    }

    private void trackByStatus() {
        String status = distributeUI.inputStatus();
        trackByCriteria(status, "Status");
    }
    
    public void generateReport() {
        int totalPending = 0;
        int totalDelivered = 0;
        int totalReceived = 0;
        int highestQuantity = 0;
        String highestStatus = "";
        String highestCategory = "";

        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            Distribution dist = distributeList.getEntry(i);

            switch (dist.getStatus().toLowerCase()) {
                case "pending":
                    totalPending += dist.getQuantity();
                    break;
                case "delivered":
                    totalDelivered += dist.getQuantity();
                    break;
                case "received":
                    totalReceived += dist.getQuantity();
                    break;
            }

            if (dist.getQuantity() > highestQuantity) {
                highestQuantity = dist.getQuantity();
                highestStatus = dist.getStatus();
                highestCategory = dist.getCategory();
            }
        }

        distributeUI.displaySummaryReport(totalPending, totalDelivered, totalReceived, highestQuantity, highestStatus, highestCategory);
    }

    public String getAllDistribute() {
        String outputStr = "";
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            outputStr += distributeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public static void main(String[] args) {
        DonationDistribution distribute = new DonationDistribution();
        distribute.runDonationDistribution();
  }
}

