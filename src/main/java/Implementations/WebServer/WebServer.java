package Implementations.WebServer;

import Implementations.Cipher;
import Implementations.ClassicalCiphers.Caesar;
import Implementations.ClassicalCiphers.CaesarWithPermutation;
import Implementations.ClassicalCiphers.Playfair;
import Implementations.ClassicalCiphers.Vigenere;
import Implementations.WebServer.Database.Database;
import Implementations.WebServer.TwoFactorAuth.Authentication;
import Implementations.WebServer.Utils.Utils;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebServer {

    private static final BasicAuthenticator basicAuthenticator = new cipherAuthentication("Lab3");
    private static Utils utils = new Utils();
    private static Database db = Database.getInstance();
    private static String SK = "PJCLO2CHEH4JE7MRMR73TRTNB52L7KG2";

    private static boolean loggedIn = false;

    public static void main(String[] args) throws Exception{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        List<String> list1 = Arrays.asList(new String[]{"caesar", "caesarPerm", "vigenere"});
        db.registerUser("amy", "12345", list1);
        List<String> list2 = Arrays.asList(new String[]{"playfair"});
        db.registerUser("user1", "password", list2);
        List<String> list3 = Arrays.asList(new String[]{"vigenere", "playfair"});
        db.registerUser("user2", "test", list3);

        HttpContext hc1 = server.createContext("/login", new Login());
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
            Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
            String twoFACode = params.get("2fa");
            if(twoFACode.equals(Authentication.getTOTPCode(SK))){
                loggedIn = true;
                String response = "You are now logged into your account.";
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

    public static class getCaesar implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
            String username = params.get("username");
            if(!loggedIn) {
                String response = "You are not logged in.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if (!db.checkAuthorization(username, "caesar")) {
                String response = "You are not authorized to access this service.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                Cipher cipher = new Caesar(13);
                String response = cipher.encrypt("CaesarService");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public static class getCaesarPerm implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
            String username = params.get("username");
            if(!loggedIn) {
                String response = "You are not logged in.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if (!db.checkAuthorization(username, "caesarPerm")) {
                String response = "You are not authorized to access this service.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                Cipher plainText = new CaesarWithPermutation(7, "testkey");
                String response = plainText.encrypt("CaesarPermService");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public static class getPlayfair implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
            String username = params.get("username");
            if(!loggedIn) {
                String response = "You are not logged in.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if (!db.checkAuthorization(username, "playfair")) {
                String response = "You are not authorized to access this service.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                Cipher plainText = new Playfair("monarchy");
                String response = plainText.encrypt("PlayfairService");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public static class getVigenere implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
            String username = params.get("username");
            if(!loggedIn) {
                String response = "You are not logged in.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if (!db.checkAuthorization(username, "vigenere")) {
                String response = "You are not authorized to access this service.";
                t.sendResponseHeaders(401, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                Cipher plainText = new Vigenere("SUPER");
                String response = plainText.encrypt("VigenreService");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    private static class cipherAuthentication extends BasicAuthenticator {

        public cipherAuthentication(String realm) {
            super(realm);
        }

        @Override
        public boolean checkCredentials(String username, String password){
            return db.checkPassword(username, password);
        }
    }
}