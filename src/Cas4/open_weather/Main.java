package Cas4.open_weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;

public class Main {
    static final String endPoint = "https://api.openweathermap.org/data/2.5/weather";
    static final String[] params = {"lat", "lon", "units", "lang"};

    static final int INIT_CAPACITY = 256;
    public static void main(String[] args) {
        try {
            QueryBuilder qb = new QueryBuilder(params, endPoint);
            URLConnection conn = qb.toUrl().openConnection();

            // Note; You should not hardcode the API key in the code (we are doing it for simplicity's sake),
            // the best practice is to either load it from the configuration files or environment variables.
            // We could treat the API key like any other parameter, by adding "appid" to this.params array
            // Instead of that, we'll send the API key through the request headers:
            final String headerFieldKeyForAPIKey = "x-api-key";
            final String headerFieldForAPIKey = "<insert_api_key_here>";
            conn.setRequestProperty(headerFieldKeyForAPIKey, headerFieldForAPIKey);


            System.out.println();
            System.out.println("Sending GET request: " + qb);


            StringBuilder jsonString = new StringBuilder(INIT_CAPACITY);

            try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                String jsonLine;
                while ((jsonLine = in.readLine()) != null){
                    jsonString.append(jsonLine);
                }
            }

            System.out.println("Response: " + jsonString);
            System.out.println();

            System.out.println("Deserializing JSON response....");


            //TODO...

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
