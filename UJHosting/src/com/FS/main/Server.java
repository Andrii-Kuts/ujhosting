package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server
{
    public Integer server_id, owner_id, computer_id, maximum_cpu, maximum_disk, cores_num;
    public Double main_cpu_frequency;
    public String owner_nickname, cpu_model, country, city, organisation, country_full;

    public Server()
    {

    }

    public JPanel getSimplePanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        RoundPanel pn = new RoundPanel(15, Color.white);
        pn.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel ram = new JLabel(((Integer)(maximum_cpu/1024)).toString() + " GB Ram");
        JLabel disk = new JLabel(((Integer)(maximum_disk/1024)).toString() + " GB Disk");

        JLabel loc = new JLabel(city + ", " + country);
        JLabel ownLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(owner_nickname);
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
                window.OpenServer(server_id);
            }
        });

        pn.add(ram);
        pn.add(disk);
        pn.add(loc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.weighty = 1;
        panel.add(pn, gbc);
        gbc.gridy = 1; gbc.weighty = 0.1;
        panel.add(ownLabel, gbc);
        gbc.gridy = 2;
        panel.add(ownerButton, gbc);
        gbc.gridy = 3;
        panel.add(serverButton, gbc);

        return panel;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        RoundPanel pn = new RoundPanel(15, Color.white);
        pn.setLayout(new GridLayout(1, 3, 5, 5));

        JLabel ram = new JLabel(((Integer)(maximum_cpu/1024)).toString() + " GB Ram");
        JLabel disk = new JLabel(((Integer)(maximum_disk/1024)).toString() + " GB Disk");

        JLabel loc = new JLabel(city + ", " + country);
        JLabel ownLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(owner_nickname);
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
                window.OpenServer(server_id);
            }
        });

        pn.add(ram);
        pn.add(disk);
        pn.add(loc);
        pn.add(ownLabel);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.weightx = 1;
        panel.add(pn, gbc);
        gbc.gridx = 1; gbc.weightx = 0.1;
        panel.add(ownerButton, gbc);
        gbc.gridx = 2;
        panel.add(serverButton, gbc);

        return panel;
    }
}
