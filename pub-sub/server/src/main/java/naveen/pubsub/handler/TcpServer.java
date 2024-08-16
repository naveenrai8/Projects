package naveen.pubsub.handler;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServer {

    private ServerSocket serverSocket;

    private TcpServer() {
    }

    public static TcpServer getInstance(int port) {
        TcpServer tcpServer = new TcpServer();
        try {
            tcpServer.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            if (!tcpServer.serverSocket.isClosed()) {
                try {
                    tcpServer.serverSocket.close();
                } catch (IOException ex) {
                    System.out.println("Unable to close server socket. " + ex.getMessage());
                }
            }
            System.out.println("Server is shutting down " + e.getMessage());
        }
        return tcpServer;
    }

    public void start() {
        try {
            this.serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        if (!this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                System.out.println("Unable to close server socket. " + ex.getMessage());
            }
        }
    }
}
