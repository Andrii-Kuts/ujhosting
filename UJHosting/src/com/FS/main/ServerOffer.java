package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerOffer
{
    public String name, nickname;
    public Integer offer_id, server_id, owner_id;
    public Double discount, price;

    public JPanel GetPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        RoundPanel image = new RoundPanel(15, Color.white);
        image.setLayout(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();
        gb.fill = GridBagConstraints.BOTH;


        JLabel nameLabel = new JLabel(name);
        JButton serverButton = new JButton("server info");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenServer(server_id);
            }
        });
        JButton ownerButton = new JButton(nickname);
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner_id);
            }
        });
        JLabel priceLabel = new JLabel(price.toString() + "$" + ((discount <= 0)? "" : " -" + discount + "%"));

        //    gb.insets = new Insets(10, 10, 10, 10);
        gb.gridx = 0; gb.gridy = 0;
        gb.weighty = 0;
        image.add(nameLabel, gb);
        gb.gridy = 1;
        gb.weighty = 0.5;
        image.add(serverButton, gb);
        gb.gridy = 2;
        gb.weighty = 0.5;
        image.add(ownerButton, gb);
        gb.gridy = 3;
        gb.weighty = 0;
        image.add(priceLabel, gb);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 1;

        panel.add(image, gbc);

        int ind = 1;

        JButton moreButton = new JButton("more");
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffer(offer_id);
            }
        });
        gbc.gridy = ind;
        gbc.weighty = 0;
        panel.add(moreButton, gbc);

        return panel;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        RoundPanel image = new RoundPanel(15, Color.white);
        image.setLayout(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();
        gb.fill = GridBagConstraints.BOTH;


        JLabel nameLabel = new JLabel(name);
        JButton serverButton = new JButton("server info");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenServer(server_id);
            }
        });
        JButton ownerButton = new JButton(nickname);
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner_id);
            }
        });
        JLabel priceLabel = new JLabel(price.toString() + "$" + ((discount <= 0)? "" : " -" + discount + "%"));

        //    gb.insets = new Insets(10, 10, 10, 10);
        gb.gridx = 0; gb.gridy = 0;
        gb.weightx = 0;
        image.add(nameLabel, gb);
        gb.gridx  = 1;
        gb.weightx = 0.5;
        image.add(serverButton, gb);
        gb.gridx = 2;
        gb.weightx = 0.5;
        image.add(ownerButton, gb);
        gb.gridx = 3;
        gb.weightx = 0;
        image.add(priceLabel, gb);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1;

        panel.add(image, gbc);

        int ind = 1;

        JButton moreButton = new JButton("more");
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffer(offer_id);
            }
        });
        gbc.gridx = ind;
        gbc.weightx = 0;
        panel.add(moreButton, gbc);

        return panel;
    }
}
