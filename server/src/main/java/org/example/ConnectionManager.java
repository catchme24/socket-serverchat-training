package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Slf4j
public class ConnectionManager extends Thread {
    private ServerSocket serverSocket;

    private Server server;
    private final int PORT = 4004;
    private int counter = 1;

    public ConnectionManager(Server server) {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket connectedClient = serverSocket.accept();
                this.server.addConnection(new ConnectedClient(this.counter, connectedClient,
                                                new BufferedReader(new InputStreamReader(connectedClient.getInputStream())),
                                                new BufferedWriter(new OutputStreamWriter(connectedClient.getOutputStream()))));

                this.counter++;
                log.info("Accepted connection with " + new StringBuilder(connectedClient.getLocalAddress().toString())
                        .append(":")
                        .append(connectedClient.getPort()).toString());
            }

        } catch (IOException e) {
            log.error("Cannot accept client :(");
            log.error(e.getMessage());
        }
    }


}
