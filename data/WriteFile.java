package data;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public static void writeFile(CsvCompatible[] items, String path) {
        try {
            FileWriter fstream = new FileWriter(path, false);
            for (CsvCompatible item : items) {
                fstream.write(item.toString() + "\n");
            }
            fstream.close();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e);
        }
    }
}
