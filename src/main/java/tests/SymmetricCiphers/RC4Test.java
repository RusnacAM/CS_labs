package tests.SymmetricCiphers;

import Implementations.SymmetricCiphers.RC4;
import org.junit.Test;

public class RC4Test {
    private static final String key = "Alcott";
    private static final String message = "Little Women";

    @Test
    public void test() {
        RC4 cipher = new RC4(key);
        int[] cipherText = cipher.encrypt(message);
        org.junit.Assert.assertEquals(message, RC4.decrypt(cipherText));
    }
}
