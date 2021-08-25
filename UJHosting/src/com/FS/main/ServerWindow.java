package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JPanel
{
    private Window window;
    private int user, server;
    private PostgresManager psql;

    public ServerWindow(Window window, int user, int server)
    {
        this.window = window;
        this.user = user;
        this.server = server;
        psql = new PostgresManager(window);
        psql.Connect();

        Server s = psql.GetServerInfo(server);

        RoundPanel infoPanel = new RoundPanel();
        infoPanel.setLayout(new GridBagLayout());

        RoundPanel servPanel = new RoundPanel(15, Color.white);
        JLabel idLabel = new JLabel("server id: " + s.server_id.toString());
        JLabel maxCpu = new JLabel("maximum cpu usage: " + s.maximum_cpu.toString() + " MB");
        JLabel maxDisk = new JLabel("maximum disk usage: " + s.maximum_disk.toString() + " MB");
        servPanel.setLayout(new GridLayout(3, 1, 5, 5));
        servPanel.add(idLabel);
        servPanel.add(maxCpu);
        servPanel.add(maxDisk);

        RoundPanel compPanel = new RoundPanel(15, Color.white);
        JLabel coresNumLabel = new JLabel("number of cores: " + s.cores_num.toString());
        JLabel cpuModelLabel = new JLabel("cpu model: " + s.cpu_model);
        JLabel mainHZ = new JLabel("main cpu frequency: " + s.main_cpu_frequency + " GHz");
        JButton compButton = new JButton("computer info");
        compButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenComputer(s.computer_id);
            }
        });
        compPanel.setLayout(new GridLayout(4, 1, 5, 5));
        compPanel.add(coresNumLabel);
        compPanel.add(cpuModelLabel);
        compPanel.add(mainHZ);
        compPanel.add(compButton);

        RoundPanel locationPanel = new RoundPanel(15, Color.white);
        JLabel country = new JLabel("country: " + s.country + " (" + s.country_full + ")");
        JLabel city = new JLabel("city: " + s.city);
        JLabel org = new JLabel("organisation: " + s.organisation);
        locationPanel.setLayout(new GridLayout(4, 1, 5, 5));
        locationPanel.add(country);
        locationPanel.add(city);
        locationPanel.add(org);

        RoundPanel userPanel = new RoundPanel(15, Color.white);
        JLabel ownerLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(s.owner_nickname);
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(s.owner_id);
            }
        });
        userPanel.setLayout(new GridLayout(6, 1, 5, 5));
        userPanel.add(ownerLabel);
        userPanel.add(ownerButton);
        int offer_id = psql.GetServerOffer(server);
        if(offer_id != -1)
        {
            if(user != s.owner_id) {
                JLabel offerLabel = new JLabel("server is on sale");
                JButton offerButton = new JButton("buy server");
                offerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        window.OpenOffer(offer_id);
                    }
                });
                userPanel.add(offerLabel);
                userPanel.add(offerButton);
            }
            else
            {
                JLabel offerLabel = new JLabel("you\'re selling this server");
                JButton offerButton = new JButton("view offer");
                offerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        window.OpenOffer(offer_id);
                    }
                });
                JButton cancelOfferButton = new JButton("cancel offer");
                cancelOfferButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        psql.CancelSell(offer_id);
                        window.OpenServer(server);
                    }
                });
                userPanel.add(offerLabel);
                userPanel.add(offerButton);
                userPanel.add(cancelOfferButton);
            }
        }
        else if(user == s.owner_id)
        {
            JButton offerButton = new JButton("sell server");
            offerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.SellServer(server);
                }
            });
            userPanel.add(offerButton);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weighty = 1; gbc.weightx = 1;
        infoPanel.add(servPanel, gbc);
        gbc.gridx = 1;
        infoPanel.add(locationPanel, gbc);
        gbc.gridx = 0;  gbc.gridy = 1;
        infoPanel.add(compPanel, gbc);
        gbc.gridx = 1;
        infoPanel.add(userPanel, gbc);

        JButton backButton = new JButton("home");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        gbc.weighty = 1;
        this.add(infoPanel, gbc);
        gbc.weighty = 0.05;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);
    }


}
