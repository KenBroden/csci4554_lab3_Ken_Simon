# Results

- *Lab 4 Bin Packing by Ken Broden & Simon Harrison-Michaels*

## Task 1: implementing block cipher

The methods for this task are written in `BlockEncrypt.java` and tested in the main() method of `BlockEncrypt.java`.

```java
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
```

Results:

```sh
Plaintext: Hello
Plaintext in binary: 10010001100101110110011011001101111
Right Shift: 11110010001100101110110011011001101
Key in binary: 11000010110101101101001000110001001
XOR: 00110000111001000011111011101000100
Encryption and decryption successful!
```

## Task 2: implementing modes

Here is my testing of the different block cipher modes, as well as some formatting testing of the data.

```java
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
```

Results:

```sh
IV: 00111110101110101111010111110111101
Plaintext: Hello World! I love cryptography!

CBCEncrypted Blocks: [10010111001100110110000001010110011, 00111000000110110101000111011110010, 10011100110001010001100010011000011, 10101010011110011000011111101000101, 11101111011000001001101100101101111, 00100100110010010011001110010111010, 10011100011100110101010101000011110]

CBCEncrypted Blocks (Formatted):
1001011 1001100 1101100 0000101 0110011
0011100 0000110 1101010 0011101 1110010
1001110 0110001 0100011 0001001 1000011
1010101 0011110 0110000 1111110 1000101
1110111 1011000 0010011 0110010 1101111
0010010 0110010 0100110 0111001 0111010
1001110 0011100 1101010 1010100 0011110

CBCEncrypted Blocks (Converted): [10010111001100110110000001010110011, 00111000000110110101000111011110010, 10011100110001010001100010011000011, 10101010011110011000011111101000101, 11101111011000001001101100101101111, 00100100110010010011001110010111010, 10011100011100110101010101000011110]
CBCDecrypted Blocks: [10010001100101110110011011001101111, 01000001010111110111111100101101100, 11001000100001010000010010010100000, 11011001101111111011011001010100000, 11000111110010111100111100001110100, 11011111100111111001011000011110000, 110100011110010100001]
CBCDecrypted Text: Hello World! I love cryptography!

ECBEncrypted Blocks: [00110000111001000011111011101000100, 01001010111111010011110111010100100, 11011011110001100111001010100011101, 11011001111000010010010011111011101, 01011010001011111010101111010000111, 11011001001001010010000011110010111, 11011000111010100111001100110001001]
ECBDecrypted Text: Hello World! I love cryptography!

CFBEncrypted Blocks: [11110100100101101110101001000010001, 10111101000110110111000001010100111, 11111101111100001011100010101111101, 10100100110101110111001101110000110, 11010001100001111111001101010001101, 10100111011110011011101001000101000, 00000111110111001110110101111001100]
CFBDecrypted Text: Hello World! I love cryptography!

OFBEncrypted Blocks: [11110100100101101110101001000010001, 01001111001010011001110010001101010, 11001011100111010000101011010001001, 00111011000010100110010110101101100, 10011001010010111010011101000000100, 00010110100110010100100100100110111, 111010100001001100000]
OFBDecrypted Text: Hello World! I love cryptography!

CTREncrypted Blocks: [01001101111111110100000011111100110, 10111101001101110101100100011100101, 01010100111011010010001010100101001, 01100101110101111001000001100101001, 10011011101000111110100100111111101, 10100011111101111011000000101111001, 110011011000110100101]
CTRDecrypted Text: Hello World! I love cryptography!
```

## Task 3: exchanging tests

Our plaintext for OFB encryption:

```txt
    Alan Mathison Turing OBE FRS (23 June 1912 7 June 1954) was an English mathematician, computer scientist, logician, 
    cryptanalyst, philosopher and theoretical biologist.[5] He was highly influential in the development of theoretical 
    computer science, providing a formalisation of the concepts of algorithm and computation with the Turing machine, 
    which can be considered a model of a general-purpose computer.[6][7][8] Turing is widely considered to be the father 
    of theoretical computer science.[9]

    Born in London, Turing was raised in southern England. He graduated from King's College, Cambridge, and in 1938, 
    earned a doctorate degree from Princeton University. During World War II, Turing worked for the Government Code and 
    Cypher School at Bletchley Park, Britain's codebreaking centre that produced Ultra intelligence. He led Hut 8, 
    the section responsible for German naval cryptanalysis. Turing devised techniques for speeding the breaking of German ciphers, 
    including improvements to the pre-war Polish bomba method, an electromechanical machine that could find settings for 
    the Enigma machine. He played a crucial role in cracking intercepted messages that enabled the Allies to defeat the 
    Axis powers in many crucial engagements, including the Battle of the Atlantic
```

key = "1111000 0101101 1100110 0001010 1000101";
iv = "1001001 0111001 1100000 0100101 1001010";

plaintext for ECB encryption:

