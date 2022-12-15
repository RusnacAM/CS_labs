package tests.ClassicalCiphers;

import Implementations.Cipher;
import Implementations.ClassicalCiphers.Vigenere;
import org.junit.Test;

public class VigenereTest {
    private static final String key = "SUPER";
    private static final String message = "PER ASPERA AD ASTRA";
    private static final String encMessageTest = "PERASPERAADASTRA";

    Cipher cipher = new Vigenere(key);

    @Test
    public void test() {
        String cipherText = cipher.encrypt(message);
        org.junit.Assert.assertEquals(encMessageTest, cipher.decrypt(cipherText));
    }
}
