package main.Implementations.Hashing;

import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Database {
    private static Database instance = new Database();
    private Map<String, String> userData = new HashMap<>();

    public static Database getInstance() {
        return instance;
    }

    private Database() {}

    public boolean userExists(String username) {
        return userData.containsKey(username);
    }

    public void registerUser() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username:");
        String username = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();

        if(userExists(username)) {
            throw new Exception("Account already exists.");
        }

        StringBuffer hashedPassword = hashPassword(password);
        userData.put(username, hashedPassword.toString());
    }

    public String getUser(String username) throws Exception{
        if(userExists(username)) {
            return username + ": " + userData.get(username);
        }
        throw new Exception("There is no such user: " + username);
    }

    private static StringBuffer hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte[] digest = md.digest();
        //System.out.println("Password Digest: " + digest);

        StringBuffer hashedPassword = convertToHex(digest);
        //System.out.println("Hex format: " + hashedPassword.toString());

        return hashedPassword;
    }

    public static StringBuffer convertToHex(byte[] message) {
        StringBuffer hashedMessage = new StringBuffer();

        for(int i = 0; i < message.length; i++){
            hashedMessage.append(Integer.toHexString(0xFF & message[i]));
        }
        return hashedMessage;
    }
}
