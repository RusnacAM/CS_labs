# Symmetric Ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography and Security
### Author: Rusnac Ana-Maria

### Objectives

1. Get familiar with the symmetric cryptography, stream and block ciphers.
2. Implement an example of a stream cipher - RC4.
3. Implement an example of a block cipher - Blowfish.

### Stream Cipher - RC4
&nbsp; &nbsp;&nbsp;&nbsp;RC4(Rivest Cipher 4) also known as ARC4 or ARCFOUR is a stream cipher.
In a stream cipher each plaintext digit is encrypted one at a time with the
corresponding digit of the keystream, to give a digit of the ciphertext stream.
The pseudorandom keystream is typically generated serially from a 
random seed value using digital shift registers. The seed value 
serves as the cryptographic key for decrypting the ciphertext stream.

&nbsp; &nbsp;&nbsp;&nbsp;RC4 generates a pseudorandom stream of bits (a keystream). 
As with any stream cipher, these can be used for encryption by 
combining it with the plaintext using bitwise exclusive or; 
decryption is performed the same way.

The RC4 algorithm has 3 main steps:
1. Key Scheduling
2. Key Stream Generation
3. Data Encryption/Decryption

#### Key Scheduling

&nbsp; &nbsp;&nbsp;&nbsp;The first step is to prepare the plaintext message by converting it to bytes
with the convert function, as well as the key. For the actual key scheduling
the number of iterations will be the size of the state array - Sarray, in this
case 256. The array is initialized, and S is then processed for 256 
iterations, mixing in bytes of the key at the same time according to 
the formula:

    j := (j + S[i] + key[i mod keylength] mod 256)

Lastly, the two values of i and j are swapped:

    private int[] keyScheduling(int[] key) {
        // Initialize array
        int stateArr[] = new int[256];
        for(int i=0; i<256; i++) {
            stateArr[i] = i;
        }

        // key scheduling algorithm
        int j = 0;
        for(int i = 0; i < 256; i++) {
            j = (j + stateArr[i] + key[i % key.length]) % 256;
            swap(i, j, stateArr);
        }
        return stateArr;
    }

#### Key Stream Generation 

&nbsp; &nbsp;&nbsp;&nbsp;The key stream generation is also know as the pseudo-random generation
algorithm. For as many iterations as are needed, the PRGA modifies 
the state and outputs a byte of the keystream. In each iteration, 
the PRGA:

- increments i
- locks up the *i*th element of S, and adds that to j
- exchanges the values of i and j and the uses their sum mod 256 as an 
index to fetch a third element of S
- bitwise XOR with the next byte of the message to produce the next byte 
of either the ciphertext or plaintext.


    private int[] keyStreamGen (int[] text, int[] ksa) {
        int i = 0;
        int j = 0;
        int[] result = new int[text.length];

        for(int p = 0; p < result.length; p++) {
            i = (i + 1) % 256;
            j = (j + ksa[i]) % 256;
            swap(i, j, ksa);

            int k = ksa[(ksa[i] + ksa[j]) % 256];
            result[p] = k;
        }
        return result;
    }

#### Data Encryption 

&nbsp; &nbsp;&nbsp;&nbsp;The final ciphertext is obtained by performing the XOR operation
between the bytes of the message and the result from the key stream
generation:
    
     private int[] performAlg(int[] message) {
        int[] newKey = convert(key);
        int[] ksa = keyScheduling(newKey);
        int[] keyGen = keyStreamGen(message, ksa);
        int[] result = performXOR(message, keyGen);

        return result;
    }

#### Data Decryption 

&nbsp; &nbsp;&nbsp;&nbsp;Decryption is done exactly the same as encryption.

### RC4 Implementation

### Block Cipher - Blowfish
&nbsp; &nbsp;&nbsp;&nbsp;Blowfish is a symmetric-key block cipher.Blowfish provides a good 
encryption rate in software, and no effective cryptanalysis of it 
has been found to date.

&nbsp; &nbsp;&nbsp;&nbsp;A block cipher  is a deterministic algorithm operating on 
fixed-length groups of bits, called blocks.They are specified 
elementary components in the design of many cryptographic protocols 
and are widely used to encrypt large amounts of data, including in 
data exchange protocols. It uses blocks as an unvarying transformation.

### Blowfish Implementation

