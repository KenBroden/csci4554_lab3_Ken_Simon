import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockEncrypt {
    private static final int BLOCK_SIZE = 35;
    // private static final int KEY_SIZE = 35;

    // Convert ascii to binary
    public static String asciiToBinary(String asciiString) {
        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 7; i++) {
                binary.append((val & 64) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    // Convert binary to ascii
    public static String binaryToAscii(String binaryString) {
        StringBuilder ascii = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            if (i + 7 <= binaryString.length()) {
                String byteString = binaryString.substring(i, i + 7);
                int charCode = Integer.parseInt(byteString, 2);
                ascii.append((char) charCode);
            }
        }
        return ascii.toString();
    }

    // XOR two binary strings
    public static String bitwiseXOR(String block, String key) {
        StringBuilder result = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < block.length(); i++) {
            result.append(block.charAt(i) ^ key.charAt(i % keyLength));
        }
        return result.toString();
    }

    // Right circular shift by 3 bits
    public static String rightCircularShift(String block, int shift) {
        int len = block.length();
        shift = shift % len;
        return block.substring(len - shift) + block.substring(0, len - shift);
    }

    // generate a random 5 ascii
    public static String generateRandomIV() {
        Random random = new Random();
        StringBuilder iv = new StringBuilder();
        for (int i = 0; i < BLOCK_SIZE; i++) {
            iv.append(random.nextInt(2));
        }
        return iv.toString();
    }

    // split binary array into blocks
    public static List<String> splitBinaryArray(String input) {
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < input.length(); i += BLOCK_SIZE) {
            blocks.add(input.substring(i, Math.min(i + BLOCK_SIZE, input.length())));
        }
        return blocks;
    }

    // Encrypt a block with the given key
    public static String encryptBlock(String block, String key) {
        block = rightCircularShift(block, 3);
        return bitwiseXOR(block, key);
    }

    // Decrypt a block with the given key
    public static String decryptBlock(String block, String key) {
        block = bitwiseXOR(block, key);
        return rightCircularShift(block, block.length() - 3);
    }

    // add padding / 0s to block
    public static String padBlock(String block) {
        while (block.length() < BLOCK_SIZE) {
            block += "0";
        }
        return block;
    }

    // remove padding
    public static String removePadding(String block) {
        return block.replaceAll("0+$", "");
    }

    // Join binary array into a single string
    public static String joinBinaryArray(List<String> blocks) {
        StringBuilder joined = new StringBuilder();
        for (String block : blocks) {
            joined.append(block);
        }
        return joined.toString();
    }

    // Electronic Codebook (ECB) mode
    // each block of plaintext is encrypted independently with the same key
    public static List<String> ECBMode(List<String> inputBlocks, String key) {
        List<String> encryptedBlocks = new ArrayList<>();
        for (String block : inputBlocks) {
            String paddedBlock = padBlock(block);
            String encryptedBlock = encryptBlock(paddedBlock, key);
            encryptedBlocks.add(encryptedBlock);
        }
        return encryptedBlocks;
    }

    // Cipher Block Chaining (CBC) mode
    // each block of plaintext is XORed with the previous ciphertext block before
    // encryption
    public static List<String> CBCMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            block = padBlock(block);
            block = bitwiseXOR(block, previousBlock);
            String encryptedBlock = encryptBlock(block, key);
            encryptedBlocks.add(encryptedBlock);
            previousBlock = encryptedBlock;
        }
        return encryptedBlocks;
    }

    // CBC decryption
    public static List<String> decryptCBCMode(List<String> encryptedBlocks, String key, String iv) {
        List<String> decryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : encryptedBlocks) {
            String decryptedBlock = decryptBlock(block, key);
            decryptedBlock = bitwiseXOR(decryptedBlock, previousBlock);
            decryptedBlocks.add(decryptedBlock);
            previousBlock = block;
        }
        // Remove padding from the last block
        int lastIndex = decryptedBlocks.size() - 1;
        decryptedBlocks.set(lastIndex, removePadding(decryptedBlocks.get(lastIndex)));
        return decryptedBlocks;
    }

    // Cipher Feedback (CFB) mode
    // each block of ciphertext is XORed with the output of the encryption function
    public static List<String> CFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            String encryptedBlock = encryptBlock(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = cipherBlock;
        }
        return encryptedBlocks;
    }

    // Output Feedback (OFB) mode
    // generates keystream blocks which are XORed with the plaintext blocks
    // ** Does not require padding the last block **
    public static List<String> OFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            String encryptedBlock = encryptBlock(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = encryptedBlock;
        }
        return encryptedBlocks;
    }

    // Counter (CTR) mode
    // Counter value is combined with IV to create a unique counter block for each
    // plaintext block
    // ** Does not require padding the last block **
    public static List<String> CTRMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        int counter = 0;
        for (String block : inputBlocks) {
            String counterBlock = iv + String.format("%16s", Integer.toBinaryString(counter)).replace(' ', '0');
            counterBlock = counterBlock.substring(counterBlock.length() - BLOCK_SIZE);
            String encryptedBlock = encryptBlock(counterBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            counter++;
        }
        return encryptedBlocks;
    }
}