package naveen.tcpclientserver.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {
    private Socket socket;

    private TcpClient() {
    }

    public static TcpClient getInstance(String host, int port) {
        TcpClient client = new TcpClient();
        try {
            client.socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    public BufferedReader getInputStream() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public PrintWriter getOutputStream() {
        try {
            return new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        if (this.socket != null && !this.socket.isClosed()) {
            try {
                this.socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
