package ru.itis.sockets.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader inClient;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            SocketClient client = this;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            inClient = new BufferedReader(new InputStreamReader(System.in));
            new Thread(receiverMessagesTask).start();
            new Thread(senderMessagesTask).start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Runnable senderMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String line = inClient.readLine();
                    String[] parameters = line.split(";");
                    if (parameters.length == 1) {
                        if (parameters[0].equals("Exit")) {
                            out.println("Exit");
                            System.exit(0);
                        } else if (parameters[0].equals("Start")) {
                            out.println("Start");
                        }
                    } else if (parameters.length >= 1 && parameters.length <= 4) {
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };
    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String response = in.readLine();
                    if (response != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

}
