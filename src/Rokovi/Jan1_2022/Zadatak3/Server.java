package Rokovi.Jan1_2022.Zadatak3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {
    private static final int port = 12345;
    private Map<String, List<String>> cache;

    public Server(){
        this.cache = new HashMap<>();
        this.initCache();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startWorking();
    }

    private void startWorking(){
        try(DatagramSocket socket = new DatagramSocket(port)){
            //socket.setSoTimeout(5000);
            while (true){
                try {
                    byte[] buf = new byte[512];
                    DatagramPacket file_request = new DatagramPacket(buf, buf.length);
                    socket.receive(file_request);
                    String file_name = new String(buf,0,file_request.getLength(), StandardCharsets.UTF_8).trim();

                    byte[] buf_line = new byte[128];
                    DatagramPacket file_line = new DatagramPacket(buf_line, buf_line.length);
                    socket.receive(file_line);
                    String line_in_file = new String(buf_line, 0, file_line.getLength(), StandardCharsets.UTF_8).trim();


                    String response_value;
                    if(file_name.contains(".")){
                        int num_line = Integer.parseInt(line_in_file);
                        if(this.cache.containsKey(file_name) && this.cache.get(file_name).size() >= num_line){
                            response_value = this.cache.get(file_name).get(num_line);
                        }
                        else {
                            response_value = "That file or that line does not exists";
                        }
                    }
                    else{
                        int num_line = Integer.parseInt(file_name);
                        if(this.cache.containsKey(line_in_file) && this.cache.get(line_in_file).size() >= num_line){
                            response_value = this.cache.get(line_in_file).get(num_line);
                        }else {
                            response_value = "That file or that line does not exists";
                        }
                    }

                    byte[] resp = response_value.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket response = new DatagramPacket(resp,0,resp.length,
                            file_line.getAddress(),file_line.getPort());

                    socket.send(response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void initCache(){
        Path dir = Paths.get("/home/luka/Desktop/tests/");
        try(DirectoryStream<Path> streams = Files.newDirectoryStream(dir)){
            for(Path entry : streams){
                try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(entry.toString())))){
                    String line;
                    LinkedList<String> lines = new LinkedList<>();
                    while((line = in.readLine()) != null){
                        StringBuilder sb = new StringBuilder(line);
                        lines.add(sb.reverse().toString());
                    }
                    this.cache.put(entry.getFileName().toString(),lines);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
