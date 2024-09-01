/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.LinkedList;
import adt.ListInterface;
import entity.Volunteer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

                if (parts.length == 6) {
                    // Parse the data
                    String volunteerID = parts[0].trim();
                    String volunteerType = parts[1].trim();
                    String volunteerName = parts[2].trim();
                    int volunteerPhoneNum = Integer.parseInt(parts[3].trim());
                    String volunteerEmail = parts[4].trim();
                    String eventAssigned = parts[5].trim();
                    // Create a Donee object and add it to the list
                    Volunteer volunteer = new Volunteer(volunteerID, volunteerType, volunteerName, volunteerPhoneNum, volunteerEmail, eventAssigned);
                    volunteerList.add(volunteer);
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
  
   public void updateVolunteer(Volunteer updatedVolunteer) {
        File originalFile = new File(fileName);
        File tempFile = new File("temp_"+ fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].trim().equals(updatedVolunteer.getVolunteerID())) {
                    // Update the line with new volunteer details                            
                    String updatedLine = String.format("%s,%s,%s,%d,%s,%s",
                        updatedVolunteer.getVolunteerID(),
                        updatedVolunteer.getVolunteerType(),
                        updatedVolunteer.getVolunteerName(),
                        updatedVolunteer.getVolunteerPhoneNum(),
                        updatedVolunteer.getVolunteerEmail(),
                        updatedVolunteer.getEventAssigned()
                    );
                    writer.write(updatedLine);
                    writer.newLine();
                    updated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!updated) {
                System.out.println("No volunteer found with ID: " + updatedVolunteer.getVolunteerID());
            }

        } catch (IOException ex) {
            System.out.println("Error processing files: " + ex.getMessage());
            ex.printStackTrace();
        }

        if (originalFile.delete()) {
            if (tempFile.renameTo(originalFile)) {
                System.out.println("Volunteer updated successfully.");
            } else {
                System.out.println("Failed to rename the temporary file.");
            }
        } else {
            System.out.println("Failed to delete the original file.");
        }
        
        // Replace the original file with the updated file
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Volunteer updated successfully.");
        } else {
            System.out.println("Failed to replace the original file.");
        }
   }
}

