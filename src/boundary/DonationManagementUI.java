
/**
 *
 * @author haojuan
 */
package boundary;

import control.DonationManagementController;
import entity.DonationManagement;

import java.util.Scanner;
import adt.LinkedList; 

public class DonationManagementUI {
    Scanner scanner = new Scanner(System.in);
    private DonationManagementController controller;

    // Constructor that takes DonationManagementController as a parameter
    public DonationManagementUI(DonationManagementController controller) {
        this.controller = controller;
    } 
    // ANSI escape code for green text
    String greenText = "\u001B[32m";

    // ANSI escape code to reset to default color
    String resetText = "\u001B[0m";
    
    

    public int getMenuChoice() {
        System.out.println("*****Donation Management System Menu*****");
        System.out.println("1. Add Donation");
        System.out.println("2. Remove Donation");
        System.out.println("3. Search Donation by ID");
        System.out.println("4. Amend Donation details");
        System.out.println("5. Track Donated Items in Categories");
        System.out.println("6. List Donations by Donors");
        System.out.println("7. List Donations");
        System.out.println("8. Generate Donation Reports");
        System.out.println("0. Exit");
        System.out.print("Enter your choice : ");
        int choice = -1;
        while (choice < 0 || choice > 8) {
            System.out.print("Enter choice (0-8) : ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number between 0 and 8.");
            }
        }
        return choice;
    }

    
    public void addDonation() {
        System.out.println("*****Add Donation*****");
        
        System.out.print("Enter Donation ID             : ");
        String donationID = scanner.nextLine();

        System.out.print("Enter Donor ID                : ");
        String donorID = scanner.nextLine();

        System.out.print("Enter Donation Type           : ");
        String donationType = scanner.nextLine();

        System.out.println("Enter Items (separate by ',') : ");
        String itemsInput = scanner.nextLine();
        String[] itemsArray = itemsInput.split(",");

        boolean success = controller.addDonation(donationID, donorID, itemsArray, donationType);
        if (success) {
            System.out.println(greenText + "Donation added successfully!" + resetText);
        } else {
            System.err.println("Failed to add donation.");
        }
        System.out.println(greenText + "Press any key to continue..." + resetText);
        scanner.nextLine();
    }
    
    public void removeDonation() {
        System.out.println("*****Remove Donation*****");

        System.out.print("Enter Donation ID: ");
        String donationID = scanner.nextLine();

        DonationManagement donation = controller.getDonationById(donationID);
        if (donation != null) {
            System.out.println("Donation ID     : " + donation.getDonationID());
            System.out.println("Donor ID        : " + donation.getDonorID()); //remember to link to donorManagement
            System.out.println("Donation Type   : " + donation.getDonationType());
            System.out.println("Items           : " + donation.getItems().toString());


            boolean validInput = false;
            while (!validInput) {
                System.out.print("Confirmation for remove (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y") || confirmation.equals("yes")) {
                    boolean success = controller.removeDonation(donationID);
                    if (success) {
                        System.out.println(greenText + "Donation removed successfully!" + resetText);
                    } else {
                        System.err.println("Failed to remove donation.");
                    }
                    validInput = true;
                } else if (confirmation.equals("n") || confirmation.equals("no")) {
                    System.err.println("Action canceled.");
                    validInput = true;
                } else {
                    System.err.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }


    public void searchDonationById() {
        System.out.println("*****Search Donation*****");
        System.out.print("Enter Donation ID: ");
        String donationID = scanner.nextLine();

        DonationManagement donation = controller.getDonationById(donationID);
        if (donation != null) {
            System.out.println("Donation ID: " + donation.getDonationID());
            System.out.println("Donor ID: " + donation.getDonorID());
            System.out.println("Donation Type: " + donation.getDonationType());
            System.out.println("Items: " + donation.getItems().toString());
        } else {
            System.err.println("Donation not found.");
        }
    }

    public void amendDonorsDetails(){
        System.out.println("*****Amend Donors Details*****");
        
        System.out.print("Enter Donor ID: ");
        String donorID = scanner.nextLine();
        
        //retrive donors details from donorManagement
        
        System.out.print("Confirm Amend Donor Details (Y): ");
        //if user enter Y/y/yes/Yes/YEs/YeS/yEs/yES/... to confirm remove else cancel the action
        //no need do first
    }
    
    public void trackDonation() {
        System.out.println("*****Track Donated Items in Categories*****");

        System.out.print("Enter category to track: ");
        String category = scanner.nextLine();

        LinkedList<String> items = controller.trackDonationByCategory(category);
        if (items != null && items.getNumberOfEntries() > 0) {
            System.out.println("Items in category '" + category + "':");
            for (int i = 1; i <= items.getNumberOfEntries(); i++) {
                System.out.println("- " + items.getEntry(i));
            }
        } else {
            System.err.println("No items found in the specified category.");
        }
    }
    
    public void listDonationsByDonors() {
        System.out.println("*****List Donation by Different Donor*****");

        // Assuming the controller can list donations by donor
        System.out.print("Enter Donor ID: ");
        String donorID = scanner.nextLine();

        LinkedList<DonationManagement> donations = controller.getDonationsByDonor(donorID);
        if (donations != null && donations.getNumberOfEntries() > 0) {
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                DonationManagement donation = donations.getEntry(i);
                System.out.println("Donation ID : " + donation.getDonationID());
                System.out.println("Donation Type : " + donation.getDonationType());
                System.out.println("Items : " + donation.getItems().toString());
                System.out.println("----");
            }
        } else {
            System.err.println("No donations found for the specified donor.");
        }
    }

    
    public void listDonations() {
        System.out.println("*****List All Donations*****");

        LinkedList<DonationManagement> donations = controller.getAllDonations();
        if (donations != null && donations.getNumberOfEntries() > 0) {
            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                DonationManagement donation = donations.getEntry(i);
                System.out.println("Donation ID: " + donation.getDonationID());
                System.out.println("Donor ID: " + donation.getDonorID());
                System.out.println("Donation Type: " + donation.getDonationType());
                System.out.println("Items: " + donation.getItems().toString());
                System.out.println("----");
            }
        } else {
            System.err.println("No donations found.");
        }
    }

    
    public void donationsReports() {
        System.out.println("*****Generate Summary Reports*****");
        String report = controller.generateReport();
        System.out.println(report);
    }
}
