public class Main {
    public static void main(String[] args) {
        String key = "a5Z#\t"; // Example key
        String plaintext = "Hello"; // Example plaintext

        // Convert plaintext and key to binary
        String binaryPlaintext = BlockEncrypt.asciiToBinary(plaintext);
        String binaryKey = BlockEncrypt.asciiToBinary(key);

        // Encrypt the plaintext
        String encrypted = BlockEncrypt.encryptBlock(binaryPlaintext, binaryKey);
        System.out.println("Encrypted: " + encrypted);

        // Decrypt the ciphertext
        String decrypted = BlockEncrypt.decryptBlock(encrypted, binaryKey);
        System.out.println("Decrypted: " + decrypted);

        // Convert decrypted binary back to ASCII
        String decryptedText = BlockEncrypt.binaryToAscii(decrypted);
        System.out.println("Decrypted Text: " + decryptedText);

        // Verify that the decrypted text matches the original plaintext
        if (plaintext.equals(decryptedText)) {
            System.out.println("Encryption and decryption successful!");
        } else {
            System.out.println("Encryption and decryption failed.");
        }
    }
}