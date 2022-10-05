package Lab1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Caesar's Cipher:");
        Cipher cipher1 = new Caesar(5);

        System.out.println("encryption: " + cipher1.encrypt("hello"));
        System.out.println("decryption: " + cipher1.decrypt(cipher1.encrypt("hello")) + "\n");

        System.out.println("Vignere Cipher:");
        Cipher cipher2 = new Vignere("SUPER");

        System.out.println("encryption: " + cipher2.encrypt("PER ASPERA AD ASTRA"));
        System.out.println("decryption: " + cipher2.decrypt(cipher2.encrypt("PER ASPERA AD ASTRA")) + "\n");

        System.out.println("Playfair Cipher:");
        Cipher cipher3 = new Playfair("monarchy");

        System.out.println("encryption: " + cipher3.encrypt("instruments"));
        System.out.println("decryption: " + cipher3.decrypt(cipher3.encrypt("instruments")) + "\n");

        System.out.println("Caesar's Cipher with Permutation:");
        Cipher cipher4 = new CaesarWithPermutation(7, "prejudice");

        System.out.println("encryption: " + cipher4.encrypt("instruments"));
        System.out.println("decryption: " + cipher4.decrypt(cipher4.encrypt("idiosyncrasy")));
    }
}
