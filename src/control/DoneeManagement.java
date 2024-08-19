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
              //updateDoneeDetails();
              doneeDAO.retrieveFromFile();
              break;
             case 3:
             // searchDoneeDetails();
             // doneeUI.listAllDonees(getAllDonee());
              break;
             case 4:
             // doneeUI.listAllDonees(getAllDonee());
              break;
             case 5:
              //filterDonee();
             // doneeUI.listAllDonees(getAllDonee());
              break;
              case 6:
              //removeDonee();
              //doneeUI.listAllDonees(getAllDonee());
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
    public String getAllDonee() {
    String outputStr = "";
    for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
      outputStr += doneeList.getEntry(i) + "\n";
    }
    return outputStr;
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
