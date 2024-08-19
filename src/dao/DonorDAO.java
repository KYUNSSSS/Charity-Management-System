/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Donor;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author xuan
 */
public class DonorDAO implements Serializable {

    private String fileName = "donor.dat"; // For security and maintainability, should not have filename hardcoded here.

//    public void saveToFile(String donorList) {
//        File file = new File(fileName);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            writer.write(donorList);
//            System.out.println("String saved successfully.");
//        } catch (IOException ex) {
//            System.out.println("Error saving string to file: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
//
//    public ListInterface<Donor> retrieveFromFile() {
//        LinkedList<Donor> donorList = new LinkedList<>();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Split the line by commas
//                String[] parts = line.split(",");
//
//                if (parts.length == 7) {
//                    // Parse the data
//                    String donorID = parts[0].trim();
//                    String donorType = parts[1].trim();
//                    String donorName = parts[2].trim();
//                    int donorPhoneNum = Integer.parseInt(parts[3].trim());
//                    String donorEmail = parts[4].trim();
//                    double donationAmount = Double.parseDouble(parts[5].trim());
////                    LocalDate donationDate = LocalDate.parse(parts[6].trim(), dateFormatter);
//
//                    // Create a Donee object and add it to the list
//                    Donor donor = new Donor(donorID, donorName, donorPhoneNum, donorEmail, donorType, donationAmount);
//                    donorList.add(donor);
//                } else {
//                    System.out.println("Skipping invalid line: " + line);
//                }
//            }
//        } catch (IOException ex) {
//            System.out.println("Error reading from file: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//
//        return donorList;
//    }
    
    public void saveToFile(ListInterface<Donor> donorList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(donorList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Donor> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Donor> donorList = new LinkedList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            donorList = (LinkedList<Donor>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return donorList;
        }
    }
}
