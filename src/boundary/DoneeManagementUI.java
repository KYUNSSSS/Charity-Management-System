/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.*;
import adt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import utility.MessageUI;
import utility.Validator;

/**
 *
 * @author Yunshen
 */
public class DoneeManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int num = 9;
        boolean loop = true;
        while (loop) {
            try {
                System.out.println("\n******DONEE MANAGEMENT******");
                System.out.println("1. Add New Donee");
                System.out.println("2. Update Donee Details");
                System.out.println("3. Search Donee Details");
                System.out.println("4. List Donee Donations");
                System.out.println("5. Filter Donee");
                System.out.println("6. Remove Donee");
                System.out.println("7. Generate Summary Report");
                System.out.println("8. Generate Detail Report");
                System.out.println("0. Quit");
                System.out.print("Enter choice(0-8): ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();
                num = choice;
                loop = false;
            } catch (Exception ex) {
                System.err.println("Digit only.");
                scanner.nextLine();

            }
        }
        return num;
    }

    public void listAllDonees(String outputStr) {
        System.out.println("\nList of Donee:\n" + outputStr);
    }

    public String inputDoneeID() {
        while (true) {
            System.out.print("Enter Donee ID (DEXXX): ");
            String id = scanner.nextLine();

            if (Validator.isValidID(id)) {
                return id.toUpperCase();
            }
            System.out.println("\nInvalid Input. Please enter a valid Donee ID without symbols. [Eg. DE001].");
        }
    }

    public String inputDoneeType() {
        String type;
        while (true) {
            System.out.print("O - Organisation\nF - Family\nI - Individual\nEnter Donee Type(O/F/I): ");
            type = scanner.nextLine().trim();

            if (type.equalsIgnoreCase("O")
                    || type.equalsIgnoreCase("F")
                    || type.equalsIgnoreCase("I")) {
                if (type.equalsIgnoreCase("O")) {
                    type = "ORGANISATION";
                } else if (type.equalsIgnoreCase("F")) {
                    type = "---FAMILY---";
                } else if (type.equalsIgnoreCase("I")) {
                    type = "-INDIVIDUAL-";
                }
                break;
            } else {
                System.out.println("\nInvalid Type.Enter O/F/I only.");
            }
        }
        return type;

    }

    public String inputDoneeName() {
        while (true) {
            System.out.print("Enter  Name (Ali bin Abu): ");
            String name = scanner.nextLine();

            if (Validator.isAlphabetic(name)) {
                return name;
            }
            System.out.println("Please enter alphabet only.");
        }
    }

    public String inputDoneeEmail() {
        System.out.print("Enter Donee Email (example@gmail.com): ");
        String email = scanner.nextLine();
        return email;
    }

    public int inputPhoneNum() {
        while (true) {
            System.out.print("Enter Phone Number(0123456789): ");
            String phone = scanner.nextLine();
            if (Validator.isValidPhoneNumber(phone)) {
                return Integer.parseInt(phone);
            }
            System.out.println("Please enter a valid phone number.");
        }
    }

    public String inputDoneeLocation() {
        while (true) {
            System.out.print("Enter Donee Location (KL): ");
            String location = scanner.nextLine();

            if (Validator.isAlphabetic(location)) {
                return location;
            }
            System.out.println("Please enter alphabet only.");
        }

    }

    public Donee inputDoneeDetails() {
        //String id = inputDoneeID();
        String type = inputDoneeType();

        String name = inputDoneeName();
        int phone = inputPhoneNum();
        String email = inputDoneeEmail();
        String location = inputDoneeLocation();
        System.out.println("Donee Details Registered.");
        return new Donee("DE000", type.toUpperCase(), name, phone, email, location.toUpperCase());
    }

    public int getFilterChoice() {

        System.out.println("*****Filter by***** ");
        System.out.println("1. Donee Type");
        System.out.println("2. Date Range By Donee ID");
        System.out.println("3. Donation Amount Range By Donee ID");
        System.out.println("4. Donee Location");
        String choice = "";
        do {
            System.out.print("Enter your choice(1-4): ");

            try {
                String choices = scanner.nextLine();
                choice = choices;
                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")) {
                    System.err.println("Invalid choice.");
                }

            } catch (Exception ex) {
                System.err.println("Digit only.");
            }

        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"));

        return Integer.parseInt(choice);
    }

    public LocalDate inputStartDate() {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (date == null) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();

            date = Validator.isValidDate(input, formatter);

            if (date == null) {
                System.err.println("\nInvalid Date Format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    public LocalDate inputEndDate() {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (date == null) {
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();

            date = Validator.isValidDate(input, formatter);

            if (date == null) {
                System.err.println("\nInvalid Date Format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    public double inputMinAmount() {
        while (true) {
            System.out.print("Enter minimum donation amount: ");
            String input = scanner.nextLine().trim();

            if (Validator.isValidAmount(input)) {
                return Double.parseDouble(input);
            }

            System.err.println("\nInvalid Input. Please enter a positive value.");
        }
    }

    public double inputMaxAmount() {
        while (true) {
            System.out.print("Enter maximum donation amount: ");
            String input = scanner.nextLine().trim();

            if (Validator.isValidAmount(input)) {
                return Double.parseDouble(input);
            }

            System.err.println("\nInvalid Input. Please enter a positive value.");
        }
    }

    public void displayFilteredDonees(ListInterface<Donee> donees) {
        if (donees.isEmpty()) {
            System.out.println("No matching donees found.");
        } else {
            System.out.println("*******************************************************************************************");
            System.out.println("Donee ID   Category        Name            Phone Number   Email                    Location");
            System.out.println("*******************************************************************************************");
            for (int i = 1; i <= donees.getNumberOfEntries(); i++) {
                Donee donee = donees.getEntry(i);
                System.out.printf("%-10s %-15s %-15s %-14s %-25s %-15s\n",
                        donee.getDoneeID(),
                        donee.getDoneeType(),
                        donee.getDoneeName(),
                        donee.getDoneePhoneNum(),
                        donee.getDoneeEmail(),
                        donee.getDoneeLocation());
            }
            System.out.println("********************************************************************************************");
        }
        MessageUI.pressAnyKeyToContinue();
    }

    public void displayFilteredDoneesByDoneeID(ListInterface<Distribution> donations) {
        if (donations.isEmpty()) {
            System.out.println("No matching donees found.");
        } else {
            System.out.println("*******************************************************************************************************");
            System.out.printf("%-15s %-20s %-15s %-10s %-10s %-10s %-20s\n",
                    "Distribution ID", "Item Name", "Category",
                    "Quantity", "Amount", "Status", "Distribution Date");
            System.out.println("*******************************************************************************************************");

            for (int i = 1; i <= donations.getNumberOfEntries(); i++) {
                Distribution donation = donations.getEntry(i);

                System.out.printf("%-15s %-20s %-15s %-10d %-10.2f %-10s %-20s\n",
                        donation.getDistributionID(),
                        donation.getItemName(),
                        donation.getCategory(),
                        donation.getQuantity(),
                        donation.getAmount(),
                        donation.getStatus(),
                        donation.getDistributionDate().toString());

            }
        }
        System.out.println("*******************************************************************************************************");
        MessageUI.pressAnyKeyToContinue();
    }

    public boolean isDoneeIDValid(String doneeID, ListInterface<Donee> doneeList) {
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeID().equalsIgnoreCase(doneeID)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLocationValid(String location, ListInterface<Donee> doneeList) {
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeLocation().equalsIgnoreCase(location)) {
                return true;
            }
        }
        return false;
    }

    public void updateDonee(Donee donee) {
        System.out.println("Donee Found!");
        listDonee(donee);
        String choice = "";
        do {
            System.out.print("Enter your choice(1-5): ");

            try {
                String choices = scanner.nextLine();
                choice = choices;
                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) {
                    System.err.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.err.println("Digit only.");
            }
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5"));

        switch (choice) {
            case "1":
                String name = inputDoneeName();
                donee.setDoneeName(name);
                break;
            case "2":
                String type = inputDoneeType();
                donee.setDoneeType(type);
                break;
            case "3":
                int num = inputPhoneNum();
                donee.setDoneePhoneNum(num);
                break;
            case "4":
                String email = inputDoneeEmail();
                donee.setDoneeEmail(email);
                break;
            case "5":
                String location = inputDoneeLocation();
                donee.setDoneeLocation(location);
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
        }
    }

    public void listDonee(Donee donee) {
        System.out.println("***Profile***\nDonee ID: " + donee.getDoneeID() + "\n1.Name: " + donee.getDoneeName() + "\n2.Type: " + donee.getDoneeType() + "\n3.Phone Number: " + donee.getDoneePhoneNum() + "\n4.Email: " + donee.getDoneeEmail() + "\n5.Location: " + donee.getDoneeLocation() + "\n*************");
    }

}
