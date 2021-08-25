package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostgresWindow extends JPanel
{
    private Window window;
    private PostgresManager psql;

    private JTextField portField, userField;
    private JPasswordField passField;
    private JLabel errorLabel;

    public PostgresWindow(Window window)
    {
        this.window = window;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 2;

        this.add(new JLabel("UJ Hosting"), gbc);
        int ind = 1;
        gbc.gridy = ind++; this.add(new JLabel("connection port:"), gbc);
        portField = new JTextField(window.port);
        gbc.gridy = ind++; this.add(portField, gbc);
        gbc.gridy = ind++; this.add(new JLabel("user name:"), gbc);
        userField = new JTextField(window.username);
        gbc.gridy = ind++; this.add(userField, gbc);
        gbc.gridy = ind++; this.add(new JLabel("password:"), gbc);
        passField = new JPasswordField(window.pass);
        gbc.gridy = ind++; this.add(passField, gbc);
        JButton checkButton = new JButton("check connect");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Connect();
            }
        });
        gbc.gridy = ind++; gbc.gridwidth = 1; this.add(checkButton, gbc);
        JButton loginButton = new JButton("login service");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(Connect())
                window.MainMenu();
            }
        });
        gbc.gridx = 1; this.add(loginButton, gbc);
        gbc.gridx = 0; gbc.gridwidth = 2;
        errorLabel = new JLabel("");
        gbc.gridy = ind++; this.add(errorLabel, gbc);
        JButton quitButton = new JButton("quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.Quit();
            }
        });
        gbc.gridy = ind++; this.add(quitButton, gbc);

    }

    private boolean Connect()
    {
        window.port = portField.getText();
        window.pass = String.valueOf(passField.getPassword());
        window.username = userField.getText();
        psql = new PostgresManager(window);
        boolean res = psql.Connect();
        if(!res)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("unable to connect");
            return false;
        }
        errorLabel.setForeground(Color.green);
        errorLabel.setText("successfully connected|");
        return true;
    }
}
