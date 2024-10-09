import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static void writeFile(String filePath, String data) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes());
    }
}