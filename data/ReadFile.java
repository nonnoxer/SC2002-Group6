package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFile {
    public static String[][] readCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[][] lines_separated = new String[lines.size()][];
        for (int i = 0; i < lines_separated.length; i++) {
            lines_separated[i] = lines.get(i).split(",");
        }
        return lines_separated;
    }
}
