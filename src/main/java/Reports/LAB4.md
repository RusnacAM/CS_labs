# Hash functions and Digital Signatures

### Course: Cryptography and Security
### Author: Rusnac Ana-Maria

### Objectives

1. Get familiar with the hashing techniques/algorithms.
2. Use an appropriate hashing algorithms to store passwords in a local DB.
3. Use an asymmetric cipher to implement a digital signature process for a user message.

### Hash algorithm - SHA-256

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;For both storing the passwords in the local in memory database, and
for the digital signature process, the SHA-256 hashing algorithm is 
used from the available java security library.</div>

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;In general, A hashing algorithm is a mathematical function 
that garbles data and makes it unreadable. Hashing algorithms 
are one-way programs, so the text can’t be unscrambled and 
decoded by anyone else. Hashing protects data at rest, so 
even if someone gains access to your server, the items stored 
there remain unreadable. Hashing can also help you prove that 
data isn’t adjusted or altered after the author is finished 
with it, and that's where the digital signature process comes in.</div>

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;SHA 256 is a part of the SHA 2 family of algorithms, the 
significance of the 256 in the name stands for the final 
hash digest value, i.e. irrespective of the size of 
plaintext/cleartext, the hash value will always be 256 bits.</div>

The SHA-256 algorithm has the following steps:
- padding bits
- padding length
- initializing the buffers
- compressing functions

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;First, it adds some extra bits to the message, 
such that the length is exactly 64 bits short of a 
multiple of 512. During the addition, the first bit 
should be one, and the rest of it should be filled with 
zeroes. Next, 8 different buffers need to be initialized, so 
they can be used in the hashing round, as well as an array
of 64 keys.</div>

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;Finally, the message is broken down into multiple blocks 
of 512 bits each. The compressing function puts each 
block through 64 rounds of operation, with the output of 
each block serving as the input for the following block.
The process is as follows:</div>

![](https://www.simplilearn.com/ice9/free_resources_article_thumb/functionsha-SHA_256_Algorithm.PNG "SHA-256 Hashing")

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;As a result, the digest, of 256 bits in length, is obtained.
Since this is a one-way process and it can't be reversed, hashing
algorithms are often used in the digital signature process.</div>

### Digital Signature Process

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;A digital signature process uses an advanced mathematical 
technique to check the authenticity and integrity of digital 
messages and documents. It guarantees that the contents of a 
message are not altered in transit and helps us overcome the 
problem of impersonation and tampering in digital 
communications.</div>

A digital signature works in the following steps:
1. The sender selects the file to be digitally signed in the document platform or application.
2. The sender’s computer calculates the unique hash value of the file content.
3. This hash value is encrypted with the sender’s private key to create the digital signature.
4. The original file along with its digital signature is sent to the receiver.
5. The receiver uses the associated document application, which identifies that the file has been digitally signed.
6. The receiver’s computer then decrypts the digital signature using the sender’s public key.

![](https://sectigo.com/uploads/images/Digital-Signature-Illustration.png "Digital Signature")


### Implementation

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The Implementations.WebServer.Database.Database is a simple in memory database, instantiated with
getInstance(), and the user data is store as a simple key-value pair.
The main method here is registerUser(), which is where all the hashing
work of the password is done as well. The user is prompted to enter
their data, after which the password goes through the whole hashing 
process before actually getting stored in the database:</div>

    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(password.getBytes());
    byte[] digest = md.digest();
    StringBuffer hashedPassword = convertToHex(digest);

The digest is under the form:

    Password Digest: [B@c4437c4
    Hex format: 5994471abb1112afcc18159f6cc74b4f511b9986da59b3caf5a9c173cacfc5

And the user data that gets store in the database:

    username: 5994471abb1112afcc18159f6cc74b4f511b9986da59b3caf5a9c173cacfc5

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;As for the digital signature process, everything related to it
is store in the DigitalSignature class. It has 3 methods, the
RSAKeyPair gets the public private key pair, according to the 
RSA algorithm. The digital signature method is the one that 
creates the signature:</div>

    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(message);

and the check method, verifies if the message, after hashing, matches the digital signature:

    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initVerify(publicKey);
    signature.update(message);

### Conclusions

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;This laboratory work explored hashing algorithms and their uses.
The main algorithm that was talked about is SHA-256 algorithm, 
how it works, why it is necessary, and how the SHA-256 algorithm
as a hashing algorithm can be used in the digital signature process.</div>
