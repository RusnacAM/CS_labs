package tests.ClassicalCiphers;

import Implementations.Cipher;
import Implementations.ClassicalCiphers.Caesar;
import Implementations.ClassicalCiphers.CaesarWithPermutation;
import org.junit.Test;

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
        org.junit.Assert.assertEquals(messageCaesar, cipher1.decrypt(cipherText));
    }

    @Test
    public void CaesarPermutationTest() {
        String cipherText = cipher2.encrypt(messageCaesarPerm);
        org.junit.Assert.assertEquals(messageCaesarPerm, cipher2.decrypt(cipherText));
    }
}
