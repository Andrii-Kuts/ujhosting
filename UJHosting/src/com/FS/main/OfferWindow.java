package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfferWindow extends JPanel
{
    private Window window;
    private int user, offer;
    private PostgresManager psql;

    public OfferWindow(Window window, int user, int offer)
    {
        this.window = window;
        this.user = user;
        this.offer = offer;
        psql = new PostgresManager(window);
        psql.Connect();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ind = 0;

        JButton homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        gbc.gridy = ind++; this.add(homeButton, gbc);


        Object[] data = psql.GetOfferInfo(offer);
        if(data.length == 12)
        {
            JLabel l1 = new JLabel("offer id: " + data[0]); gbc.gridy = ind++; this.add(l1, gbc);
            JLabel l2 = new JLabel("name: " + data[1]); gbc.gridy = ind++; this.add(l2, gbc);
            JLabel l3 = new JLabel("starting price: " + data[2] + "$"); gbc.gridy = ind++; this.add(l3, gbc);
            JLabel l4 = new JLabel("available since: " + data[3]); gbc.gridy = ind++; this.add(l4, gbc);
            if(data[4] != null) {
                JLabel l5 = new JLabel("available till: " +  data[4]);
                gbc.gridy = ind++;
                this.add(l5, gbc);
            }
            JLabel l6 = new JLabel("maximum ram usage: " + data[5] + " MB"); gbc.gridy = ind++; this.add(l6, gbc);
            JLabel l7 = new JLabel("maximum disk space usage: " + data[6] + " MB"); gbc.gridy = ind++; this.add(l7, gbc);
            if(data[7] != null) {
                String loc = data[8] + " (" + data[11] + ")";
                if(data[9] != null)
                    loc += ", " + data[9];
                if(data[10] != null)
                    loc += ", " + data[10];
                JLabel l8 = new JLabel("specifical location: " + loc);
                gbc.gridy = ind++;
                this.add(l8, gbc);
            }
        }
        else
        {
            JLabel l1 = new JLabel("offer id: " + data[0]); gbc.gridy = ind++; this.add(l1, gbc);
            JLabel l2 = new JLabel("name: " + data[1]); gbc.gridy = ind++; this.add(l2, gbc);
            JLabel l3 = new JLabel("starting price: " + data[2] + "$"); gbc.gridy = ind++; this.add(l3, gbc);
            JLabel l4 = new JLabel("available since: " + data[3]); gbc.gridy = ind++; this.add(l4, gbc);
            if(data[4] != null) {
                JLabel l5 = new JLabel("available till: " + data[4]);
                gbc.gridy = ind++;
                this.add(l5, gbc);
            }
            JButton serverButton = new JButton("server info");
            serverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenServer((int)data[5]);
                }
            });
            gbc.gridy = ind++; this.add(serverButton, gbc);

            JButton userButton = new JButton("owner info (" + data[7] + ")");
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenProfile((int)data[6]);
                }
            });
            gbc.gridy = ind++; this.add(userButton, gbc);
        }
        if(data.length == 12 || user != (int)data[6]) {
            JButton buyButton = new JButton("buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenOfferBuy(offer);
                }
            });
            gbc.gridy = ind++;
            this.add(buyButton, gbc);
        }

    }
}
