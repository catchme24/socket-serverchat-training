package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageHandler {
    private ArrayList<String> history;

    private Server server;

    public MessageHandler(Server server) {
        this.server = server;
        this.history = new ArrayList<>();
    }


    public List<String> getNewMessages() {
        List<String> list = new ArrayList<>();
        for (ConnectedClient connectedClient: this.server.getConnections()) {
            log.info("Получаю от клиента " + connectedClient.getId());
            if (connectedClient.canRead()) {
                while (connectedClient.canRead()) {
                    String line = connectedClient.readMessage();
                    list.add(line);
                }
                log.info("Получил от клиента " + connectedClient.getId());
            } else {
                log.info("Пока нечео получать");
            }
        }
        this.history.addAll(list);
        log.info("Получил все сообщения");
        return list;
    }

    public void sendNewMessages(List<String> list) {
        log.info("Длинна list для отправки месседжей: " + list.size());
        for (ConnectedClient connectedClient: this.server.getConnections()) {
            for (String line: list) {
                log.info("Пишу клиенту " + connectedClient.getId());
                connectedClient.writeMessage(line);
                log.info("Написал клиенту " + connectedClient.getId());
            }
        }
        log.info("Отправил все сообщения!");
    }
}
