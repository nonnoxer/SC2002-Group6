package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {
    public static void writeFile(ArrayList<? extends CsvCompatible> items, String path) {
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
