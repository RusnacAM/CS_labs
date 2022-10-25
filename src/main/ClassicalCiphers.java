package main;

import main.Implementations.ClassicalCiphers.Caesar;
import main.Implementations.ClassicalCiphers.CaesarWithPermutation;
import main.Implementations.ClassicalCiphers.Playfair;
import main.Implementations.ClassicalCiphers.Vigenere;

public class ClassicalCiphers {
    public static void main(String[] args) {
        System.out.println("Caesar's main.Cipher:");
        Cipher cipher1 = new Caesar(5);

        System.out.println("encryption: " + cipher1.encrypt("hello"));
        System.out.println("decryption: " + cipher1.decrypt(cipher1.encrypt("hello")) + "\n");

        System.out.println("Vigenere main.Cipher:");
        Cipher cipher2 = new Vigenere("SUPER");

        System.out.println("encryption: " + cipher2.encrypt("PER ASPERA AD ASTRA"));
        System.out.println("decryption: " + cipher2.decrypt(cipher2.encrypt("PER ASPERA AD ASTRA")) + "\n");

        System.out.println("Playfair main.Cipher:");
        Cipher cipher3 = new Playfair("monarchy");

        System.out.println("encryption: " + cipher3.encrypt("instruments"));
        System.out.println("decryption: " + cipher3.decrypt(cipher3.encrypt("instruments")) + "\n");

        System.out.println("Caesar's main.Cipher with Permutation:");
        Cipher cipher4 = new CaesarWithPermutation(7, "prejudice");

        System.out.println("encryption: " + cipher4.encrypt("instruments"));
        System.out.println("decryption: " + cipher4.decrypt(cipher4.encrypt("idiosyncrasy")));
    }
}
