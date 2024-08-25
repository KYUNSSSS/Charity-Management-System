package dao;

import adt.*;
import entity.Donee;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Kat Tan
 */
public class DoneeDAO implements Serializable {
  private String fileName = "donee.txt"; // For security and maintainability, should not have filename hardcoded here.
  
  public void saveToFile(String doneeList) {
    File file = new File(fileName);
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(doneeList);
            System.out.println("String saved successfully.");
        } catch (IOException ex) {
            System.out.println("Error saving string to file: " + ex.getMessage());
            ex.printStackTrace();
        }
  }

  public ListInterface<Donee> retrieveFromFile() {
        LinkedList<Donee> doneeList = new LinkedList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    // Parse the data
                    String doneeID = parts[0].trim();
                    String doneeType = parts[1].trim();
                    String doneeName = parts[2].trim();
                    int doneePhoneNum = Integer.parseInt(parts[3].trim());
                    String doneeEmail = parts[4].trim();
                    String doneeLocation = parts[5].trim();
                    // Create a Donee object and add it to the list
                    Donee donee = new Donee(doneeID, doneeType, doneeName, doneePhoneNum, doneeEmail,doneeLocation);
                    doneeList.add(donee);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
            ex.printStackTrace();
        }

        return doneeList;
    }
}
