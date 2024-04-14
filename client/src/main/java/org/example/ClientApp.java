package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.Controller.ModelFacadeController;
import org.example.Model.ModelFacade;
import org.example.View.MainWindow;


import java.io.*;

@Slf4j
public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        ModelFacade model = new ModelFacade();
        model.setUp();
        ModelFacadeController controller = new ModelFacadeController(model);
        MainWindow mainWindow = new MainWindow(model, controller);
        model.addObserver(mainWindow);
    }
}
