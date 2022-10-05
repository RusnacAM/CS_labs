package implementations;

import java.util.Locale;

public class Caesar implements Cipher{
    private static int key;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Caesar(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String message){
        message = message.toLowerCase();
        String encryptedMess = "";

        for(int i = 0; i < message.length(); i++){
            int pos = ALPHABET.indexOf(message.charAt(i));
            pos = (key + pos) % 26;
            char c = ALPHABET.charAt(pos);

            encryptedMess += c;
        }
        return encryptedMess;
    }

    @Override
    public String decrypt(String message){
        message = message.toLowerCase();
        String decryptedMess = "";

        for(int i = 0; i < message.length(); i++){
            int pos = ALPHABET.indexOf(message.charAt(i));
            pos = (pos - key) % 26;
            if(pos < 0){
                pos = ALPHABET.length() + pos;
            }
            char c = ALPHABET.charAt(pos);
            decryptedMess += c;
        }
        return decryptedMess;
    }


}
