package Implementations.WebServer.Database;

import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    String password;
    List<String> authorizedCiphers;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.authorizedCiphers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Implementations.WebServer.Implementations.WebServer.Database.Database.User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorizedCiphers=" + authorizedCiphers +
                '}';
    }
}