```txt
    Deep Blue was a chess-playing expert system run on a unique purpose-built IBM supercomputer. It was the first computer 
    to win a game, and the first to win a match, against a reigning world champion under regular time controls. Development 
    began in 1985 at Carnegie Mellon University under the name ChipTest. It then moved to IBM, where it was first renamed 
    Deep Thought, then again in 1989 to Deep Blue. It first played world champion Garry Kasparov in a six-game match in 1996, 
    where it lost four games to two. It was upgraded in 1997 and in a six-game re-match, it defeated Kasparov by winning two 
    games and drawing three. Deep Blue's victory is considered a milestone in the history of artificial intelligence and has 
    been the subject of several books and films.

    History
    While a doctoral student at Carnegie Mellon University, Feng-hsiung Hsu began development of a chess-playing supercomputer 
    under the name ChipTest. The machine won the North American Computer Chess Championship in 1987 and Hsu and his team 
    followed up with a successor, Deep Thought, in 1988.[2][3] After receiving his doctorate in 1989, Hsu and Murray Campbell 
    joined IBM Research to continue their project to build a machine that could defeat a world chess champion.[4] Their 
    colleague Thomas Anantharaman briefly joined them at IBM before leaving for the finance industry and being replaced by 
    programmer Arthur Joseph Hoane.[5][6] Jerry Brody, a long-time employee of IBM Research, subsequently joined the team 
    in 1990.[7]

    After Deep Thought's two-game 1989 loss to Kasparov, IBM held a contest to rename the chess machine: the winning name was 
    "Deep Blue", submitted by Peter Fitzhugh Brown,[8] was a play on IBM's nickname, "Big Blue".[a] After a scaled-down 
    version of Deep Blue played Grandmaster Joel Benjamin,[10] Hsu and Campbell decided that Benjamin was the expert they 
    were looking for to help develop Deep Blue's opening book, so hired him to assist with the preparations for Deep Blue's 
    matches against Garry Kasparov.[11] In 1995, a Deep Blue prototype played in the eighth World Computer Chess Championship, 
    playing Wchess to a draw before ultimately losing to Fritz in round five, despite playing as White.[12]

    Today, one of the two racks that made up Deep Blue is held by the National Museum of American History, having previously 
    been displayed in an exhibit about the Information Age,[13] while the other rack was acquired by the Computer History 
    Museum in 1997, and is displayed in the Revolution exhibit's "Artificial Intelligence and Robotics" gallery.[14] Several
    books were written about Deep Blue, among them Behind Deep Blue: Building the Computer that Defeated the World Chess 
    Champion by Deep Blue developer Feng-hsiung Hsu.[15]
```

key = "1111000 0101101 1100110 0001010 1000101";

### Jaydon & Malena CBC Decryption

key is: 1111000 0101101 1100110 0001010 1000101
IV is: 1001001 0111001 1100000 0100101 1001010

```txt
Daddy's flown across the ocean
Leaving just a memory
Snapshot in the family album
Daddy what else did you leave for me
Daddy, what'd'ja leave behind for me
All in all it was just a brick in the wall
All in all it was all just bricks in the wall

We don't need no education
We don't need no thought control
No dark sarcasm in the classroom
Teacher leave them kids alone
Hey, teacher, leave them kids alone
All in all it's just another brick in the wall
All in all you're just another brick in the wall

We don't need no education
We don't need no thought control
No dark sarcasm in the classroom
Teachers leave them kids alone
Hey, teacher, leave us kids alone
All in all you're just another brick in the wall
All in all you're just another brick in the wall

I don't need no arms around me
And I don't need no drugs to calm me
I have seen the writing on the wall
Don't think I need anything at all
No, don't think I'll need anything at all
All in all it was all just bricks in the wall
All in all you were all just bricks in the wall
```

### Jaydon & Malena CTR Decryption

key is: 1111000 0101101 1100110 0001010 1000101
IV is: 1000100 1100010 11101

```txt
How all occasions do inform against me
And spur my dull revenge! What is a man
If his chief good and market of his time
Be but to sleep and feed? A beast, no more.
Sure he that made us with such large discourse,
Looking before and after, gave us not
That capability and godlike reason
To fust in us unused. Now, whether it be
Bestial oblivion, or some craven scruple
Of thinking too precisely on the event
A thought which, quartered, hath but one part wisdom
And ever three parts coward; I do not know
Why yet I live to say 'This thing's to do'
Sith I have cause, and will, and strength, and means
To do it. Examples, gross as earth, exhort me:
Witness this army, of such mass and charge,
Led by a delicate and tender prince.
Whose spirit, with divine ambition puffed,
Makes mouths at the invisible event.
Exposing what is mortal and unsure
To all that fortune, death, and danger dare,
Even for an egg-shell. Rightly to be great
Is not to stir without great argument,
But greatly to find quarrel in a straw
When honour's at the stake. How stand I, then,
That have a father killed, a mother stained,
Excitements of my reason and my blood,
And let all sleep? While, to my shame, I see
The imminent death of twenty thousand men
That, for a fantasy and trick of fame,
Go to their graves like beds - fight for a plot
Whereon the numbers cannot try the cause,
Which is not tomb enough and continent
To hide the slain? O, from this time forth
My thoughts be bloody, or be nothing worth!

- Hamlet, Act 4, Scene 4
```
