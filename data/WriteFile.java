package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Utility class for writing objects that implement the CsvCompatible interface to a CSV file.
 * This class provides a method to serialize a collection of CsvCompatible objects into a CSV file.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class WriteFile {
    /**
     * Writes a collection of CsvCompatible objects to a CSV file.
     * Each object in the collection is serialized using its {@code toCsv} method.
     * 
     * @param items the collection of objects to write to the file
     * @param path the path of the file where the data will be written
     * @throws IOException if there is an error writing to the file
     */
    public static void writeFile(Collection<? extends CsvCompatible> items, String path) {
        try {
            FileWriter fstream = new FileWriter(path, false);
            for (CsvCompatible item : items) {
                fstream.write(item.toCsv() + "\n");
            }
            fstream.close();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e);
        }
    }
}
