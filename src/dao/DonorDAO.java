/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Donor;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ng Yin Xuan
 */
public class DonorDAO implements Serializable {

    private String fileName = "donor.txt";

    public void saveToFile(String donorList) {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(donorList);

        } catch (IOException ex) {
            System.out.println("Error saving string to file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public ListInterface<Donor> retrieveFromFile() {
        LinkedList<Donor> donorList = new LinkedList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        File file = new File(fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DonorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    // Parse the data
                    String donorID = parts[0].trim();
                    String donorName = parts[1].trim();
                    int donorPhoneNum = Integer.parseInt(parts[2].trim());
                    String donorEmail = parts[3].trim();
                    String donorType = parts[4].trim();
                    String donorEntity = parts[5].trim();

                    // Create a Donor object and add it to the list
                    Donor donor = new Donor(donorID, donorName, donorPhoneNum, donorEmail, donorType, donorEntity);
                    donorList.add(donor);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
            ex.printStackTrace();
        }

        return donorList;
    }
}
