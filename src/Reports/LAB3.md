# Asymmetric Ciphers

### Course: Cryptography and Security
### Author: Rusnac Ana-Maria

### Objectives

1. Get familiar with the asymmetric cryptography mechanisms.
2. Implement an example of an asymmetric cipher - RSA.

### Asymmetric Ciphers. RSA (Rivest-Shamir-Adleman) cipher.
<div style="text-align: justify">&nbsp; &nbsp;&nbsp;&nbsp;RSA (Rivest–Shamir–Adleman) is a public-key cryptosystem 
that is widely used for secure data transmission. It is also 
one of the oldest. The acronym "RSA" comes from the surnames of 
Ron Rivest, Adi Shamir and Leonard Adleman.</div>

<div style="text-align: justify">&nbsp; &nbsp;&nbsp;&nbsp;In a public-key cryptosystem, the encryption key is public and 
distinct from the decryption key, which is kept secret (private).
An RSA user creates and publishes a public key based on two large 
prime numbers, along with an auxiliary value. The prime numbers 
are kept secret. Messages can be encrypted by anyone, via the public 
key, but can only be decoded by someone who knows the prime numbers.
The security of RSA relies on the practical difficulty of 
factoring the product of two large prime numbers, the 
"factoring problem". There are no published methods to 
defeat the system if a large enough key is used.</div>

### Implementation
The RSA cipher has generally 5 main steps:

1. Select two large prime numbers: p and q.
2. Compute n = pq.
3. Compute the totient function phi(n) = (p-1)(q-1).
4. Select an integer e, such that 1 < e < phi(n); e and phi(n) are coprime.
5. Determine d such that d ≡ e<sup>-1</sup>(mod phi(n)).

#### Selecting Primes

&nbsp; &nbsp;&nbsp;&nbsp;The two primes p and q should be chosen at random, and be kept 
secret. This is done in the *getPrimes* function:

    Random randomInteger = new Random();
    return BigInteger.probablePrime(bits, randomInteger);

The *probablePrime()* method will return a BigInteger of bitLength
bits which is prime.

#### Compute N

<div style="text-align: justify">&nbsp; &nbsp;&nbsp;&nbsp;N is used as the modulus for both the public and private keys.
The pair of numbers (n, e) serves as the public key, and the pair 
of numbers (n, d) is the private key. With the two primes selected,
it is easy to compute n through multiplication.</div>

    modulus = primeNum1.multiply(primeNum2);

#### Compute Totient Function

The totient function is compute using the formula phi(n) = (p-1)(q-1):

     phi = (primeNum1.subtract(BigInteger.ONE)).multiply(primeNum2.subtract(BigInteger.ONE));

#### Select E

<div style="text-align: justify">&nbsp; &nbsp;&nbsp;&nbsp;E is the coprime of phi(n). Thus, E must be chosen such that 
1 < E < phi(n), and gcd(e, phi(n)) = 1. E is generated using the 
function *getCoprime*. It is chosen randomly, and for the checking
if E and phi are coprime, the gcd (greatest commom divisor) function is used.</div>

    Random rand = new Random();
        BigInteger coprime;
        do {
            coprime = new BigInteger(1024, rand);
            while (coprime.min(phi).equals(phi)) {
                coprime = new BigInteger(1024, rand);
            }
        } while (!gcd(coprime, phi).equals(BigInteger.ONE));
        return coprime;

The gcd function is a recursive implementation of the Euclidian 
algorithm for finding the greatest common divisor:

     if (num2.equals(BigInteger.ZERO)) {
            return num1;
        } else {
            return gcd(num2, num1.mod(num2));
        }

#### Determine D

&nbsp; &nbsp;&nbsp;&nbsp;D is also know as the multiplicative inverse, D need to be determined
such that d ≡ e<sup>-1</sup>(mod phi(n)). D can be computed using
Euclid's extended algorithm. Since e and phi(n) are coprime, the 
equation is a form of Bézout's identity.

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

#### Encryption

&nbsp; &nbsp;&nbsp;&nbsp;All the necessary steps are store int he *performAlg* function.
The result is as follows:

    First Prime Num: 10544456362700763964459427178782631828...
    Second Prime Num: 7433729557729706119876447050733696464...
    N - the modulus: 78384636933599735767715429857725561002...
    Phi: 78384636933599735767715429857725561002266527858541...
    E - the coprime: 76834144260420854530778679874245293110...
    D - the multiplicative inverse: -1516202737286104936297...

After which, as described, the encryption is done using a public
key. This public key is represented by the pair of numbers (n, e).
The respective formula for encryption is *C = P<sup>e</sup>mod n*:

     BigInteger plainText = toInt(message);
        performAlg();
        return plainText.modPow(coprime, modulus);

#### Decryption

&nbsp; &nbsp;&nbsp;&nbsp;The decryption is done using the private key, which is represented
by the pair of numbers (n, d), and the formula *P = C<sup>d</sup>mod n*:

    BigInteger decrypted = message.modPow(multipInverse, modulus);
    return toString(decrypted);

### Conclusion

&nbsp; &nbsp;&nbsp;&nbsp; During this laboratory work, the RSA cipher
was analyzed. Since the RSA cipher is an asymmetric key, it was also
a good exercise in what asymmetric key cryptography is, and how a
public and private keys are both generated and used. 