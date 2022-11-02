package main.Implementations.SymmetricCiphers;
import main.Implementations.Cipher;

public class Blowfish implements Cipher {
    private static String key;

    public Blowfish(String key) {
        Blowfish.key = key;
    }

    // storing 2^32 in modVal
    private long getModVal(long modVal){
        for (int i = 0; i < 32; i++)
            modVal = modVal << 1;
        return modVal;
    }

    // First Step. Key Generation
    public void keyGenerate(String key, BlowfishConstants constant) {
        int j = 0;
        for (int i = 0; i < constant.P.length; i++) {
            constant.P[i] = xor(constant.P[i], key.substring(j, j + 8));
            j = (j + 8) % key.length();
            System.out.println("Subkey" + i +": " + constant.P[i]);
        }
    }

    // to convert hexadecimal to binary.
    private String hexToBin(String message) {
        String binary = "";
        Long num;
        String binary4B;
        int n = message.length();
        for (int i = 0; i < n; i++) {
            num = Long.parseUnsignedLong(
                    message.charAt(i) + "", 16);
            binary4B = Long.toBinaryString(num);
            binary4B = "0000" + binary4B;

            binary4B = binary4B.substring(binary4B.length() - 4);
            binary += binary4B;
        }
        return binary;
    }

    // convert from binary to hexadecimal.
    private String binToHex(String message) {
        long num = Long.parseUnsignedLong(message, 2);
        String hexa = Long.toHexString(num);
        while (hexa.length() < (message.length() / 4))
            hexa = "0" + hexa;
        return hexa;
    }

    // XOR operation for 2 hexadecimals
    private String xor(String a, String b) {
        a = hexToBin(a);
        b = hexToBin(b);
        String ans = "";
        for (int i = 0; i < a.length(); i++)
            ans += (char)(((a.charAt(i) - '0') ^ (b.charAt(i) - '0')) + '0');
        ans = binToHex(ans);
        return ans;
    }

    // addition modulo 2^32 of two hexadecimal strings.
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

    // round function
    private String round(int time, String message, BlowfishConstants constant) {
        String left, right;
        left = message.substring(0, 8);
        right = message.substring(8, 16);

        left = xor(left, constant.P[time]);
        String fOut = f(left, constant);
        right = xor(fOut, right);
        String result = right + left;

        System.out.println("round " + time + ": " + result);
        return result;
    }

    @Override
    public String encrypt(String message) {
        BlowfishConstants constant = new BlowfishConstants();
        keyGenerate(key, constant);

        for (int i = 0; i < 16; i++)
            message = round(i, message, constant);

        String right = message.substring(0, 8);
        String left = message.substring(8, 16);
        right = xor(right, constant.P[16]);
        left = xor(left, constant.P[17]);
        String result = left + right;
        return result;
    }

    @Override
    public String decrypt(String message) {
        BlowfishConstants constant = new BlowfishConstants();
        keyGenerate(key, constant);

        for (int i = 17; i > 1; i--)
            message = round(i, message, constant);

        String right = message.substring(0, 8);
        String left = message.substring(8, 16);
        right = xor(right, constant.P[1]);
        left = xor(left, constant.P[0]);
        String result = left + right;
        return result;
    }
}