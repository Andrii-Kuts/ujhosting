package com.FS.main;

import javax.security.auth.login.AppConfigurationEntry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellServerWindow extends JPanel
{
    private Window window;
    private int user, server;
    private PostgresManager psql;

    private JTextField valueField, nameField;
    private JLabel errorLabel;

    public SellServerWindow(Window window, int user, int server)
    {
        this.window = window;
        this.user = user;
        this.server = server;
        psql = new PostgresManager(window);
        psql.Connect();

        JButton homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenServer(server);
            }
        });
        JPanel serverPanel = psql.GetServerInfo(server).getSimplePanel(window);
        valueField = new JTextField("enter price in US dollars");
        nameField = new JTextField("enter a name for the offer");
        JButton sellButton = new JButton("sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Sell();
            }
        });
        errorLabel = new JLabel("");

        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        int ind = 0;

        gbc.gridy = ind++; panel.add(homeButton, gbc);
        gbc.gridy = ind++; panel.add(backButton, gbc);
        gbc.gridy = ind++; panel.add(serverPanel, gbc);
        gbc.gridy = ind++; panel.add(valueField, gbc);
        gbc.gridy = ind++; panel.add(nameField, gbc);
        gbc.gridy = ind++; panel.add(sellButton, gbc);
        gbc.gridy = ind++; panel.add(errorLabel, gbc);

        this.add(panel);
    }

    private void Sell()
    {
        Double value = 0.0;
        try
        {
            value = Double.parseDouble(valueField.getText());
        } catch (Exception e)
        {
            e.printStackTrace();
            errorLabel.setForeground(Color.red);
            errorLabel.setText("the price you typed is incorrect");
            return;
        }
        if(value <= 0.0)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("the price you typed is incorrect");
            return;
        }
        boolean res = psql.SellServer(user, server, value, nameField.getText());
        if(!res)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("something went wrong");
            return;
        }
        errorLabel.setForeground(Color.green);
        errorLabel.setText("server was successfully put out on the market");
    }
}
