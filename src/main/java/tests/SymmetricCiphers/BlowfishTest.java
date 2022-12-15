package tests.SymmetricCiphers;

import Implementations.Cipher;
import Implementations.SymmetricCiphers.Blowfish;
import org.junit.Test;

public class BlowfishTest {
    private static final String key = "aabb09182736ccdd";
    private static final String message = "123456abcd132536";
    private static final String encMessageTest = "d748ec383d3405f7";

    private final Cipher cipher = new Blowfish(key);

    @Test
    public void testCipherEncryption() {
        final String cipherText = cipher.encrypt(message);
        final boolean areEqual = encMessageTest.equalsIgnoreCase(cipherText);

        org.junit.Assert.assertTrue(areEqual);
    }

    @Test public void testCipherDecryption() {
        final String plainText = cipher.decrypt(encMessageTest);
        final boolean areEqual = message.equalsIgnoreCase(plainText);

        org.junit.Assert.assertTrue(areEqual);
    }
}
