package tests.Hashing;

import org.junit.Test;

import java.security.KeyPair;

import static Implementations.Hashing.DigitalSignature.*;


public class DigitalSignatureTest {
    String message = "HashingTestMess";

    @Test
    public void test() throws Exception {
        KeyPair keyPair = RSAKeyPair();
        byte[] signature = DigitalSignature(message.getBytes(), keyPair.getPrivate());
        checkDigitalSignature(message.getBytes(), signature, keyPair.getPublic());
    }
}
