package org.example.Model;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.View.Observer;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModelFacade implements Observable {
    private Client client;
    private List<Observer> observers;

    public ModelFacade() {
        this.client = new Client();
        if (!client.tryConnect("localhost", 4004)) {
            log.info("Подключение не удалось...");
        }
        observers = new ArrayList<>();
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void setUp() {
        client.setModel(this);
    }

    @Override
    public void inform() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public boolean sendMessage(String line) {
        return client.sendMessage(line);
    }

    public List<String> getMessagesFromBuffer() {
        return client.getMessagesFromBuffer();
    }

}
