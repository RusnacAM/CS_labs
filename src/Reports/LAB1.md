# Intro to Cryptography. Classical ciphers

### Course: Cryptography and Security
### Author: Rusnac Ana-Maria

### Objectives

1. Get familiar with the basics of cryptography
and classical ciphers
2. Implement 4 types of classical ciphers:
- Caesar cipher 
- Caesar cipher with permutation
- Vignere cipher
- Playfair cipher
3. Structure the project in the methods/classes/packages


### Caesar

&nbsp; &nbsp;For Caesar's cipher, each letter of the given text is replaced by a letter of some fixed number
of positions down the alphabet.\
&nbsp; &nbsp;A key, represented by an integer between 1 and 26 is
given, and this key will represent the shift of the 
alphabet. With this new alphabet in hand,each letter
in the message that will be encrypted is replaced with
its counterpart.\
&nbsp; &nbsp;The decryption process is exactly the same as encryption,
but in reverse. The encryption and decryption are represented
by the formulas: 
    
*em = enc<sub>k</sub>(x) = (x + k) mod 26*,\
*dm = dec<sub>k</sub>(x) = (x - n) mod 26*

### Caesar with Permutation

&nbsp; &nbsp;For Caesar main.Implementations.Cipher with permutation, it is very similar
to Caesar, almost the same, except there are 2 keys,
one is the shift of the alphabet, and the other is 
a string, the word based on which the permuation of 
the alphabet will happen in the first place.\
&nbsp; &nbsp;First, using the string key, the work is placed first
to create the new alphabet, each letter from the word 
is only written once even if it repeats itself in the word.\
The second step would then after the work to place the
alphabet like normally, except, again, no letter should
appear twice in the new alphabet.\
&nbsp; &nbsp;Once the new alphabet is made the encryption and decryption
goes exactly the same as for the usual Caesar cipher, just
with a new alphabet.

### Vignere 

&nbsp; &nbsp;The Vignere cipher is a simple form of a polyalphabetic substitution.
A polyalphabetic cipher is any cipher based on substitution, using multiple substitution alphabets.
The encryption is done using the Vignere square/table.
This table consists of the alphabets written 26 times in
different rows, each alphabet shifted cyclically to the 
left compared to the previous alphabet, corresponding to the 
26 possible Caesar ciphers. At different points of the 
encryption, the cipher uses a different alphabet, and
this alphabet is determined by a repeating keyword.\
&nbsp; &nbsp;The key in this case is a string, and this keyword is
repeated as many times as necessary until it matches the
length of the message we are trying to encrypt. After
which, one letter at a time, for example the first 
letter of the plaintext, is paired with the first letter
of the key, and this determined which alphabet will
be used, as well as the letter that will substitute
the plaintext for encryption. This process is repeated
as many times as necessary.
&nbsp; &nbsp; Decryption is again performed in the 
same exact manner, just in reverse.
These could also be represented by the following formulas:

*E<sub>i</sub> = (P<sub>i</sub> + k<sub>i</sub>) mod 26*\
*D<sub>i</sub> = (E<sub>i</sub> - k<sub>i</sub> + 26) mod 26*

### Playfair

&nbsp; &nbsp;The Playfair cipher is a substitution cipher, and a popular
symmetric encryption technique.\
&nbsp; &nbsp;It has a key represented by a string, and begins very
similar to the caesar cipher with permutation. Just in 
the same way the new alphabet was constructed there, by
placing the key first and then the alphabet, with no
duplicate letter, is the way the new alphabet will be
made for this cipher.\
&nbsp; &nbsp;This new alphabet is the placed in a 5x5 table, where
the letter i and j are equivalent, so j gets omitted.
When it comes to the text that will be encrypted, the
plaintext can't have the same exact letters placed
next to each other, like in the word "tree" for example.
In this case the two "e" letter get separated by an x
in the middle. Another condition is that the plaintext has
to have an even amount of letters, so if the amount of letters
is odd, at the end of the plaintext a "z" is placed.\
As for the actual encryption, it is done according
to the 5x5 table, the plaintext is take 2 letter at a time
and by their placement in the table, there are 3 possible
cases:
- the letters are not in the same column or row, in 
this instance they are encrypted as the letter with the
column position of the other letter. For example,if
two letter are 'b' and 'r', and b is in row 2 col 2, and
r is in row 4 col 4, then b is encrypted as the letter in
the table with the position of row 2 col 4, and r as the
letter in row 4 col 2.
- the letters are in the same row, in this case the letters
get encrypted as their corresponding 1 shift to the right.
- the letters are in the same col, the letters are encrypted
as the letter in the same row, 1 down.

&nbsp; &nbsp;For the decryption, the process is the exact same, 
the only difference is in the last three rules for
decryption:

-if the letters are not in the same row or col, it
is the same process as for encryption.\
-if the letters are in the same row, same as encryption
except they get shifted with 1 to the left.\
-if the letters are in the same col, same process
as encryption, but the letters are shifted up instead.

### Implementation

Implementation is described through comments in the
actual implementation of the ciphers.

### Conclusion

The ciphers were implemented successfully, and have 
been a good starting point into understanding the 
process of encryption, and decryption.