&nbsp; &nbsp;&nbsp;&nbsp;Blowfish has a 64-bit block size and a variable key length from 32 bits up to 448 bits.
There are 2 main parts/steps in a blowfish cipher:

1. Key Generation
2. Data Encryption/Decryption

#### Key Generation

&nbsp; &nbsp;&nbsp;&nbsp;The key in the blowfish cipher has a size from 32 bits to 448 bits.
For the key generation, there are a few values that need to be initialized:
the P-array, and S-boxes. Both of these are stored in Blowfish Constants.
The P-array consists of 18 "words", with a length of 32 bits, and is initialized
with the digits of Pi, hexadecimal values:

    public String P[] = { "243f6a88", "85a308d3", "13198a2e", "03707344", "a4093822",
            "299f31d0", "082efa98", "ec4e6c89", "452821e6", "38d01377",
            "be5466cf", "34e90c6c", "c0ac29b7", "c97c50dd", "3f84d5b5",
            "b5470917", "9216d5d9", "8979fb1b" };

&nbsp; &nbsp;&nbsp;&nbsp;The S-boxes, much like the P-array, are also initializes with the digits
of Pi. There are 4 total S-boxes, each being 256 bits.

&nbsp; &nbsp;&nbsp;&nbsp;Lastly, the key will be obtained by performing a XOR operation between
the P-array values and 32 bit parts of the key. Assuming a 32 bit key value is
k1:

    P1 = P1 XOR k1
    P2 = P2 XOR k2
    .
    .
    .
    P14 = P14 XOR k14
    P15 = P15 XOR k1
    .
    .
    .
    P18 = P18 XOR k4

&nbsp; &nbsp;&nbsp;&nbsp;Since the key size can only be as high as 448 bits, this means that we
will have around 14 keys, up to k14, but there are 18 P-array values, that
is why once the XOR operation gets to P15, the counting for the keys is
restarted. In this way 18 subkeys are generated. The same ones are used
for both encryption and decryption:

     public void keyGenerate(String key, BlowfishConstants constant) {
        int j = 0;
        for (int i = 0; i < constant.P.length; i++) {
            // xor-ing 32-bit parts of the key
            constant.P[i] = xor(constant.P[i], key.substring(j, j + 8));
            j = (j + 8) % key.length();
        }
    }

&nbsp; &nbsp;&nbsp;&nbsp;In the above code, it is demonstrated, for the length of the P-array, 
18 values in totals, there will be 18 XOR operations with 32 bits of the
key, and thus 18 subkeys formed. After the function is called, this is 
an example of the subkeys formed for encryption:

    Subkey0: 8e846390
    Subkey1: a295c40e
    Subkey2: b9a28336
    Subkey3: 2446bf99
    Subkey4: 0eb2313a
    Subkey5: 0ea9fd0d
    Subkey6: a295f380
    Subkey7: cb78a054
    Subkey8: ef9328fe
    Subkey9: 1fe6dfaa
    Subkey10: 14ef6fd7
    Subkey11: 13dfc0b1
    Subkey12: 6a1720af
    Subkey13: ee4a9c00
    Subkey14: 953fdcad
    Subkey15: 9271c5ca
    Subkey16: 38addcc1
    Subkey17: ae4f37c6

#### Data encryption

Data encryption can also be seen as having 2 steps:

1. Rounds
2. Post Processing

