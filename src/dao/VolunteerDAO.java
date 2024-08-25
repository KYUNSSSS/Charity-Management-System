/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.LinkedList;
import adt.ListInterface;
import entity.Donee;
import entity.Volunteer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class VolunteerDAO {
    private String fileName = "volunteer.txt"; // For security and maintainability, should not have filename hardcoded here.
  
    public void saveToFile(String volunteerList) {
      File file = new File(fileName);
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
              writer.write(volunteerList);
              System.out.println("String saved successfully.");
          } catch (IOException ex) {
              System.out.println("Error saving string to file: " + ex.getMessage());
              ex.printStackTrace();
          }
    }

    public ListInterface<Volunteer> retrieveFromFile() {
        LinkedList<Volunteer> volunteerList = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas
                String[] parts = line.split(",");

                if (parts.length == 7) {
                    // Parse the data
                    String volunteerID = parts[0].trim();
                    String volunteerType = parts[1].trim();
                    String volunteerName = parts[2].trim();
                    int volunteerPhoneNum = Integer.parseInt(parts[3].trim());
                    String volunteerEmail = parts[4].trim();

                    // Create a Volunteer object and add it to the list
//                    Volunteer volunteer = new Volunteer(volunteerID, volunteerType, volunteerName, volunteerPhoneNum, volunteerEmail);
//                    volunteerList.add(volunteer);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
            ex.printStackTrace();
        }

        return volunteerList;
    }
}

