package main.Implementations.Hashing;

import java.security.*;

public class DigitalSignature {
    public static KeyPair RSAKeyPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(
                2048,
                secureRandom
        );
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] DigitalSignature(byte[] message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message);
        return signature.sign();
    }

    public static boolean checkDigitalSignature(byte[] message, byte[] signatureToCheck, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(message);
        return signature.verify(signatureToCheck);
    }
}
