import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter mode (ECB, CBC, CFB, OFB, CTR): ");
        String mode = scanner.nextLine();

        System.out.print("Enter key (5 ASCII characters): ");
        String key = scanner.nextLine();

        System.out.print("Enter IV (35 binary digits) or leave blank to generate: ");
        String iv = scanner.nextLine();
        if (iv.isEmpty()) {
            iv = BlockEncrypt.generateRandomIV();
        }

        System.out.print("Enter input file path: ");
        String inputFilePath = scanner.nextLine();

        System.out.print("Enter output file path: ");
        String outputFilePath = scanner.nextLine();

        try {
            String inputText = FileUtil.readFile(inputFilePath);
            String inputBinary = BlockEncrypt.asciiToBinary(inputText);
            List<String> inputBlocks = BlockEncrypt.splitBinaryArray(inputBinary);

            List<String> encryptedBlocks;
            switch (mode) {
                case "ECB":
                    encryptedBlocks = BlockEncrypt.ECBMode(inputBlocks, key);
                    break;
                case "CBC":
                    encryptedBlocks = BlockEncrypt.CBCMode(inputBlocks, key, iv);
                    break;
                case "CFB":
                    encryptedBlocks = BlockEncrypt.CFBMode(inputBlocks, key, iv);
                    break;
                case "OFB":
                    encryptedBlocks = BlockEncrypt.OFBMode(inputBlocks, key, iv);
                    break;
                case "CTR":
                    encryptedBlocks = BlockEncrypt.CTRMode(inputBlocks, key, iv);
                    break;
                default:
                    System.out.println("Invalid mode");
                    return;
            }

            StringBuilder encryptedText = new StringBuilder();
            for (String block : encryptedBlocks) {
                encryptedText.append(block).append("\n");
            }
            FileUtil.writeFile(outputFilePath, encryptedText.toString());
            System.out.println("Encrypted text written to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}