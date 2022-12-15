package Implementations.ClassicalCiphers;


import Implementations.Cipher;

public class Caesar implements Cipher {
    private static int key;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Caesar(int key) {
        Caesar.key = key;
    }

    @Override
    public String encrypt(String message){
        message = message.toLowerCase();
        String encryptedMess = "";

        // for the message to be encrypted, simplu the given formula has been applied
        // in order to find the new letter
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

        // same process but the formula for decryption is applied
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
