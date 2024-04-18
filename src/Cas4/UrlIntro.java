package Cas4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class UrlIntro {
    public static void main(String[] args) {
        URL u;

        try {
            u = new URL("http://matf.bg.ac.rs:8080");

            System.out.println("Host part of the url " + u.getHost());
            System.out.println("Protocol part is: " + u.getProtocol());
            System.out.println("Port is: " + u.getPort());

            URL u1 = new URL("http://www.matf.bg.ac.rs/~ivan_ristovic/index.html");

            System.out.println("Default port for this protocol is: " + u1.getDefaultPort());
            System.out.println("Path part of this url is: " + u1.getPath());
            System.out.println("Authority part of URL is: "+u1.getAuthority());


            u = new URL("http://poincare.matf.bg.ac.rs/~ivan_ristovic/");

            URLConnection con = u.openConnection();
            System.out.println(con.getContentType());
            System.out.println(con.getContentEncoding());
            System.out.println("Date: " + new Date(con.getDate()));
            System.out.println("Last Modified: " + new Date(con.getLastModified()));
            System.out.println("URL: " + con.getURL());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
