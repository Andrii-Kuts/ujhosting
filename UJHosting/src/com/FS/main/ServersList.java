package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServersList extends JPanel
{
    private Window window;
    private int user, owner;
    private PostgresManager psql;

    private JPanel servers_panel;
    private JPanel[] servers;

    private int curPage = 0;
    private static final int pageSize = 10;

    public ServersList(Window window, int user, int owner, int computer)
    {
        this.window = window;
        this.user = user;
        this.owner = owner;
        psql = new PostgresManager(window);
        psql.Connect();

        String ownerName = psql.GetUserNickname(owner);
        JLabel title = new JLabel(ownerName + "\'s servers");

        JButton backButton = new JButton("back");
        if(owner != -1) {
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenProfile(owner);
                }
            });
        }
        else backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenComputer(computer);
            }
        });


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipady = 5;
        gbc.ipadx = 5;

        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);

        if(owner != -1) {
            gbc.gridx = 1;
            this.add(title, gbc);
        }

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

        Server[] servs = new Server[1];
        if(owner != -1)
            servs = psql.GetUserServers(-1, owner);
        else
            servs = psql.GetComputersServers(-1, computer);
        servers = new JPanel[servs.length];
        System.out.println(servers.length);
        for(int i = 0; i < servers.length; i++)
        {
            servers[i] = servs[i].getFullPanel(window);
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
            if(i >= servers.length)
            {
                JLabel lab = new JLabel("no more servers");
                servers_panel.add(lab);
            }
            else
                servers_panel.add(servers[i]);
        }
        window.frame.repaint();
        window.frame.pack();

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
        if(curPage*pageSize+pageSize >= servers.length)
            return;
        curPage++;
        loadPage();
    }
}
