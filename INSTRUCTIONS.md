# Instructions

## Encryption

1. Open Main.java:
Ensure you have the Main.java file open in your workspace.

2. Set the Input File Path:

    Locate the line where the input file path is defined:

    ```java
    String inputFilePath = "Text/deepblue_plaintext.txt"; // DEFINE THE FILE PATH HERE
    ```

    Replace "Text/deepblue_plaintext.txt" with the path to your plaintext file.

3. Set the Encryption Key:

    Locate the line where the key is defined:

    ```java
    String key = "1111000 0101101 1100110 0001010 1000101"; // DEFINE THE KEY HERE
    ```

    Replace "1111000 0101101 1100110 0001010 1000101" with your encryption key.

4. Set the Initialization Vector (IV) (if required by the mode):

    Locate the line where the IV is defined:

    ```java
    String iv = "1001001 0111001 1100000 0100101 1001010"; // DEFINE THE IV HERE
    ```

    Replace "1001001 0111001 1100000 0100101 1001010" with your IV. Note that ECB mode does not require an IV.

    If you would like to generate a randomly generated IV of 35 binary digits:

    ```java
    String iv = BlockEncrypt.generateIV();
    ```

5. Choose the Encryption Mode:

    Locate the line where the encryption modes are defined:

    ```java
    String[] modes = { "ECB" }; // DEFINE THE MODES HERE
    ```

    Replace "ECB" with the desired encryption mode/s (e.g., "CBC", "CFB", "OFB", "CTR").

6. Run the Encryption:

    Save the changes to `Main.java`.
    Run the Main class to perform the encryption. The encrypted data will be written to a file in the Text directory with a name based on the input file and mode.

    Sure, here are the decryption instructions following a similar style:

## Decryption

1. Open Main.java:
Ensure you have the `Main.java` file open in your workspace.

2. Set the Encrypted File Path:

Locate the line where the encrypted file path is defined:

```java
String encryptedFilePath = "Text/deepblue_ECB_ciphertext.txt"; // DEFINE THE CIPHERTEXT FILE PATH HERE
```

Replace "Text/deepblue_ECB_ciphertext.txt" with the path to your encrypted file.

3. Set the Decryption Key:

Locate the line where the decryption key is defined:

```java
String decryptionBinaryKey = "1111000 0101101 1100110 0001010 1000101"; // DEFINE THE KEY USED FOR ENCRYPTION HERE
```

Replace "1111000 0101101 1100110 0001010 1000101" with your decryption key.

4. Set the Initialization Vector (IV) (if required by the mode):

Locate the line where the IV is defined:

```java
String decryptionIV = "1001001 0111001 1100000 0100101 1001010"; // DEFINE THE IV USED FOR ENCRYPTION HERE
```

Replace "1001001 0111001 1100000 0100101 1001010" with the IV used during encryption. Note that ECB mode does not require an IV.

5. Choose the Decryption Mode:

Locate the line where the decryption mode is defined:

```java
String decryptionMode = "ECB"; // DEFINE THE DECRYPTION MODE HERE
```

Replace "ECB" with the desired decryption mode (e.g., "CBC", "CFB", "OFB", "CTR").

6. Run the Decryption:

Save the changes to `Main.java`. Run the Main class to perform the decryption. The decrypted data will be written to a file in the Text directory with a name based on the input file and mode.
