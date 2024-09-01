/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SCSM11
 */
import adt.MapInterface;
import adt.HashMap;
import java.io.*;

public class TempDAO {
    private static final String FILE_NAME = "Totals.txt";
    public void createFileIfNotExists() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + FILE_NAME);
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    public void saveTotals(MapInterface<String, Double> itemTotals, double cashTotal) {
        createFileIfNotExists(); // Ensure file is created before saving
        File file = new File(FILE_NAME);

        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                
                writer.write("Item totals:\n");
                for (int i = 0; i < itemTotals.capacity(); i++) {
                    if (itemTotals.getKey(i) == null) {
                        continue;
                    }
                    String key = itemTotals.getKey(i);
                    String[] parts = key.split(": ");
                    String category = parts[0];
                    String item = parts[1];
                    writer.write("Category: " + category + ", Item: " + item + ", Total: " + itemTotals.get(key) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while saving totals: " + e.getMessage());
        }
    }

    public MapInterface<String, Double> loadItemTotals() {
        MapInterface<String, Double> itemTotals = new HashMap<>();
        double cashTotal = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Category:")) {
                    String[] parts = line.split(", ");
                    String category = parts[0].split(": ")[1];
                    String item = parts[1].split(": ")[1];
                    double total = Double.parseDouble(parts[2].split(": ")[1]);

                    // Combine category and item for the key
                    String key = category + ": " + item;
                    itemTotals.put(key, total);
                }
            }
            
            for (int i = 0; i < itemTotals.capacity(); i++) {
                if (itemTotals.getKey(i) == null) {
                    continue;
                }
                String key = itemTotals.getKey(i);
                System.out.println("Category: " + key.split(": ")[0] + ", Item: " + key.split(": ")[1] + ", Total: " + itemTotals.get(key));
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found. Please save totals first.");
        } catch (IOException e) {
            System.err.println("Error loading totals: " + e.getMessage());
        }

        return itemTotals;
    }

    public double loadCashTotal() {
        double cashTotal = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Total cash donations:")) {
                    cashTotal = Double.parseDouble(line.split(": ")[1]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found. Please save totals first.");
        } catch (IOException e) {
            System.err.println("Error loading cash total: " + e.getMessage());
        }

        return cashTotal;
    }
}

