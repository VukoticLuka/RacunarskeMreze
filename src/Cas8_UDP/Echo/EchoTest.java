package Cas8_UDP.Echo;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class EchoTest {
    public static void main(String[] args) {
        try(EchoServer server = new EchoServer(12345);
            EchoClient client = new EchoClient("localhost", 12345))
        {
            server.start();

            String echo;
            echo = client.sendEcho("test1) hello!");
            System.out.println("(test1) returned: " + echo);
            echo = client.sendEcho("(test2) works?");
            System.out.println("(test2) returned: " + echo);
            echo = client.sendEcho("(test3) \uD83D\uDE0B");
            System.out.println("(test3) returned: " + echo);
            client.sendEcho("end");
        }catch (SocketTimeoutException e){
            e.printStackTrace();
        }
        catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
