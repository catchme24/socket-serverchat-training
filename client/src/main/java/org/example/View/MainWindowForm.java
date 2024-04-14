package org.example.View;

import org.example.Controller.ModelFacadeController;
import org.example.Model.ModelFacade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindowForm {
    private JTextArea textArea1;
    private JButton button1;
    private JTextField textField1;
    private JPanel panel;
    private ModelFacadeController controller;
    private ModelFacade model;

    MainWindowForm(ModelFacadeController controller, ModelFacade model) {
        this.controller = controller;
        this.model = model;
        createUIComponents();
    }

    private void createUIComponents() {
        button1.setText("Отправить");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!controller.trySendMessage(textField1.getText()))
                    JOptionPane.showMessageDialog(panel, "Не отправилось :(");
            }
        });

        textArea1.setEditable(false);
    }

    JPanel getContentPane() {
        return panel;
    }

    void update() {
        List<String> list = model.getMessagesFromBuffer();
        for (String line: list) {
            textArea1.append(line + "\n");
        }

    }
}
