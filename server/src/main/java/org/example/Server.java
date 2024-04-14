package org.example;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.util.ArrayList;

@Slf4j
public class Server extends Thread {
    private int period = 100;
    private ServerSocket server;
    private final int PORT = 4004;
    private ConnectionManager cm;
    private MessageHandler mh;
    private ArrayList<ConnectedClient> connections;

    public Server() {
        this.connections = new ArrayList<>();
        this.cm = new ConnectionManager(this);
        this.mh = new MessageHandler(this);
        start();
    }

    @Override
    public void run() {
        this.cm.start();

        while (true) {
            try {
                log.info("Жду 10 сек..");
                Thread.sleep(period);
                log.info("Пытаюсь обновить месседжы");
                this.mh.sendNewMessages(this.mh.getNewMessages());
            } catch (InterruptedException e) {
                log.error("InterruptedException");
                throw new RuntimeException(e);
            }
        }
    }

    public void addConnection(ConnectedClient connection) {
        this.connections.add(connection);
    }

    public ArrayList<ConnectedClient> getConnections() {
        log.info("Зашел в получение коннекшенов");
        return connections;
    }
}
