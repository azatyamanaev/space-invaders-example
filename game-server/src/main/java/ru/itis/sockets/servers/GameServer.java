package ru.itis.sockets.servers;

import ru.itis.protocol.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {
    private static List<ClientHandler> clients;
    private BufferedReader inServer;


    public GameServer() {
        clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static List<ClientHandler> getClients() {
        return clients;
    }
}
