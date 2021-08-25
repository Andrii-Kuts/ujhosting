package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComputersList extends JPanel
{
    private Window window;
    private int user, owner;
    private PostgresManager psql;

    private JPanel servers_panel;
    private JPanel[] computers;

    private int curPage = 0;
    private static final int pageSize = 10;

    public ComputersList(Window window, int user, int owner)
    {
        this.window = window;
        this.user = user;
        this.owner = owner;
        psql = new PostgresManager(window);
        psql.Connect();

        String ownerName = psql.GetUserNickname(owner);
        JLabel title = new JLabel(ownerName + "\'s computers");

        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipady = 5;
        gbc.ipadx = 5;

        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);

        gbc.gridx = 1;
        this.add(title, gbc);

        JButton pageBackButton = new JButton("/\\");
        pageBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                page_back();
            }
        });
        JButton pageUpButton = new JButton("\\/");
        pageUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                page_front();
            }
        });
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 0.5;
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(pageBackButton, gbc);
        gbc.gridy = 2;
        this.add(pageUpButton, gbc);

        Computer[] comps = psql.GetUserComputers(-1, owner);
        computers = new JPanel[comps.length];
        for(int i = 0; i < comps.length; i++)
        {
            computers[i] = comps[i].getFullPanel(window);
        }

        servers_panel = new JPanel();
        servers_panel.setLayout(new GridLayout(pageSize, 1, 5, 5));
        loadPage();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1; gbc.weighty = 1;

        this.add(servers_panel, gbc);

    }

    private void loadPage()
    {
        servers_panel.removeAll();
        for(int i = curPage*pageSize; i < curPage*pageSize+pageSize; i++)
        {
            if(i >= computers.length)
            {
                JLabel lab = new JLabel("no more computers");
                servers_panel.add(lab);
            }
            else
                servers_panel.add(computers[i]);
        }
        window.Pack();
        window.frame.repaint();
    }

    private void page_back()
    {
        if(curPage == 0)
            return;
        curPage--;
        loadPage();
    }

    private void page_front()
    {
        if(curPage*pageSize+pageSize >= computers.length)
            return;
        curPage++;
        loadPage();
    }
}
