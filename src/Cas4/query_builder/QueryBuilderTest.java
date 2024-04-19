package Cas4.query_builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class QueryBuilderTest {
    public static void main(String[] args) {
        int jokeId = new Random().nextInt(500) + 1;
        String endPoint = "http://api.icndb.com/jokes/" + jokeId + "/";

        QueryBuilder qb = new QueryBuilder(endPoint);

        try(Scanner sc = new Scanner(System.in)){
            System.out.println("Enter your first name: ");
            qb.append("firstName",sc.nextLine());
            System.out.println("Enter your last name :");
            qb.append("lastName", sc.nextLine());
        }

        System.err.println("Sending GET request: ");
        System.err.println(qb);

        try{
            URL u = qb.toUrl();

            String json;

            try(BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            u.openStream())))
            {
                json = in.readLine();
            }

            //extraxt joke from json response
            int jokeFieldPos = json.indexOf("joke") + 8;
            String joke = json.substring(jokeFieldPos, json.indexOf('"', jokeFieldPos));



            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
