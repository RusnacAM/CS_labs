import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    String password;
    String secretKey;
    List<String> authorizedCiphers;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.secretKey = "";
        this.authorizedCiphers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", authorizedCiphers=" + authorizedCiphers +
                '}';
    }
}
