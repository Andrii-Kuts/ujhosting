package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayWindow extends JPanel
{
    private Window window;
    private int user;
    private PostgresManager psql;

    private JComboBox<Card> cardBox;
    private JTextField valueText;
    private JLabel errorLabel, balanceLabel;
    private double balanc;

    public PayWindow(Window window, int user)
    {
        this.window = window;
        this.user = user;
        psql = new PostgresManager(window);
        psql.Connect();

        JButton homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        JButton profileButton  = new JButton("profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(user);
            }
        });

        cardBox = new JComboBox(psql.GetUserCards(-1, user));
        JButton cardsButton = new JButton("edit cards");
        cardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenCardsList(user);
            }
        });
        valueText = new JTextField("0");
        JButton payButton = new JButton("pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Pay();
            }
        });
        balanc = psql.UserBalance(user);
        balanceLabel = new JLabel(((Double)balanc).toString() + "$");
        errorLabel = new JLabel("");

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 5; gbc.ipadx = 5;

        int ind = 0;
        gbc.gridy = ind++; this.add(homeButton, gbc);
        gbc.gridy = ind++; this.add(profileButton, gbc);
        gbc.gridy = ind++; this.add(new JLabel("your current balance"), gbc);
        gbc.gridy = ind++; this.add(balanceLabel, gbc);
        gbc.gridy = ind++; this.add(new JLabel("pick a card"), gbc);
        gbc.gridy = ind++; this.add(cardBox, gbc);
        gbc.gridy = ind++; this.add(cardsButton, gbc);
        gbc.gridy = ind++; this.add(new JLabel("value (in US dollars)"), gbc);
        gbc.gridy = ind++; this.add(valueText, gbc);
        gbc.gridy = ind++; this.add(payButton, gbc);
        gbc.gridy = ind++; this.add(errorLabel, gbc);
    }

    private void Pay()
    {
        Card crd = (Card)cardBox.getSelectedItem();
        if(crd == null)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("you don't have any cards");
            return;
        }
        Double value = 0.0;
        try
        {
            value = Double.parseDouble(valueText.getText());
        }
        catch (Exception e)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("the typed value is incorrect");
            return;
        }
        value = Math.round(value*1000.0)/1000.0;

        if(-value > balanc)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("not enough money on account");
            return;
        }
        boolean res = psql.PayUser(user, crd.card_id, value);
        if(!res)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("could\'nt make the payment");
            return;
        }
        errorLabel.setForeground(Color.green);
        errorLabel.setText("successfully payed");

        balanc = psql.UserBalance(user);
        balanceLabel.setText(((Double)balanc).toString() + "$");

    }
}
