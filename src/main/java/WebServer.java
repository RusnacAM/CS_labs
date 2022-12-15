import Implementations.Cipher;
import Implementations.ClassicalCiphers.Caesar;
import Implementations.ClassicalCiphers.CaesarWithPermutation;
import Implementations.ClassicalCiphers.Playfair;
import Implementations.ClassicalCiphers.Vigenere;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebServer {

    private static final BasicAuthenticator basicAuthenticator = new someAuthentication("test");
    private static Database db = Database.getInstance();
    private static String SK = "PJCLO2CHEH4JE7MRMR73TRTNB52L7KG2";

    public static void main(String[] args) throws Exception{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // PJCLO2CHEH4JE7MRMR73TRTNB52L7KG2
        db.registerUser("amy", "12345");
        // UGHV76YUEOFGSMYCDQ24K7D75Y7LG2U6
        db.registerUser("user1", "password");
        // EQ3AGSGPD64DOVFFT444QVMCK2W2PAN5
        db.registerUser("user2", "test");

        HttpContext hc1 = server.createContext("/", new Login());
        hc1.setAuthenticator(basicAuthenticator);
        HttpContext hc2 = server.createContext("/caesar", new getCaesar());
        hc2.setAuthenticator(basicAuthenticator);
        HttpContext hc3 = server.createContext("/caesarPerm", new getCaesarPerm());
        hc3.setAuthenticator(basicAuthenticator);
        HttpContext hc4 = server.createContext("/playfair", new getPlayfair());
        hc4.setAuthenticator(basicAuthenticator);
        HttpContext hc5 = server.createContext("/vigenere", new getVigenere());
        hc5.setAuthenticator(basicAuthenticator);

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server running on port 8080.");

    }
    public static class Login implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = WebServer.WebqueryToMap(t.getRequestURI().getQuery());
            String twoFACode = params.get("2fa");
            if(twoFACode.equals(Authentication.getTOTPCode(SK))){
                String response = "Logged in successfully";
                System.out.println("Logged in successfully");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "Invalid 2FA Code";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    private static Map<String, String> WebqueryToMap(String query) {
        Map<String, String> result = new java.util.HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static class getCaesar implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            //String response = "This the Caesar cipher service.";
            Cipher cipher = new Caesar(13);
            String response = cipher.encrypt("CaesarService");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static class getCaesarPerm implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            //String response = "This the Caesar cipher with permutation service.";
            Cipher plainText = new CaesarWithPermutation(7, "testkey");
            String response = plainText.encrypt("CaesarPermService");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static class getPlayfair implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            //String response = "This the Playfair cipher service.";
            Cipher plainText = new Playfair("monarchy");
            String response = plainText.encrypt("PlayfairService");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static class getVigenere implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            //String response = "This the Vigenere cipher service.";
            Cipher plainText = new Vigenere("SUPER");
            String response = plainText.encrypt("VigenreService");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class someAuthentication extends BasicAuthenticator {

        public someAuthentication(String realm) {
            super(realm);
        }

        @Override
        public boolean checkCredentials(String username, String password){
            return db.checkPassword(username, password);
        }
    }
}