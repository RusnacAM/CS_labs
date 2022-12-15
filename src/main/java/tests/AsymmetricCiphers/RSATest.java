package tests.AsymmetricCiphers;

import Implementations.AsymmetricCiphers.RSA;
import org.junit.Test;

import java.math.BigInteger;

public class RSATest {
    private static final String message = "Louisa May Alcott";
    private static final String messageTest = "louisamayalcott";

    private final RSA cipher = new RSA();

    @Test
    public void test() {
        BigInteger cipherText = cipher.encrypt(message);
        org.junit.Assert.assertEquals(messageTest, cipher.decrypt(cipherText));
    }
}
