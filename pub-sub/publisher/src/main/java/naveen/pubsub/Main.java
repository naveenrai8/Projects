package naveen.pubsub;

import naveen.pubsub.handler.TcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        TcpClient tcpClient = TcpClient.getInstance("localhost", 8082);
        PrintWriter outputStream = tcpClient.getOutputStream();
        BufferedReader inputStream = tcpClient.getInputStream();
        int i = 0;
        while (i++ < 10) {
            outputStream.println("Hello from client " + i);
            try {
                System.out.println(inputStream.readLine());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        outputStream.println("bye");
        tcpClient.closeSocket();
    }
}
