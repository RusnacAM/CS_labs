package tests.ClassicalCiphers;

import Implementations.Cipher;
import Implementations.ClassicalCiphers.Playfair;
import org.junit.Test;

public class PlayfairTest {
    private static final String key = "monarchy";
    private static final String message = "chocolates";

    Cipher cipher = new Playfair(key);

    @Test
    public void test() {
        String cipherText = cipher.encrypt(message);
        org.junit.Assert.assertEquals(message, cipher.decrypt(cipherText));
    }
}
