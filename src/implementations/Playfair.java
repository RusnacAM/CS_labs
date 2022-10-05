package implementations;

public class Playfair implements Cipher {
    private static String key;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Playfair(String key) {
        this.key = key;
    }

    private String getAlphabet(){
        String tempAlphabet = ALPHABET.replace("j", "");
        tempAlphabet = key + tempAlphabet;
        StringBuilder newAlphabet = new StringBuilder();
        tempAlphabet.chars().distinct().forEach(c -> newAlphabet.append((char) c));
        String alphabet = newAlphabet.toString();

        return alphabet;
    }

    private String checkMessage(String message){
        message = message.replace(" ", "");
        for(int i = 0; i < message.length()-1; i++){
            int j = i+1;
            if(message.charAt(i) == message.charAt(j)){
                message = message.substring(0, j) + 'x' + message.substring(j);
            }
        }

        if(message.length() % 2 != 0){
            message += 'z';
        }

        return message;
    }

    private char[][] makeTable(String alphabet){
        char[][] table = new char[5][5];

        int counter = 0;
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table.length; j++){
                char curr = alphabet.charAt(counter);
                table[i][j] = curr;
                counter++;
            }
        }

        return table;
    }


    @Override
    public String encrypt(String message) {
        String alphabet = getAlphabet();
        message = checkMessage(message);
        char[][] table = makeTable(alphabet);


        String encryptedMess = "";

        for(int i = 0; i < message.length(); i += 2){
            char first = message.charAt(i);
            char second = message.charAt(i+1);
            int firstRow = 0;
            int firstCol = 0;
            int secondRow = 0;
            int secondCol = 0;

            for(int j = 0; j < table.length; j++){
                for(int k = 0; k < table.length; k++){
                    if(table[j][k] == first){
                        firstRow = j;
                        firstCol = k;
                    } else if(table[j][k] == second){
                        secondRow = j;
                        secondCol = k;
                    }
                }
            }

            if(firstRow == secondRow){
                encryptedMess += table[firstRow][(firstCol + 1) % 5];
                encryptedMess += table[secondRow][(secondCol + 1) % 5];
            } else if(firstCol == secondCol){
                encryptedMess += table[(firstRow + 1) % 5][firstCol];
                encryptedMess += table[(secondRow + 1) % 5][secondCol];
            } else {
                encryptedMess += table[firstRow][secondCol];
                encryptedMess += table[secondRow][firstCol];
            }
        }

        return encryptedMess;
    }

    @Override
    public String decrypt(String message) {
        String alphabet = getAlphabet();
        message = checkMessage(message);
        char[][] table = makeTable(alphabet);

        String decryptedMess = "";

        for(int i = 0; i < message.length(); i += 2){
            char first = message.charAt(i);
            char second = message.charAt(i+1);
            int firstRow = 0;
            int firstCol = 0;
            int secondRow = 0;
            int secondCol = 0;

            for(int j = 0; j < table.length; j++){
                for(int k = 0; k < table.length; k++){
                    if(table[j][k] == first){
                        firstRow = j;
                        firstCol = k;
                    } else if(table[j][k] == second){
                        secondRow = j;
                        secondCol = k;
                    }
                }
            }

            if(firstRow == secondRow){
                if(firstCol == 0){
                    firstCol = 5;
                } else if(secondCol == 0){
                    secondCol = 5;
                }
                decryptedMess += table[firstRow][(firstCol - 1) % 5];
                decryptedMess += table[secondRow][(secondCol - 1) % 5];
            } else if(firstCol == secondCol){
                if(firstRow == 0){
                    firstRow = 5;
                } else if(secondRow == 0){
                    secondRow = 5;
                }
                decryptedMess += table[(firstRow - 1) % 5][firstCol];
                decryptedMess += table[(secondRow - 1) % 5][secondCol];
            } else {
                decryptedMess += table[firstRow][secondCol];
                decryptedMess += table[secondRow][firstCol];
            }
        }
        return decryptedMess;
    }
}
