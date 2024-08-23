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
                   //distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 4:
                    // monitorDistribute();
                    //distributeUI.listAllDistribute(getAllDistribute());
                    break;
                case 5:
                    // generateReport();
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
    
    public void removeDistribute(){
    }
    
    public void updateDistribute(){
    }
    
    public String getAllDistribute() {
        String outputStr = "";
        for (int i = 1; i <= distributeList.getNumberOfEntries(); i++) {
            outputStr += distributeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

}
