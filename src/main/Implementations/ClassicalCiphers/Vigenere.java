package main.Implementations.ClassicalCiphers;
import main.Cipher;

public class Vigenere implements Cipher {
    private static String key;

    public Vigenere(String key) {
        Vigenere.key = key;
    }

    @Override
    public String encrypt(String message) {
        String encryptedMess = "";
        message = message.toUpperCase();
        message = message.replace(" ", "");

        //respective formula for encryption is applied
        for(int i = 0, j = 0; i < message.length(); i++){
            char curr = message.charAt(i);
            int letter = (curr + key.charAt(j)) % 26;
            // convert to ASCII
            letter += 'A';
            encryptedMess += (char)(letter);
            j = ++j % key.length();

        }
        return encryptedMess;
    }

    @Override
    public String decrypt(String message) {
        String decryptedMess = "";
        message = message.toUpperCase();
        message = message.replace(" ", "");

        //respective formula for decryption is applied
        for(int i = 0, j = 0; i < message.length(); i++){
            char curr = message.charAt(i);
            int letter = (curr - key.charAt(j) + 26) % 26;
            letter += 'A';
            decryptedMess += (char)(letter);
            j = ++j % key.length();
        }

        return decryptedMess;
    }
}
