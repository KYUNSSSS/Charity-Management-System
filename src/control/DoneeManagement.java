/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import boundary.DoneeManagementUI;
import utility.MessageUI;
import entity.Donee;
/**
 *
 * @author Hp
 */
public class DoneeManagement {
    private DoneeManagementUI doneeUI = new DoneeManagementUI();
    public void runDoneeManagement() {
        int choice = 0;
        do {
          choice = doneeUI.getMenuChoice();
          switch(choice) {
            case 0:
              MessageUI.displayExitMessage();
              break;
            case 1:
              //addNewDonee();
             // doneeUI.listAllDonee(getAllDonee());
              break;
            case 2:
              //updateDoneeDetails();
              //doneeUI.listAllDonees(getAllDonee());
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
    Donee newProduct = DoneeManagementUI.inputDoneeDetails();
    productList.add(newProduct);
    productDAO.saveToFile(productList);
  }
    public void updateNewProduct() {
    Donees newProduct = DoneeManagementUI.inputProductDetails();
    productList.add(newProduct);
    productDAO.saveToFile(productList);
  }
      
    public static void main(String[] args) {
    DoneeManagement doneeMaintenance = new DoneeManagement();
    doneeMaintenance.runDoneeManagement();
  }
}
