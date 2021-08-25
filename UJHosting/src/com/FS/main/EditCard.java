package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCard extends JPanel
{
    private Window window;
    private int user, card;
    private PostgresManager psql;

    JTextField number;
    JTextField cvc;
    JTextField date;

    JLabel error;

    public EditCard(Window window, int user, int card, boolean add)
    {
        this.window = window;
        this.user = user;
        this.card = card;
        psql = new PostgresManager(window);
        psql.Connect();

        this.setLayout(new GridBagLayout());
        int ind = 0;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipady = 5; gbc.ipadx = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        gbc.gridy = ind++; this.add(homeButton, gbc);
        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenCardsList(user);
            }
        });
        gbc.gridy = ind++; this.add(backButton, gbc);

        if(!add) {
            Card crd = psql.GetCardInfo(card);
            number = new JTextField(crd.number);
            cvc = new JTextField(crd.cvc);
            date = new JTextField(crd.expirationDate);
        }
        else
        {
            number = new JTextField("card number");
            cvc = new JTextField("cvc/cvc2 code");
            date = new JTextField("expiration date");
        }
        gbc.gridy = ind++; this.add(number, gbc);
        gbc.gridy = ind++; this.add(cvc, gbc);
        gbc.gridy = ind++; this.add(date, gbc);
        if(!add) {
            JButton apply = new JButton("apply changes");
            apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Apply();
                }
            });
            gbc.gridy = ind++; this.add(apply, gbc);
            JButton remove = new JButton("remove this card");
            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Remove();
                }
            });
            gbc.gridy = ind++; this.add(remove, gbc);
        }
        else {
            JButton apply = new JButton("add card");
            apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Add();
                }
            });
            gbc.gridy = ind++; this.add(apply, gbc);
        }

        error = new JLabel("");
        gbc.gridy = ind++; this.add(error, gbc);
    }

    private void Apply()
    {
        boolean res = psql.UpdateCard(card, number.getText(), cvc.getText(), date.getText());
        if(!res)
        {
            error.setForeground(Color.red);
            error.setText("something wrong with data");
            return;
        }
        error.setForeground(Color.green);
        error.setText("successfully edited this card");
    }

    private void Remove()
    {
        boolean res = psql.DeleteCard(card);
        if(!res)
        {
            error.setForeground(Color.red);
            error.setText("couldn\'t remove this card");
            return;
        }
        window.OpenCardsList(user);
    }

    private void Add()
    {
        boolean res = psql.AddCard(number.getText(), cvc.getText(), date.getText(), user);
        if(!res)
        {
            error.setForeground(Color.red);
            error.setText("something wrong with data");
            return;
        }
        window.OpenCardsList(user);
    }
}
