package main.tests.ClassicalCiphers;

import main.Implementations.Cipher;
import main.Implementations.ClassicalCiphers.Playfair;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayfairTest {
    private static final String key = "monarchy";
    private static final String message = "chocolates";

    Cipher cipher = new Playfair(key);

    @Test
    public void test() {
        String cipherText = cipher.encrypt(message);
        assertEquals(message, cipher.decrypt(cipherText));
    }
}
