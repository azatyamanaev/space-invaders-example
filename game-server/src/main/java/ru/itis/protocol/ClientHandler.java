package ru.itis.protocol;

import ru.itis.game.SpaceInvaders;
import ru.itis.sockets.servers.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
        System.out.println("New client");
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("Exit")) {
                    break;
                } else if (inputLine.equals("Start")) {
                    SpaceInvaders.main();
                }
            }
            this.stopConnection();
            in.close();
            System.out.println("Client exited");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void stopConnection() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
            GameServer.getClients().remove(this);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
