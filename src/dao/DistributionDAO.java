/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.Distribution;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author SCSM11
 */
public class DistributionDAO implements Serializable { 
    private String fileName = "DonationDistribution.txt"; // File for storing data
     
    // Save DonationDistribution objects to file
    public void saveToFile(String distributeList) {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(distributeList);
            System.out.println("String saved successfully.");
        } catch (IOException ex) {
            System.out.println("Error saving string to file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public ListInterface<Distribution> retrieveFromFile() {
        LinkedList<Distribution> distributeList = new LinkedList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas
                String[] parts = line.split(",");

                if (parts.length == 8) { // Adjust the length according to the Distribution fields
                    try {
                        // Parse the data
                        String distributionID = parts[0].trim();
                        String itemName = parts[1].trim();
                        String category = parts[2].trim();
                        int quantity = Integer.parseInt(parts[3].trim()); // Correctly parse quantity as int
                        double amount = Double.parseDouble(parts[4].trim());
                        String doneeID = parts[5].trim();
                        String status = parts[6].trim();
                        LocalDate distributionDate = LocalDate.parse(parts[7].trim(), dateFormatter);

                        // Create a Distribution object and add it to the list
                        Distribution distribution = new Distribution(distributionID, itemName, category, quantity, amount, doneeID, status, distributionDate);
                        distributeList.add(distribution);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping line due to number format error: " + line);
                    }
                } else {
                    System.out.print("");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
            ex.printStackTrace();
        }

        return distributeList;
    }
}

