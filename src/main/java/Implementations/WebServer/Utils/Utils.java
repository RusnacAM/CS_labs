package Implementations.WebServer.Utils;

import java.util.Map;

public class Utils {
    public static Map<String, String> WebqueryToMap(String query) {
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
}
