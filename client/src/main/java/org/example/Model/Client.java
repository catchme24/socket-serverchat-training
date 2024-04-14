package org.example.Model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
class Client {

    private static final char END = '\n';
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private String hostName;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private int port = 0;
    private Socket socket;
    private BufferedReader fromServer;
    private BufferedWriter toServer;

    String myPattern = "dd.MM.yyyy HH:mm";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(myPattern);

    private Stack<String> buffer;
    @Setter(AccessLevel.PACKAGE)
    private ModelFacade model;

    Client() {
        buffer = new Stack<>();
    }

    boolean isConnected() {
        return socket != null ? socket.isConnected() : false;
    }

    boolean tryConnect() {
        if (isConnected())
            return false;
        if (hostName == null || port == 0) {
            log.info("Unspecified hostname or port!");
            return false;
        }
        return tryConnect(hostName, port);
    }


    boolean tryConnect(String hostName, int port) {
        if (isConnected())
            return false;
        if (hostName == null)
            log.info("Unspecified hostname!");
        this.hostName = hostName;
        this.port = port;
        try {
            socket = new Socket(hostName, port);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new MessageReader();
        } catch (IOException e) {
            log.error("Connection with " + hostName + "/" + port + " terminated :(");
            return false;
        }
        return true;
    }

    private class MessageReader extends Thread {

        private MessageReader() {
            start();
        }

        @Override
        public void run() {
            while (isConnected()) {
                try {
                    if (fromServer.ready()) {
                        while (fromServer.ready()) {
                            String line = fromServer.readLine();
                            buffer.push(line);
                            model.inform();
                        }
                    }
                } catch (IOException e) {
                    log.error("Cant read mess from server!");
                }
            }
        }

    }

    boolean sendMessage(String line) {
        try {
            toServer.write(LocalDateTime.now().format(formatter) + " - ");
            toServer.write(socket.getInetAddress().toString() + ":" + socket.getPort() + " - ");
            toServer.write(line + END);
            toServer.flush();
        } catch (IOException e) {
            log.error("Не отправляется на сервер");
            return false;
        }
        return true;
    }

    List<String> getMessagesFromBuffer() {
        List<String> messages = new ArrayList<>();
        while (!buffer.isEmpty()) {
            messages.add(buffer.pop());
        }
        return messages;
    }

}
