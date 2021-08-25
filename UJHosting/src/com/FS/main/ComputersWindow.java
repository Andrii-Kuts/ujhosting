package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComputersWindow extends JPanel
{
    private Window window;
    private int user, computer;
    private PostgresManager psql;

    public ComputersWindow(Window window, int user, int computer)
    {
        this.window = window;
        this.user = user;
        this.computer = computer;
        psql = new PostgresManager(window);
        psql.Connect();

        Computer s = psql.GetComputerInfo(computer);

        RoundPanel infoPanel = new RoundPanel();
        infoPanel.setLayout(new GridBagLayout());

        RoundPanel servPanel = new RoundPanel(15, Color.white);
        JLabel idLabel = new JLabel("computer id: " + s.computer_id.toString());
        JLabel maxCpu = new JLabel("RAM: " + s.ram.toString() + " MB");
        JLabel maxDisk = new JLabel("disk space: " + s.disk.toString() + " MB");
        servPanel.setLayout(new GridLayout(3, 1, 5, 5));
        servPanel.add(idLabel);
        servPanel.add(maxCpu);
        servPanel.add(maxDisk);

        RoundPanel compPanel = new RoundPanel(15, Color.white);
        JLabel coresNumLabel = new JLabel("number of cores: " + s.cores.toString());
        JLabel cpuModelLabel = new JLabel("cpu model: " + s.cpu_name);
        JLabel mainHZ = new JLabel("main cpu frequency: " + s.cpu_freq + " GHz");
        compPanel.setLayout(new GridLayout(4, 1, 5, 5));
        compPanel.add(coresNumLabel);
        compPanel.add(cpuModelLabel);
        compPanel.add(mainHZ);

        RoundPanel locationPanel = new RoundPanel(15, Color.white);
        JLabel country = new JLabel("country: " + s.country + " (" + s.country_full + ")");
        JLabel city = new JLabel("city: " + s.city);
        JLabel org = new JLabel("organisation: " + s.organisation);
        locationPanel.setLayout(new GridLayout(3, 1, 5, 5));
        locationPanel.add(country);
        locationPanel.add(city);
        locationPanel.add(org);

        RoundPanel userPanel = new RoundPanel(15, Color.white);
        JLabel ownerLabel = new JLabel("owner:");
        JButton ownerButton = new JButton(s.ownerNickname);
        JLabel serversLabel = new JLabel("servers on computer:");
        JButton serversButton = new JButton("view servers");
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(s.owner_id);
            }
        });
        serversButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenComputerServersList(computer);
            }
        });
        userPanel.setLayout(new GridLayout(2, 1, 5, 5));
        userPanel.add(ownerLabel);
        userPanel.add(ownerButton);
        userPanel.add(serversLabel);
        userPanel.add(serversButton);

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
