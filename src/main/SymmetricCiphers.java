package main;

import main.Implementations.SymmetricCiphers.Blowfish;
import main.Implementations.SymmetricCiphers.RC4;

public class SymmetricCiphers {
    public static void main(String[] args) {

        RC4 cipher1 = new RC4("Alcott");
        String message = "Little Women";

        System.out.println("Stream Cipher Encryption");
        System.out.println("\nEncrypted Message: ");
        System.out.println(cipher1.encrypt(message) + "\n");

        System.out.println("Decrypted Message:");
        System.out.println(cipher1.decrypt(cipher1.encrypt(message)));

        Cipher cipher2 = new Blowfish("aabb09182736ccdd");
        String plainText = "123456abcd132536";

        System.out.println("\n---------------------------");
        System.out.println("\nBlock Cipher Encryption");
        System.out.println("\nEncryption");
        String cipherText = cipher2.encrypt(plainText);
        System.out.println("CipherText: " + cipherText);

        System.out.println("\nDecryption");
        String finalMess = cipher2.decrypt("d748ec383d3405f7");
        System.out.println("PlainText: " + finalMess);

    }
}

// key aabb09182736ccdd
// text 123456abcd132536