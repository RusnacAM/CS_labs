package main.tests.ClassicalCiphers;

import main.Implementations.Cipher;
import main.Implementations.ClassicalCiphers.Caesar;
import main.Implementations.ClassicalCiphers.CaesarWithPermutation;
import org.junit.Test;
import static org.junit.Assert.*;

public class CaesarTest {
    private static final int keyCaesar = 5;
    private static final String messageCaesar = "hello";

    private static final int keyCaesarPerm = 5;
    private static final String keyCaesarPerm1 = "wafers";
    private static final String messageCaesarPerm = "attackatonce";

    Cipher cipher1 = new Caesar(keyCaesar);
    Cipher cipher2 = new CaesarWithPermutation(keyCaesarPerm, keyCaesarPerm1);

    @Test
    public void CaesarTest() {
        String cipherText = cipher1.encrypt(messageCaesar);
        assertEquals(messageCaesar, cipher1.decrypt(cipherText));
    }

    @Test
    public void CaesarPermutationTest() {
        String cipherText = cipher2.encrypt(messageCaesarPerm);
        assertEquals(messageCaesarPerm, cipher2.decrypt(cipherText));
    }

}
