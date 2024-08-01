/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import boundary.*;
import utility.MessageUI;
import entity.Donee;
/**
 *
 * @author Hp
 */
public class driver {
    private driverUI driverUI = new driverUI();
    private DoneeManagement donee = new DoneeManagement();
    public void runDriver() {
        int choice = 0;
        do {
          choice = driverUI.getMenuChoice();
          switch(choice) {
            case 0:
              driver driver = new driver();
              driver.runDriver();
              break;
            case 1:
              
              break;
            case 2:
              donee.runDoneeManagement();
              
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
            default:
              MessageUI.displayInvalidChoiceMessage();
          } 
        } while (choice != 0);
      }
        

      
    public static void main(String[] args) {
    driver driver = new driver();
    driver.runDriver();
  }
}
