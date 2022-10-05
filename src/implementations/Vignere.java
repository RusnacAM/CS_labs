package implementations;

import java.util.Locale;

public class Vignere implements Cipher{
    private static String key;

    public Vignere(String key) {
        this.key = key;
    }

    @Override
    public String encrypt(String message) {
        String encryptedMess = "";
        message = message.toUpperCase();
        message = message.replace(" ", "");

        for(int i = 0, j = 0; i < message.length(); i++){
            char curr = message.charAt(i);
            int letter = (curr + key.charAt(j)) % 26;

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
