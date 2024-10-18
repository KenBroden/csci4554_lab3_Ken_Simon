import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Define the file path to encrypt
        String inputFilePath = "Text/turing_plaintext.txt"; // DEFINE THE FILE PATH HERE

        // Define key, generate IV, and read plaintext
        String key = "1111000 0101101 1100110 0001010 1000101"; // DEFINE THE KEY HERE
        String iv = "1001001 0111001 1100000 0100101 1001010"; // DEFINE THE IV HERE
        String plaintext = "";

        try {
            plaintext = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String binaryKey = BlockEncrypt.unformatBinaryString(key);
        String binaryIV = BlockEncrypt.unformatBinaryString(iv);
        System.out.println("IV: " + iv);
        System.out.println("Key: " + key);
        // System.out.println("Plaintext: " + plaintext);

        // plaintext to binary
        String binaryPlainText = BlockEncrypt.asciiToBinary(plaintext);

        // Choose modes to encrypt with
        String[] modes = { "CBC" }; // DEFINE THE MODES HERE

        // Encrypt using the chosen modes
        for (String mode : modes) {
            processEncryptionMode(mode.trim(), binaryPlainText, binaryKey, binaryIV, inputFilePath);
        }

        // Decryption process
        String encryptedFilePath = "Text/jaydon_CTR_ciphertext.txt"; // DEFINE THE CIPHERTEXT FILE PATH HERE
        String decryptionMode = "CTR"; // DEFINE THE DECRYPTION MODE HERE
        String decryptionIV = "1000100110001011101"; // DEFINE THE IV USED FOR ENCRYPTION HERE
        String decryptionBinaryKey = "11110000101101110011000010101000101"; // DEFINE THE KEY USED FOR ENCRYPTION
  


        String encryptedText = "";
        try {
            encryptedText = new String(Files.readAllBytes(Paths.get(encryptedFilePath)));
            
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Validate the binary string
        validateBinaryString(encryptedText);

       // format to an array of 35 bit blocks
        List<String> encryptedBlocks = BlockEncrypt.convertFormattedBinaryToBlocks(encryptedText);

        // encrypted block array
        System.out.println("Encrypted Blocks: " + encryptedBlocks);
        processDecryptionMode(decryptionMode, encryptedBlocks, decryptionBinaryKey, decryptionIV, encryptedFilePath);
    }

    private static void processEncryptionMode(String mode, String binaryPlainText, String binaryKey, String iv,
            String inputFilePath) {
        List<String> encryptedBlocks = null;
        String filePrefix = inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1, inputFilePath.indexOf('_'));
        String encryptedFilePath = "Text/" + filePrefix + "_" + mode + "_ciphertext.txt";

        switch (mode) {
            case "CBC":
                encryptedBlocks = BlockEncrypt.CBCMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey, iv);
                break;
            case "ECB":
                encryptedBlocks = BlockEncrypt.ECBMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey);
                break;
            case "CFB":
                encryptedBlocks = BlockEncrypt.CFBMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey, iv);
                break;
            case "OFB":
                encryptedBlocks = BlockEncrypt.OFBMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey, iv);
                break;
            case "CTR":
                encryptedBlocks = BlockEncrypt.CTRMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey, iv);
                break;
            default:
                System.out.println("Invalid mode selected.");
                return;
        }

        // Write the encrypted data to a file
        String encryptedText = BlockEncrypt.joinBinaryArray(encryptedBlocks);
        String formattedEncryptedText = BlockEncrypt.formatBinaryString(encryptedText);
        try {
            Files.write(Paths.get(encryptedFilePath), formattedEncryptedText.getBytes(), StandardOpenOption.CREATE);
            System.out.println(mode + " Encrypted Blocks written to: " + encryptedFilePath);
        } catch (IOException e) {
            System.err.println("Error writing the encrypted file: " + e.getMessage());
        }
    }

    private static void processDecryptionMode(String mode, List<String> encryptedBlocks, String decryptionBinaryKey,
            String iv, String encryptedFilePath) {
        List<String> decryptedBlocks = null;
        String filePrefix = encryptedFilePath.substring(encryptedFilePath.lastIndexOf('/') + 1,
                encryptedFilePath.indexOf('_'));
        String decryptedFilePath = "Text/" + filePrefix + "_" + mode + "_decryption.txt";

        switch (mode) {
            case "CBC":
                decryptedBlocks = BlockEncrypt.decryptCBCMode(encryptedBlocks, decryptionBinaryKey, iv);
                break;
            case "ECB":
                decryptedBlocks = BlockEncrypt.decryptECBMode(encryptedBlocks, decryptionBinaryKey);
                break;
            case "CFB":
                decryptedBlocks = BlockEncrypt.decryptCFBMode(encryptedBlocks, decryptionBinaryKey, iv);
                break;
            case "OFB":
                decryptedBlocks = BlockEncrypt.decryptOFBMode(encryptedBlocks, decryptionBinaryKey, iv);
                break;
            case "CTR":
                decryptedBlocks = BlockEncrypt.decryptCTRMode(encryptedBlocks, decryptionBinaryKey, iv);
                break;
            default:
                System.out.println("Invalid mode selected.");
                return;
        }

        // Convert the decrypted blocks back to ASCII
        String joinedDecryptedBlocks = BlockEncrypt.joinBinaryArray(decryptedBlocks);
        System.out.println("Joined Decrypted Blocks: " + joinedDecryptedBlocks);
        validateBinaryString(joinedDecryptedBlocks); // Validate the joined decrypted blocks
        String decryptedText = BlockEncrypt.binaryToAscii(joinedDecryptedBlocks);

        // Write the decrypted data to a file
        try {
            Files.write(Paths.get(decryptedFilePath), decryptedText.getBytes(), StandardOpenOption.CREATE);
            System.out.println(mode + " Decrypted Text written to: " + decryptedFilePath);
        } catch (IOException e) {
            System.err.println("Error writing the decrypted file: " + e.getMessage());
        }

        // Format the decrypted blocks and write to a file
        String formattedDecryptedBlocks = BlockEncrypt.formatBinaryString(joinedDecryptedBlocks);
        String formattedDecryptedFilePath = "Text/" + filePrefix + "_" + mode + "_formatted_decryption.txt";
        try {
            Files.write(Paths.get(formattedDecryptedFilePath), formattedDecryptedBlocks.getBytes(),
                    StandardOpenOption.CREATE);
            System.out.println(mode + " Formatted Decrypted Blocks written to: " + formattedDecryptedFilePath);
        } catch (IOException e) {
            System.err.println("Error writing the formatted decrypted file: " + e.getMessage());
        }
    }

    private static void validateBinaryString(String binaryString) {
        String unformattedBinaryString = BlockEncrypt.unformatBinaryString(binaryString);
        System.out.println("Unformatted Binary String: " + unformattedBinaryString);
        for (int i = 0; i < unformattedBinaryString.length(); i += 7) {
            String segment = unformattedBinaryString.substring(i, Math.min(i + 7, unformattedBinaryString.length()));
            try {
                Integer.parseInt(segment, 2);
            } catch (NumberFormatException e) {
                System.err.println("Invalid binary segment: " + segment + " at position " + i);
                return;
            }
        }
        System.out.println("All binary segments are valid.");
    }
}