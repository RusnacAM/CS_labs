package main.Implementations.SymmetricCiphers;

public class RC4 {
    private static String key;

    public RC4(String key) {
        RC4.key = key;
    }

    // conversion functions from string to bytes, and bytes to string
    private static int[] convert(String s) {
        byte[] bytes = s.getBytes();
        int[] values = new int[bytes.length];

        for(int i = 0; i < bytes.length; i++) {
            values[i] = bytes[i];
        }
        return values;
    }

    private static String toString(int[] values) {
        byte[] bytes = new byte[values.length];

        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) values[i];
        }
        return new String(bytes);
    }

    // first step: key scheduling
    private static int[] keyScheduling(int[] key) {
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

    // second step key stream generation
    private static int[] keyStreamGen(int[] text, int[] ksa) {
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

    private static void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // XOR operation
    private static int[] performXOR(int[] message, int[] prga) {
        int[] result = new int[message.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = message[i] ^ prga[i];
        }
        return result;
    }

    // final step, either encryption or decryption
    private static int[] performAlg(int[] message) {
        int[] newKey = convert(key);
        int[] ksa = keyScheduling(newKey);
        int[] keyGen = keyStreamGen(message, ksa);
        int[] result = performXOR(message, keyGen);

        return result;
    }

    public int[] encrypt(String message) {
        int[] newMessage = convert(message);
        int[] encryptedMess = performAlg(newMessage);

        return encryptedMess;
    }

    public static String decrypt(int[] message) {
        int[] result = performAlg(message);
        String decryptedMess = toString(result);

        return decryptedMess;
    }
}