![](https://i.imgur.com/M5NyFQ7.jpg "Data Encryption Diagram")

&nbsp; &nbsp;&nbsp;&nbsp;The text is first split into two 32 bit halves(left half and right half).
Every round consists of 4 actions:
1. XOR the left half of the data with *r*th path entry(one of the 18 subkeys)
2. Use XORed data as input for the f function(explained below)
3. XOR the f function's output with the right half of the data
4. Swap the left and right sides

&nbsp; &nbsp;&nbsp;&nbsp;After 18 rounds of this, the 32 bit halves are restored to obtain either
the ciphertext or plaintext.

&nbsp; &nbsp;&nbsp;&nbsp;The ***f*** function the 32-bit input into 8-bit quarters as input to the 
S-boxes. The S-boxes accept 8-bit input and produce 32-bit output. 
The outputs are added modulo 2^32 and XORed to produce the final 32-bit output,
as seen in the image below:

![](https://media.geeksforgeeks.org/wp-content/uploads/20190929212325/F-blowfish.jpg "F function diagram")

In the code, after the subkeys are generated, this exact loop for the
rounds is called next:

    public String encrypt(String message) {
        BlowfishConstants constant = new BlowfishConstants();
        keyGenerate(key, constant);

        for (int i = 0; i < 16; i++)
            message = round(i, message, constant);
    ...

The round function is an implementation of the rounds described above:

    private String round(int time, String plainText, BlowfishConstants constant) {
        String left, right;
        left = plainText.substring(0, 8);
        right = plainText.substring(8, 16);

        left = xor(left, constant.P[time]);
        String fOut = f(left, constant);
        right = xor(fOut, right);
        String result = right + left;

        return result;
    }

&nbsp; &nbsp;&nbsp;&nbsp;Here, the message/plaintext is first separated into 2 halves. On the 
left half, the XOR operation with the subkey is executed in the xor function,
which is just an implementation of the xor operation. Then the return
value of this xor operation is used as input for the f function:
    
    private String f(String message, BlowfishConstants constant) {
        String a[] = new String[4];
        String ans = "";
        for (int i = 0; i < 8; i += 2) {
            long col = Long.parseUnsignedLong(hexToBin(message.substring(i, i + 2)), 2);
            a[i / 2] = constant.S[i / 2][(int)col];
        }
        ans = addBit(a[0], a[1]);
        ans = xor(ans, a[2]);
        ans = addBit(ans, a[3]);
        return ans;
    }

&nbsp; &nbsp;&nbsp;&nbsp;Inside the f function, the message is again split into quarters, which
are each passed to an S-box, and through the formula, produce a 32-bit output.
The addBit function adds the outputs, performs modulo 2^32, and produces
the next input for the xor:

    private String addBit(String a, String b) {
        long tempVal = 1;
        long modVal = getModVal(tempVal);
        String ans = "";
        long n1 = Long.parseUnsignedLong(a, 16);
        long n2 = Long.parseUnsignedLong(b, 16);
        n1 = (n1 + n2) % modVal;
        ans = Long.toHexString(n1);
        ans = "00000000" + ans;
        return ans.substring(ans.length() - 8);
    }

&nbsp; &nbsp;&nbsp;&nbsp;The getModVal is a function that simply get this 2^32 to perform the modulo
operation.

After the rounds are done, follows the final parte, the post processing.
Given the values from the rounds:

    round 0: 77b3ba639cb0353b
    round 1: 0cc7d63fd5267e6d
    round 2: c799728ab5655509
    round 3: 69612395e3dfcd13
    round 4: f3f5b74b67d312af
    round 5: 52023d4efd5c4a46
    round 6: 5b785180f097cece
    round 7: cc946d119000f1d4
    round 8: 6af47a4b230745ef
    round 9: 9fb82cc57512a5e1
    round 10: 1106c1ab8b574312
    round 11: 7d7a616502d9011a
    round 12: 81e9ce71176d41ca
    round 13: 9727e50a6fa35271
    round 14: eb761e34021839a7
    round 15: 0599d9367907dbfe

For encryption, the last 2 Xors are performed, and by uniting these last
two 32 bit values, the result, a 64 bit ciphertext is obtained:

    String right = message.substring(0, 8);
    String left = message.substring(8, 16);
    right = xor(right, constant.P[16]);
    left = xor(left, constant.P[17]);
    String result = left + right;

And the value of result, the cipher text:

    Cipher Text: d748ec383d3405f7

### Data decryption

&nbsp; &nbsp;&nbsp;&nbsp;The decryption for blowfish is exactly the same as encryption, it is
the reverse process. The only difference is the order in which rounds
and post processing take part. Decryption ocurrs in reverse order, so
it counts down from 18 values of subkeys, and the last two swaps and xors
are between P0 and P1 instead of P16 and P17:

    for (int i = 17; i > 1; i--)
        message = round(i, message, constant);

    String right = message.substring(0, 8);
    String left = message.substring(8, 16);
    right = xor(right, constant.P[1]);
    left = xor(left, constant.P[0]);
    String result = left + right;
    return result;

### Conclusion


Through the implementation of both types of symmetric key encryption, the 
specific differences between them became clearer, as well as the situations
in which one algorithm would be preferred over the other.