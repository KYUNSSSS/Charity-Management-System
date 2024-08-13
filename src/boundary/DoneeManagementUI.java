/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import entity.Donee;
import java.util.Scanner;

/**
 *
 * @author Yunshen
 */
public class DoneeManagementUI {
    Scanner scanner = new Scanner(System.in);

  public int getMenuChoice() {
   try{
    System.out.println("\n******DONEE MANAGEMENT******");
    System.out.println("1. Add New Donee");
    System.out.println("2. Update Donee Details");
    System.out.println("3. Search Donee Details");
    System.out.println("4. List Donee Donations");
    System.out.println("5. Filter Donee");
    System.out.println("6. Remove Donee");
    System.out.println("7. Generate Summary Report");
    System.out.println("0. Quit");
    System.out.print("Enter choice(0-7): ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    System.out.println();
    return choice;
   }catch(Exception ex){
       System.out.println("Invalid input");
   }
    return 0;
  }
   public void listAllDonees(String outputStr) {
    System.out.println("\nList of Products:\n" + outputStr);
  }
   
//   public Donee inputProductDetails() {
//    String productCode = inputProductCode();
//    String productName = inputProductName();
//    int quantity = inputQuantity();
//    System.out.println();
//    return new Product(productCode, productName, quantity);
//  }

}
