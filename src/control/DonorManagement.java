/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import boundary.DonorManagementUI;
import dao.DonorDAO;
import utility.MessageUI;

/**
 *
 * @author xuan
 */
public class DonorManagement {

    private ListInterface<Donor> donorList = new LinkedList<>();
    private DonorDAO donorDAO = new DonorDAO();
    private DonorManagementUI donorUI = new DonorManagementUI();

    public DonorManagement() {
        donorList = donorDAO.retrieveFromFile();
    }

    public void runDonorManagement() {
        int choice = 0;
        do {
            choice = donorUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewDonor();
                    donorUI.listAllDonors(getAllDonors());
                    break;
                case 2:
                    // removeDonor();
                    donorDAO.retrieveFromFile();
                    break;
                case 3:
                    // updateDoneeDetails();
                    // donorUI.listAllDonors(getAllDonors());
                    break;
                case 4:
                    // searchDoneeDetails();
                    // donorUI.listAllDonors(getAllDonors());
                    break;
                case 5:
                    // listDonorWithDonations();
                    // donorUI.listAllDonors(getAllDonors());
                    break;
                case 6:
                    // filterDonee();
                    // donorUI.listAllDonors(getAllDonors());
                    break;
                case 7:
                    // categoriseDonor();
                    // donorUI.listAllDonors(getAllDonors());
                    break;
                case 8:
                    // generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addNewDonor() {
        Donor newDonor = donorUI.inputDonorDetails();
        donorList.add(newDonor);
        donorDAO.saveToFile(donorList);
    }

    public String getAllDonors() {
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            outputStr += donorList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void displayDonors() {
        donorUI.listAllDonors(getAllDonors());
    }

    public static void main(String[] args) {
        DonorManagement DonorManagement = new DonorManagement();
        DonorManagement.runDonorManagement();
    }
}
