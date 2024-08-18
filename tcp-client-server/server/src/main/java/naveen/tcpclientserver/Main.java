package naveen.tcpclientserver;

import naveen.tcpclientserver.handler.TcpServer;

public class Main {
    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.getInstance(8082);
        tcpServer.start();
    }
}
