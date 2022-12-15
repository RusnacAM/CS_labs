package Implementations.AsymmetricCiphers;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private static BigInteger primeNum1;
    private static BigInteger primeNum2;
    private static BigInteger modulus;
    private static BigInteger phi;
    private static BigInteger coprime;
    private static BigInteger multipInverse;

    // Conversion to BigInt from String
    private static BigInteger toInt(String message) {
        message = message.toUpperCase().replaceAll("\\s", "");
        String converted = "";
        int i = 0;
        while (i < message.length()) {
            int ch = message.charAt(i);
            converted = converted + ch;
            i++;
        }
        return new BigInteger(converted);
    }

    // Conversion from BigInt to String
    private static String toString(BigInteger message) {
        String cipherString = message.toString();
        String converted = "";
        int i = 0;
        while (i < cipherString.length()) {
            int temp = Integer.parseInt(cipherString.substring(i, i + 2));
            char ch = (char) temp;
            converted = converted + ch;
            i = i + 2;
        }
        return converted.toLowerCase();
    }

    // Function to get a random prime number of a certain number of bits
    private static BigInteger getPrime(int bits) {
        Random randomInteger = new Random();
        return BigInteger.probablePrime(bits, randomInteger);
    }

    // Function to get a coprime for phi, such that 1 < coprime < phi
    private static BigInteger getCoprime(BigInteger phi) {
        Random rand = new Random();
        BigInteger coprime;
        do {
            coprime = new BigInteger(1024, rand);
            while (coprime.min(phi).equals(phi)) {
                coprime = new BigInteger(1024, rand);
            }
        } while (!gcd(coprime, phi).equals(BigInteger.ONE));
        return coprime;
    }

    // Function for the greatest common divisor
    private static BigInteger gcd(BigInteger num1, BigInteger num2) {
        if (num2.equals(BigInteger.ZERO)) {
            return num1;
        } else {
            return gcd(num2, num1.mod(num2));
        }
    }

    // Function for the extended euclid algorithm for finding the multiplicative inverse
    private static BigInteger[] extendedEuclidAlg(BigInteger num1, BigInteger num2) {
        if (num2.equals(BigInteger.ZERO)) return new BigInteger[] {
                num1, BigInteger.ONE, BigInteger.ZERO
        };
        BigInteger[] vals = extendedEuclidAlg(num2, num1.mod(num2));
        BigInteger d = vals[0];
        BigInteger p = vals[2];
        BigInteger q = vals[1].subtract(num1.divide(num2).multiply(vals[2]));
        return new BigInteger[] {
                d, p, q
        };
    }

    // Function which contains all the RSA cipher algorithm steps
    private void performAlg() {
        primeNum1 = getPrime(512);
        primeNum2 = getPrime(512);
        modulus = primeNum1.multiply(primeNum2);
        phi = (primeNum1.subtract(BigInteger.ONE)).multiply(primeNum2.subtract(BigInteger.ONE));
        coprime = getCoprime(phi);
        multipInverse = extendedEuclidAlg(coprime, phi)[1];
    }

    public BigInteger encrypt(String message) {
        BigInteger plainText = toInt(message);
        performAlg();
        return plainText.modPow(coprime, modulus);
    }

    public String decrypt(BigInteger message) {
        BigInteger decrypted = message.modPow(multipInverse, modulus);
        return toString(decrypted);
    }
}
