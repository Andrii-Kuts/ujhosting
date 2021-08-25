package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Computer
{
    public Integer computer_id, owner_id, location_id, ram, disk, cores;
    public Double cpu_freq;
    public String ownerNickname, city, country, country_full, cpu_name, organisation;
    public Server[] servers;

    public Computer()
    {

    }

    public JPanel getSimplePanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        RoundPanel pan = new RoundPanel(15, Color.white);
        pan.setLayout(new GridLayout(4, 1, 5, 5));

        JLabel ramLabel = new JLabel(((Integer)(ram/1024)).toString() + " GB Ram");
        JLabel diskLabel = new JLabel(((Integer)(disk/1024)).toString() + " GB Disk");

        JLabel loc = new JLabel(city + ", " + country);
        JLabel ownLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(ownerNickname);
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner_id);
            }
        });
        JButton serverButton = new JButton("more info");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenComputer(computer_id);
            }
        });

        pan.add(ramLabel);
        pan.add(diskLabel);
        pan.add(loc);
        pan.add(ownLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(pan, gbc);
        gbc.weighty = 0; gbc.gridy = 1;
        panel.add(ownerButton, gbc);
        gbc.gridy = 2;
        panel.add(serverButton, gbc);

        return panel;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        RoundPanel pan = new RoundPanel(15, Color.white);
        pan.setLayout(new GridLayout(1, 5, 5, 10));

        JLabel ramLabel = new JLabel(((Integer)(ram/1024)).toString() + " GB Ram | ");
        JLabel diskLabel = new JLabel(((Integer)(disk/1024)).toString() + " GB Disk | ");

        JLabel loc = new JLabel(city + ", " + country + " | ");
        JLabel ownLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(ownerNickname);
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner_id);
            }
        });
        JButton serverButton = new JButton("more info");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenComputer(computer_id);
            }
        });

        pan.add(ramLabel);
        pan.add(diskLabel);
        pan.add(loc);
        pan.add(ownLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(pan, gbc);
        gbc.weightx = 0; gbc.gridx = 1;
        panel.add(ownerButton, gbc);
        gbc.gridx = 2;
        panel.add(serverButton, gbc);

        return panel;
    }


}
