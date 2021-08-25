package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OffersList extends JPanel
{
    private Window window;
    private int user;
    private PostgresManager psql;

    private JPanel offers_panel;
    private JPanel[] offers;

    private int curPage = 0;
    private static final int pageSize = 17;

    public OffersList(Window window, int user)
    {
        this.window = window;
        this.user = user;
        psql = new PostgresManager(window);
        psql.Connect();

        JLabel title = new JLabel("all offers");

        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
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

        HostOffer[] offs = new HostOffer[1];
        ServerOffer[] serv_offs = new ServerOffer[1];
        offs = psql.GetPopularOffers(-1);
        serv_offs = psql.GetServerOffers(-1);
        offers = new JPanel[offs.length + serv_offs.length];

        for(int i = 0; i < offs.length; i++)
        {
            offers[i] = offs[i].getFullPanel(window);
        }
        for(int i = offs.length; i <  offers.length; i++)
        {
            offers[i] = serv_offs[i-offs.length].getFullPanel(window);
        }

        offers_panel = new JPanel();
        offers_panel.setLayout(new GridLayout(pageSize, 1, 5, 5));
        loadPage();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1; gbc.weighty = 1;

        this.add(offers_panel, gbc);

    }

    private void loadPage()
    {
        offers_panel.removeAll();
        for(int i = curPage*pageSize; i < curPage*pageSize+pageSize; i++)
        {
            if(i >= offers.length)
            {
                JLabel lab = new JLabel("no more offers");
                offers_panel.add(lab);
            }
            else
                offers_panel.add(offers[i]);
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
        if(curPage*pageSize+pageSize >= offers.length)
            return;
        curPage++;
        loadPage();
    }
}
