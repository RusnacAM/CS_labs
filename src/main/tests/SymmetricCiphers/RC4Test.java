package main.tests.SymmetricCiphers;

import main.Implementations.SymmetricCiphers.RC4;
import org.junit.Test;
import static org.junit.Assert.*;

public class RC4Test {
    private static final String key = "Alcott";
    private static final String message = "Little Women";

    @Test
    public void test() {
        RC4 cipher = new RC4(key);
        int[] cipherText = cipher.encrypt(message);
        assertEquals(message, RC4.decrypt(cipherText));
    }
}
