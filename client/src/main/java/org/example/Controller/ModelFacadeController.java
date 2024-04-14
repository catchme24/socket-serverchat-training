package org.example.Controller;

import org.example.Model.Message;
import org.example.Model.ModelFacade;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelFacadeController {

    private ModelFacade model;

    private Set<String> bannedWords = new HashSet<>(List.of("войн", "демон"));

    public ModelFacadeController(ModelFacade model) {
        this.model = model;
    }

    public boolean trySendMessage(String message) {
        if (message == null || message.startsWith("***"))
            return false;
        String finalMessage = message;
        if (bannedWords.stream()
                .anyMatch(m -> finalMessage.toLowerCase().contains(m)))
            message = "что-то запрещенное";

        model.sendMessage(message);
        return true;
    }

}
