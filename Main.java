import java.util.List;

public class Main {
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
        List<String> encryptedBlocks = BlockEncrypt.CBCMode(BlockEncrypt.splitBinaryArray(binaryPlainText), binaryKey, iv);
        System.out.println("CBCEncrypted Blocks: " + encryptedBlocks);

        // Decrypt the CBC encrypted blocks
        List<String> decryptedBlocks = BlockEncrypt.decryptCBCMode(encryptedBlocks, binaryKey, iv);
        System.out.println("CBCDecrypted Blocks: " + decryptedBlocks);

        // Convert the decrypted blocks back to ASCII
        String cbcDecryptedText = BlockEncrypt.binaryToAscii(BlockEncrypt.joinBinaryArray(decryptedBlocks));
        System.out.println("Decrypted Text: " + cbcDecryptedText);


    }
}