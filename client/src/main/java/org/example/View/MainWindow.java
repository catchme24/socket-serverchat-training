package org.example.View;

import org.example.Controller.ModelFacadeController;
import org.example.Model.ModelFacade;

import javax.swing.*;

public class MainWindow implements Observer {
    private MainWindowForm form;

    public MainWindow(ModelFacade model, ModelFacadeController controller) {
        JFrame frame = new JFrame("Chat");
        this.form = new MainWindowForm(controller, model);
        frame.setContentPane(form.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update() {
        form.update();
    }
}
