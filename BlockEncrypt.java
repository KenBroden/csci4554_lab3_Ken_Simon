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
            block = padBlock(block);
            String encryptedBlock = encryptBlock(block, key);
            encryptedBlocks.add(encryptedBlock);
        }
        return encryptedBlocks;
    }

    // ECB decryption
    public static List<String> decryptECBMode(List<String> encryptedBlocks, String key) {
        List<String> decryptedBlocks = new ArrayList<>();
        for (String block : encryptedBlocks) {
            String decryptedBlock = decryptBlock(block, key);
            decryptedBlocks.add(decryptedBlock);
        }
        // Remove padding from the last block
        int lastIndex = decryptedBlocks.size() - 1;
        decryptedBlocks.set(lastIndex, removePadding(decryptedBlocks.get(lastIndex)));
        return decryptedBlocks;
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
    // each block of plaintext is XORed with the output of the encryption function
    public static List<String> CFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            block = padBlock(block);
            String encryptedBlock = encryptBlock(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, encryptedBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = cipherBlock;
        }
        return encryptedBlocks;
    }

    // CFB decryption
    public static List<String> decryptCFBMode(List<String> encryptedBlocks, String key, String iv) {
        List<String> decryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : encryptedBlocks) {
            String encryptedBlock = encryptBlock(previousBlock, key);
            String decryptedBlock = bitwiseXOR(block, encryptedBlock);
            decryptedBlocks.add(decryptedBlock);
            previousBlock = block;
        }
        // Remove padding from the last block
        int lastIndex = decryptedBlocks.size() - 1;
        decryptedBlocks.set(lastIndex, removePadding(decryptedBlocks.get(lastIndex)));
        return decryptedBlocks;
    }

    // Output Feedback (OFB) mode
    // generates keystream blocks which are XORed with the plaintext blocks
    // ** Does not require padding the last block **
    public static List<String> OFBMode(List<String> inputBlocks, String key, String iv) {
        List<String> encryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : inputBlocks) {
            String keystreamBlock = encryptBlock(previousBlock, key);
            String cipherBlock = bitwiseXOR(block, keystreamBlock);
            encryptedBlocks.add(cipherBlock);
            previousBlock = keystreamBlock;
        }
        return encryptedBlocks;
    }

    // OFB decryption
    public static List<String> decryptOFBMode(List<String> encryptedBlocks, String key, String iv) {
        List<String> decryptedBlocks = new ArrayList<>();
        String previousBlock = iv;
        for (String block : encryptedBlocks) {
            String keystreamBlock = encryptBlock(previousBlock, key);
            String decryptedBlock = bitwiseXOR(block, keystreamBlock);
            decryptedBlocks.add(decryptedBlock);
            previousBlock = keystreamBlock;
        }
        return decryptedBlocks;
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

    // CTR decryption
    public static List<String> decryptCTRMode(List<String> encryptedBlocks, String key, String iv) {
        List<String> decryptedBlocks = new ArrayList<>();
        int counter = 0;
        for (String block : encryptedBlocks) {
            String counterBlock = iv + String.format("%16s", Integer.toBinaryString(counter)).replace(' ', '0');
            counterBlock = counterBlock.substring(counterBlock.length() - BLOCK_SIZE);
            String encryptedBlock = encryptBlock(counterBlock, key);
            String decryptedBlock = bitwiseXOR(block, encryptedBlock);
            decryptedBlocks.add(decryptedBlock);
            counter++;
        }
        return decryptedBlocks;
    }

    // Format binary string with spaces every 7 characters and new lines every 35
    // characters
    public static String formatBinaryString(String binaryString) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            if (i > 0 && i % 35 == 0) {
                formatted.append("\n");
            }
            formatted.append(binaryString, i, Math.min(i + 7, binaryString.length()));
            if (i + 7 < binaryString.length()) {
                formatted.append(" ");
            }
        }
        return formatted.toString();
    }

    // Unformat binary string by removing spaces and new lines
    public static String unformatBinaryString(String formattedBinaryString) {
        return formattedBinaryString.replaceAll("[\\s\\n]", "");
    }

    // Method to convert formatted binary data to an array of 35-bit blocks
    public static List<String> convertFormattedBinaryToBlocks(String formattedBinary) {
        // Remove spaces and newlines
        String continuousBinary = formattedBinary.replaceAll("[\\s\\n]", "");

        // Split into 35-bit blocks
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < continuousBinary.length(); i += 35) {
            blocks.add(continuousBinary.substring(i, Math.min(i + 35, continuousBinary.length())));
        }

        return blocks;
    }

    // TESTING THE IMPLEMENTATION
    public static void main(String[] args) {
        String key = "a5Z#\t"; // Example key
        String plaintext = "Hello"; // Example plaintext

        // Convert plaintext and key to binary
        String binaryPlaintext = BlockEncrypt.asciiToBinary(plaintext);
        String binaryKey = BlockEncrypt.asciiToBinary(key);

        System.out.println("\nTask 1: implementing block cipher\n");

        // Print the ascii encoding of the plaintext
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Plaintext in binary: " + binaryPlaintext);

        // After right shift
        String rightShift = BlockEncrypt.rightCircularShift(binaryPlaintext, 3);
        System.out.println("Right Shift: " + rightShift);

        // key in binary
        System.out.println("Key in binary: " + binaryKey);

        // add the key bitwise after right shift
        String xor = BlockEncrypt.bitwiseXOR(rightShift, binaryKey);
        System.out.println("XOR: " + xor);

        // Encrypt the plaintext
        String encrypted = BlockEncrypt.encryptBlock(binaryPlaintext, binaryKey);
        // Decrypt the ciphertext
        String decrypted = BlockEncrypt.decryptBlock(encrypted, binaryKey);
        // Convert decrypted binary back to ASCII
        String decryptedText = BlockEncrypt.binaryToAscii(decrypted);
        // Verify that the decrypted text matches the original plaintext
        if (plaintext.equals(decryptedText)) {
            System.out.println("Encryption and decryption successful!");
        } else {
            System.out.println("Encryption and decryption failed.");
        }

        System.out.println("\nTask 2: implementing modes\n");

        // Define the key and IV
        String iv = BlockEncrypt.generateRandomIV();
        System.out.println("IV: " + iv);

        // Define a plain text [JUST FOR TESTING FOR NOW - Ken]
        String plainText = "Hello World! I love cryptography!";
        System.out.println("Plaintext: " + plainText);

        // plaintext to binary
        String binaryPlainText = BlockEncrypt.asciiToBinary(plainText);

        // Encrypt the plaintext using CBC mode
        List<String> encryptedBlocks = BlockEncrypt.CBCMode(BlockEncrypt.splitBinaryArray(binaryPlainText),
                binaryKey, iv);
        System.out.println("\nCBCEncrypted Blocks: " + encryptedBlocks);

        // format the encrypted blocks
        String formattedEncryptedBlocks = BlockEncrypt
                .formatBinaryString(BlockEncrypt.joinBinaryArray(encryptedBlocks));
        System.out.println("\nCBCEncrypted Blocks (Formatted): \n" + formattedEncryptedBlocks);

        // Convert the formatted encrypted blocks back to an array of 35-bit blocks
        List<String> encryptedBlocksFormatted = BlockEncrypt.convertFormattedBinaryToBlocks(formattedEncryptedBlocks);
        System.out.println("\nCBCEncrypted Blocks (Converted): " + encryptedBlocksFormatted);

        // Decrypt the formatted encrypted blocks
        String unformattedEncryptedBlocks = BlockEncrypt.unformatBinaryString(formattedEncryptedBlocks);
        List<String> encryptedBlocksUnformatted = BlockEncrypt.splitBinaryArray(unformattedEncryptedBlocks);
        List<String> decryptedBlocks = BlockEncrypt.decryptCBCMode(encryptedBlocksUnformatted, binaryKey, iv);
        System.out.println("CBCDecrypted Blocks: " + decryptedBlocks);

        // Convert the decrypted blocks back to ASCII
        String cbcDecryptedText = BlockEncrypt.binaryToAscii(
                BlockEncrypt.joinBinaryArray(BlockEncrypt.decryptCBCMode(encryptedBlocks, binaryKey, iv)));
        System.out.println("CBCDecrypted Text: " + cbcDecryptedText);

        // Encrypt the plaintext using ECB mode
        List<String> encryptedBlocksECB = BlockEncrypt.ECBMode(BlockEncrypt.splitBinaryArray(binaryPlainText),
                binaryKey);
        System.out.println("\nECBEncrypted Blocks: " + encryptedBlocksECB);

        // Decrypt the ECB encrypted blocks
        List<String> decryptedBlocksECB = BlockEncrypt.decryptECBMode(encryptedBlocksECB, binaryKey);
        // System.out.println("ECBDecrypted Blocks: " + decryptedBlocksECB);

        // Convert the decrypted blocks back to ASCII
        String ecbDecryptedText = BlockEncrypt.binaryToAscii(BlockEncrypt.joinBinaryArray(decryptedBlocksECB));
        System.out.println("\nECBDecrypted Text: " + ecbDecryptedText);

        // Encrypt the plaintext using CFB mode
        List<String> encryptedBlocksCFB = BlockEncrypt.CFBMode(BlockEncrypt.splitBinaryArray(binaryPlainText),
                binaryKey, iv);
        System.out.println("\nCFBEncrypted Blocks: " + encryptedBlocksCFB);

        // Decrypt the CFB encrypted blocks
        List<String> decryptedBlocksCFB = BlockEncrypt.decryptCFBMode(encryptedBlocksCFB, binaryKey, iv);
        // System.out.println("CFBDecrypted Blocks: " + decryptedBlocksCFB);

        // Convert the decrypted blocks back to ASCII
        String cfbDecryptedText = BlockEncrypt.binaryToAscii(BlockEncrypt.joinBinaryArray(decryptedBlocksCFB));
        System.out.println("\nCFBDecrypted Text: " + cfbDecryptedText);

        // Encrypt the plaintext using OFB mode
        List<String> encryptedBlocksOFB = BlockEncrypt.OFBMode(BlockEncrypt.splitBinaryArray(binaryPlainText),
                binaryKey, iv);
        System.out.println("\nOFBEncrypted Blocks: " + encryptedBlocksOFB);

        // Decrypt the OFB encrypted blocks
        List<String> decryptedBlocksOFB = BlockEncrypt.decryptOFBMode(encryptedBlocksOFB, binaryKey, iv);
        // System.out.println("OFBDecrypted Blocks: " + decryptedBlocksOFB);

        // Convert the decrypted blocks back to ASCII
        String ofbDecryptedText = BlockEncrypt.binaryToAscii(BlockEncrypt.joinBinaryArray(decryptedBlocksOFB));
        System.out.println("OFBDecrypted Text: " + ofbDecryptedText);

        // Encrypt the plaintext using CTR mode
        List<String> encryptedBlocksCTR = BlockEncrypt.CTRMode(BlockEncrypt.splitBinaryArray(binaryPlainText),
                binaryKey, iv);
        System.out.println("\nCTREncrypted Blocks: " + encryptedBlocksCTR);

        // Decrypt the CTR encrypted blocks
        List<String> decryptedBlocksCTR = BlockEncrypt.decryptCTRMode(encryptedBlocksCTR, binaryKey, iv);
        // System.out.println("CTRDecrypted Blocks: " + decryptedBlocksCTR);

        // Convert the decrypted blocks back to ASCII
        String ctrDecryptedText = BlockEncrypt.binaryToAscii(BlockEncrypt.joinBinaryArray(decryptedBlocksCTR));
        System.out.println("CTRDecrypted Text: " + ctrDecryptedText);

    }
}