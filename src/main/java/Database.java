import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database instance = new Database();
    private Map<String, User> userMap = new HashMap<>();

    public static Database getInstance() {
        return instance;
    }

    private Database() {}

    public boolean userExists(String username) {
        return userMap.containsKey(username);
    }

    public boolean checkPassword(String username, String password){
        if(!userExists(username)){
            System.out.println("Password or Username incorrect.");
            return false;
        }
        StringBuffer hashedPassword = new StringBuffer();
        try {
            hashedPassword = hashPassword(password);
        } catch (Exception e) {
            System.out.println("Password or Username incorrect.");
        }
        return userMap.get(username).password.equals(hashedPassword.toString());
    }

    // also assign new secret key every time a user is created, last step
    public void registerUser(String username, String password) throws Exception {
        if(userExists(username)) {
            throw new Exception("Account already exists.");
        }
        StringBuffer hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword.toString());
        userMap.put(username, user);
    }

    public User getUser(String username) throws Exception{
        if(userExists(username)) {
            return userMap.get(username);
        }
        throw new Exception("There is no such user: " + username);
    }

    private static StringBuffer hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return convertToHex(digest);
    }

    public static StringBuffer convertToHex(byte[] message) {
        StringBuffer hashedMessage = new StringBuffer();

        for (byte b : message) {
            hashedMessage.append(Integer.toHexString(0xFF & b));
        }
        return hashedMessage;
    }
}
