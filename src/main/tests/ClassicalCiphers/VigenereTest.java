package main.tests.ClassicalCiphers;

import main.Implementations.Cipher;
import main.Implementations.ClassicalCiphers.Vigenere;
import org.junit.Test;

import static org.junit.Assert.*;

public class VigenereTest {
    private static final String key = "SUPER";
    private static final String message = "PER ASPERA AD ASTRA";
    private static final String encMessageTest = "PERASPERAADASTRA";

    Cipher cipher = new Vigenere(key);

    @Test
    public void test() {
        String cipherText = cipher.encrypt(message);
        assertEquals(encMessageTest, cipher.decrypt(cipherText));
    }
}
