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

//    public ListInterface<Distribution> retrieveFromFile() {
//        File file = new File(fileName);
//        ListInterface<Distribution> distributeList = new LinkedList<>();
//        try {
//            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
//            distributeList = (LinkedList<Distribution>) (oiStream.readObject());
//            oiStream.close();
//        } catch (FileNotFoundException ex) {
//            System.out.println("\nNo such file.");
//        } catch (IOException ex) {
//            System.out.println("\nCannot read from file.");
//        } catch (ClassNotFoundException ex) {
//            System.out.println("\nClass not found.");
//        } finally {
//            return distributeList;
//        }
//    }
    
    public ListInterface<Distribution> retrieveFromFile() {
        LinkedList<Distribution> distributeList = new LinkedList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas
                String[] parts = line.split(",");

                if (parts.length == 7) { // Adjust the length according to the Distribution fields
                    try {
                        // Parse the data
                        String distributionID = parts[0].trim();
                        String itemName = parts[1].trim();
                        String category = parts[2].trim();
                        int quantity = Integer.parseInt(parts[3].trim()); // Correctly parse quantity as int
                        String doneeID = parts[4].trim();
                        String status = parts[5].trim();
                        LocalDate distributionDate = LocalDate.parse(parts[6].trim(), dateFormatter);

                        // Create a Distribution object and add it to the list
                        Distribution distribution = new Distribution(distributionID, itemName, category, quantity, doneeID, status, distributionDate);
                        distributeList.add(distribution);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping line due to number format error: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
            ex.printStackTrace();
        }

        return distributeList;
    }
}

