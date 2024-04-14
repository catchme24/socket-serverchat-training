package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ConnectedClient {

    private Socket connectedClient;
    private BufferedReader fromConnectedClient;
    private BufferedWriter toConnectedClient;
    private int id;

    public ConnectedClient(int id, Socket connectedClient, BufferedReader fromConnetedClient, BufferedWriter toConnectedClient) {
        this.id = id;
        this.connectedClient = connectedClient;
        this.fromConnectedClient = fromConnetedClient;
        this.toConnectedClient = toConnectedClient;
    }

    public String readMessage() {
        try {
            log.info("Считываю сообщения клиента");
            String line = this.fromConnectedClient.readLine();
            log.info("Считал сообщение клиента, возвращаю его");
            return line;
        } catch (IOException e) {
            log.error("Не удалось считать сообщение :(");
            throw new RuntimeException(e);
        }
    }

    public void writeMessage(String line) {
        try {
            log.info("Отправляю клиенту сообщение");
            this.toConnectedClient.write(line + "\n");
            this.toConnectedClient.flush();
            log.info("Отправил :)");
        } catch (IOException e) {
            log.error("Не удалось отправить сообщение :(");
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public boolean canRead() {
        try {
            return this.fromConnectedClient.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
