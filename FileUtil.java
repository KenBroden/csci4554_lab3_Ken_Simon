import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    /**
     * Takes a file path and returns the contents of the matching file as a String.
     */
    public static String fileToString(String pathname) throws FileNotFoundException, IOException {
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader(new File(pathname))
            );
            StringBuilder result = new StringBuilder();
            // write to result line by line, replacing the string as we go
            String line = null;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * Takes a string and a file path and writes the contents of the string to the file.
     */
    public static void stringToFile(String text, String pathname) throws IOException {
        try {
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
             }
            BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(pathname))
            );
            writer.write(text);
            writer.close();
        } catch(Exception e) {
            throw e;
        }
    }
}