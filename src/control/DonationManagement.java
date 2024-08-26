package control;

import adt.LinkedList;
import adt.ListInterface;
import dao.DonationDAO;
import entity.Donation;
import boundary.DonationManagementUI;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;
import java.util.InputMismatchException;
import utility.*;

public class DonationManagement {
    private DonationDAO donationDAO = new DonationDAO();
    public LinkedList<Donation> donationList;
    private DonationManagementUI ui;
    private Filter<Donation> filter;

    public DonationManagement(DonationManagementUI ui) {
        this.ui = ui;
        this.donationList = donationDAO.loadDonationsFromFile(); // Load donations from file on initialization
    }

    public DonationManagement() {
        this.donationList = donationDAO.loadDonationsFromFile();
    }

    public void setUI(DonationManagementUI ui) {
        this.ui = ui;
    }

    public void runDonationManagement() {
        int choice;
        do {
            choice = ui.getMenuChoice();
            switch (choice) {
                case 1 -> ui.addDonation();
                case 2 -> ui.removeDonation();
                case 3 -> ui.searchDonationById();
                case 4 -> ui.amendDonationDetails();
                case 5 -> ui.trackDonation();
                case 6 -> ui.listDonationsByDonors();
                case 7 -> ui.listDonations();
                case 8 -> handleFilterChoice();//base on criteria
                case 9 -> ui.donationsReports();
                case 0 -> System.out.println("Exiting Donation Management System.");
                default -> System.err.println("Invalid choice. Please select an option between 0 and 9.");
            }
        } while (choice != 0);
    }


    public boolean addDonation(String donationID, String donorID, String itemCategory, String item, double amount, LocalDate donationDate) {
        Donation donation = new Donation(donationID, donorID, itemCategory,item, amount, donationDate);
        boolean isAdded = donationList.add(donation);
        if (isAdded) {
            donationDAO.saveDonationListToFile(donationList);
        }
        return isAdded;
    }

    public boolean removeDonation(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                donationList.remove(i);
                donationDAO.saveDonationListToFile(donationList); // Save the updated list to file
                return true;
            }
        }
        return false;
    }

    public Donation getDonationById(String donationID) {
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonationID().equals(donationID)) {
                return donation;
            }
        }
        return null;
    }
    
    public void amendDonationDetails(String donationID, String newDonorID, String newItemCategory, String newItem, Double newAmount) {
        Donation donation = getDonationById(donationID);
        if (donation == null) {
            System.err.println("Donation not found.");
            return;
        }

        // Update the details if new values are provided
        if (!newDonorID.isEmpty()) donation.setDonorID(newDonorID);
        if (!newItemCategory.isEmpty()) donation.setItemCategory(newItemCategory);
        if (!newItem.isEmpty()) donation.setItem(newItem);
        if (newAmount != null) donation.setAmount(newAmount);

        // Save the updated donation list back to the file
        donationDAO.saveDonationListToFile(donationList);
    }


    public LinkedList<String> trackDonationByCategory(String itemCategory) {
        LinkedList<String> itemsInCategory = new LinkedList<>();
        

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);

            if (donation.getItemCategory().equalsIgnoreCase(itemCategory)) {
                String itemWithAmount = donation.getItem() + " (Amount: " + donation.getAmount() + ")";
                itemsInCategory.add(itemWithAmount);
            }
        }
        return itemsInCategory;
    }


    public LinkedList<Donation> listDonationsByDonor(String donorID) {
        LinkedList<Donation> result = new LinkedList<>();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            if (donation.getDonorID().equals(donorID)) {
                result.add(donation);
            }
        }
        return result;
    }
    public void handleFilterChoice() {
        int choice;
        do{
            choice = ui.displayFilterOptions();
            switch (choice) {
            case 1:
                ui.filterByDateRange();
                break;
            case 2:
                ui.filterByDonationAmountRange();
                break;
            case 3:
                ui.filterByDateAndAmountRange();
                break;
            default:
                System.err.println("Invalid choice. Please enter a number between 1 and 3.");
            }   
        }while (choice != 0);
    }
    
    public ListInterface<Donation> filterDonationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return filter.filterByDateRange(donationList, startDate, endDate);
    }

    public ListInterface<Donation> filterDonationsByAmountRange(double minAmount, double maxAmount) {
        return filter.filterByDonationAmountRange(donationList, minAmount, maxAmount);
    }

    public LinkedList<Donation> listDonations() {
        return donationList;
    }

    public String generateReport() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fileName = "donation_summary_report_" + dateFormatter.format(LocalDate.now()) + ".txt";
        StringBuilder reportContent = new StringBuilder();

        try {
            Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
            Path filePath = downloadsFolder.resolve(fileName);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            reportContent.append("*****Donation Summary Report*****\n");
            reportContent.append("Date Generated    : ").append(LocalDate.now().format(dateFormatter)).append("\n\n");
            reportContent.append("Summary Report :\n");
            reportContent.append("Total Number of Donations : ").append(donationList.getNumberOfEntries()).append("\n\n");

            for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                Donation donation = donationList.getEntry(i);
                reportContent.append("Donation ID   : ").append(donation.getDonationID()).append("\n");
                reportContent.append("Donors ID     : ").append(donation.getDonorID()).append("\n");
                reportContent.append("Item          : ").append(donation.getItem()).append("\n");
                reportContent.append("Donation Type : ").append(donation.getItemCategory()).append("\n");
                reportContent.append("Donation Date : ").append(donation.getDonationDate()).append("\n");
                reportContent.append("--------------------------------------------\n");
            }

            Files.write(filePath, reportContent.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public static void main(String[] args) {
        DonationManagement controller = new DonationManagement();
        DonationManagementUI ui = new DonationManagementUI(controller);
        controller.setUI(ui);
        controller.runDonationManagement();
    }
}
