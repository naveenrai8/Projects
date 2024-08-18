package naveen.tcpclientserver;

import naveen.tcpclientserver.handler.TcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main implements Runnable {
    public static void main(String[] args) {
        try (ExecutorService service = Executors.newFixedThreadPool(1000)) {
            for (int i = 0; i < 1000; i++) {
                service.submit(new Main());
            }
        }
    }

    @Override
    public void run() {
        TcpClient tcpClient = TcpClient.getInstance("localhost", 8082);
        PrintWriter outputStream = tcpClient.getOutputStream();
        BufferedReader inputStream = tcpClient.getInputStream();
        int i = 0;
        while (i++ < 10000) {
            try {
                outputStream.println("Hello from client " + i + " -> " + Thread.currentThread().threadId());
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
