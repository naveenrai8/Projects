package naveen.tcpclientserver.handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {

    private final int threadCount;
    private ServerSocket serverSocket;

    private TcpServer() {
        this.threadCount = Runtime.getRuntime().availableProcessors();
    }

    public static TcpServer getInstance(int port) {
        TcpServer tcpServer = new TcpServer();
        try {
            tcpServer.serverSocket = new ServerSocket(port);
            tcpServer.serverSocket.setReuseAddress(true);
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
        try (ExecutorService service = Executors.newFixedThreadPool(this.threadCount)) {
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = this.serverSocket.accept();
                    service.submit(ClientHandler.getInstance(clientSocket));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void closeClientSocket(Socket clientSocket) {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
