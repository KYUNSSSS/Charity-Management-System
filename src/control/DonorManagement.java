/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.Donor;
import java.util.Scanner;

/**
 *
 * @author xuan
 */
public class DonorManagement {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice = scanner.nextInt();
        try {
            System.out.println("\n******DONOR MANAGEMENT******");
            System.out.println("1. Add New Donor");
            System.out.println("2. Remove Donor");
            System.out.println("3. Update Donor Details");
            System.out.println("4. Search Donor Details");
            System.out.println("5. List Donors Donations"); //List donors with all the donations made
            System.out.println("6. Filter Donors"); //Filter donors based on criteria
            System.out.println("7. Categorise Donors"); //Categorize donors(e.g., government, private, public)
            System.out.println("8. Generate Summary Report");
            System.out.println("0. Quit");
            System.out.print("Enter choice(0-8): ");
            
            scanner.nextLine();
            System.out.println();
        } catch (Exception ex) {
            System.err.println("Invalid Choice. Please re-enter.");
        }
        return choice;
    }
}
