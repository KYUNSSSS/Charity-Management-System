/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.Distribution;
import java.io.*;

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

    // Retrieve DonationDistribution objects from file
    public ListInterface<Distribution> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Distribution> distributeList = new LinkedList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            distributeList = (LinkedList<Distribution>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return distributeList;
        }
    }
}

