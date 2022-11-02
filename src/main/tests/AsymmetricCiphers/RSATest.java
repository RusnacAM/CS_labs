package main.tests.AsymmetricCiphers;

import main.Implementations.AsymmetricCiphers.RSA;
import org.junit.Test;
import java.math.BigInteger;
import static org.junit.Assert.*;

public class RSATest {
    private static final String message = "Louisa May Alcott";
    private static final String messageTest = "louisamayalcott";

    private final RSA cipher = new RSA();

    @Test
    public void test() {
        BigInteger cipherText = cipher.encrypt(message);
        assertEquals(messageTest, cipher.decrypt(cipherText));
    }
}

