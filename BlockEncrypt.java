import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockEncrypt {
    private static final int BLOCK_SIZE = 35;
    //private static final int KEY_SIZE = 35;

    // Convert ascii to binary
    public static String asciiToBinary(String asciiString) {
        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    public static String generateRandomIV() {
        Random random = new Random();
        StringBuilder iv = new StringBuilder();
        for (int i = 0; i < BLOCK_SIZE; i++) {
            iv.append(random.nextInt(2));
        }
        return iv.toString();
    }

    public static List<String> splitBinaryArray(String input) {
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < input.length(); i += BLOCK_SIZE) {
            blocks.add(input.substring(i, Math.min(i + BLOCK_SIZE, input.length())));
        }
        return blocks;
    }

    public static String padBlock(String block) {
        while (block.length() < BLOCK_SIZE) {
            block += "0";
        }
        return block;
    }

    public static List<String> ECBMode(List<String> inputBlocks, String key) {
        List<String> encryptedBlocks = new ArrayList<>();
        for (String block : inputBlocks) {
            String encryptedBlock = encrypt(block, key);
            encryptedBlocks.add(encryptedBlock);
        }
        return encryptedBlocks;
    }

    public static List<String> CBCMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            block = bitwiseXOR(block, previousBlock);
            String encryptedBlock = encrypt(block, key);
            encryptedBlocks.add(encryptedBlock);
            previousBlock = encryptedBlock;
        }
        return encryptedBlocks;
    }

    public static List<String> CFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            String encryptedBlock = encrypt(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = cipherBlock;
        }
        return encryptedBlocks;
    }

    public static List<String> OFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            String encryptedBlock = encrypt(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = encryptedBlock;
        }
        return encryptedBlocks;
    }

    public static List<String> CTRMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        int counter = 0;
        for (String block : inputBlocks) {
            String counterBlock = iv + String.format("%16s", Integer.toBinaryString(counter)).replace(' ', '0');
            counterBlock = counterBlock.substring(counterBlock.length() - BLOCK_SIZE);
            String encryptedBlock = encrypt(counterBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            counter++;
        }
        return encryptedBlocks;
    }

    public static String encrypt(String block, String key) {
        // Implement the encryption logic here
        // This is a placeholder implementation
        return bitwiseXOR(block, key);
    }

    public static String bitwiseXOR(String block, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < block.length(); i++) {
            result.append(block.charAt(i) ^ key.charAt(i));
        }
        return result.toString();
    }
}