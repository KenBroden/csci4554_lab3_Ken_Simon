
// Encryption for Block cipher
public class BlockEncrypt {
    
    private static final int BLOCK_SIZE = 35;
    private static final int KEY_SIZE = 35;

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


}