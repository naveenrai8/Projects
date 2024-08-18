package naveen.tcpclientserver.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    private ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static ClientHandler getInstance(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(clientSocket);
        clientHandler.reader = new BufferedReader(new InputStreamReader(clientHandler.clientSocket.getInputStream()));
        clientHandler.writer = new PrintWriter(clientHandler.clientSocket.getOutputStream(), true);
        return clientHandler;
    }

    @Override
    public void run() {
        System.out.println(this.clientSocket.getInetAddress());
        while (true) {
            try {
                String message = reader.readLine();
                if (message.equals("bye")) {
                    break;
                }
                //System.out.println(message);
                this.writer.println("From Server: " + message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        closeSocket();
    }

    private void closeSocket() {
        try {
            if (this.reader != null) {
                this.reader.close();
            }
            if (this.writer != null) {
                this.writer.close();
            }
            if (this.clientSocket != null && !this.clientSocket.isClosed()) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
