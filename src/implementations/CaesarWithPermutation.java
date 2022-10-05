package implementations;

public class CaesarWithPermutation implements Cipher {
    private static int key;
    private static String strKey;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public CaesarWithPermutation(int key, String strKey) {
        this.key = key;
        this.strKey = strKey;
    }

    private String getAlphabet(){
        String tempAlphabet = "";
        tempAlphabet = strKey + ALPHABET;
        StringBuilder newAlphabet = new StringBuilder();
        tempAlphabet.chars().distinct().forEach(c -> newAlphabet.append((char) c));
        String alphabet = newAlphabet.toString();

        return alphabet;
    }

    @Override
    public String encrypt(String message) {
        String alphabet = getAlphabet();
        message = message.toLowerCase();
        String encryptedMess = "";

        for(int i = 0; i < message.length(); i++){
            int pos = alphabet.indexOf(message.charAt(i));
            pos = (key + pos) % 26;
            char c = alphabet.charAt(pos);

            encryptedMess += c;
        }
        return encryptedMess;
    }

    @Override
    public String decrypt(String message) {
        String alphabet = getAlphabet();
        message = message.toLowerCase();
        String decryptedMess = "";

        for(int i = 0; i < message.length(); i++){
            int pos = alphabet.indexOf(message.charAt(i));
            pos = (pos - key) % 26;
            if(pos < 0){
                pos = alphabet.length() + pos;
            }
            char c = alphabet.charAt(pos);
            decryptedMess += c;
        }
        return decryptedMess;
    }
}
