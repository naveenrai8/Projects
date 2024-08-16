package naveen.pubsub;

import naveen.pubsub.handler.TcpClient;

public class Main {
    public static void main(String[] args) {
        TcpClient tcpClient = TcpClient.getInstance("localhost", 8082);
        tcpClient.getOutputStream().println("Hello from client");
    }
}
