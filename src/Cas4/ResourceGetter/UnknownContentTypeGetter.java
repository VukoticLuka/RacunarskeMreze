package Cas4.ResourceGetter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class UnknownContentTypeGetter {

    private static final String URL_STRING = "http://poincare.matf.bg.ac.rs/~marko.spasic/";
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL(URL_STRING);

        try {
            System.out.println("I got a " + url.getContent().getClass().getName());

            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;

            Object o = url.getContent(types);

            //We check the retur type

            if(o instanceof String){
                System.out.println("String");
                System.out.println(o);
            } else if (o instanceof Reader) {
                System.out.println("Reader");
                int c;
                Reader r = (Reader) o;

                while((c = r.read()) != -1){
                    System.out.println(c);
                }
                r.close();
            } else if (o instanceof InputStream) {
                System.out.println("Input stream");
                int c;
                InputStream in = (InputStream) o;
                while ((c = in.read()) != -1){
                    System.out.println(c);
                }

                in.close();
            }else {
                System.out.println("Error: unexpected type " + o.getClass());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
