
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.*;

/**
 *
 * @author Yunshen
 */
public class driverUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        try {
            System.out.println("\n******CHARITY MANAGEMENT SYSTEM******");
            System.out.println("1. Donor Management");
            System.out.println("2. Donee Management");
            System.out.println("3. Donation Management");
            System.out.println("4. Donation Distribution");
            System.out.println("5. Volunteer Management");
            System.out.println("0. Quit");
            System.out.print("Enter choice(0-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            return choice;
        } catch (Exception ex) {
            System.out.println("Invalid input");
        }
        return 0;
    }

}
