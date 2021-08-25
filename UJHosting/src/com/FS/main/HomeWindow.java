package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeWindow extends JPanel
{
    private Window window;
    private PostgresManager psql;
    int user;

    private JPanel homePanel;

    private JPanel offersPanel, offerDiscountPanel, profilePanel;
    private JButton profileButton, logoutButton;

    public HomeWindow(Window window, int user)
    {
        this.window = window;
        this.user = user;
        psql = new PostgresManager(window);
        psql.Connect();

        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(2, 1, 10, 10));

        profileButton = new JButton(psql.GetUserNickname(user));
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                OpenProfile();
            }
        });

        logoutButton = new JButton("log out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logout();
            }
        });

        profilePanel.add(profileButton);
        profilePanel.add(logoutButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        homePanel.add(profilePanel, gbc);

        JPanel hostOffersPanel = new JPanel();

        hostOffersPanel = new JPanel();
        hostOffersPanel.setLayout(new GridBagLayout());

        GridBagConstraints cb = new GridBagConstraints();

        cb.gridx = 0; cb.gridy = 0; cb.fill = GridBagConstraints.VERTICAL;
        JLabel offersLabel = new JLabel("Popular Offers");
        hostOffersPanel.add(offersLabel, cb);

        cb.gridy = 1; cb.weighty = 1; cb.fill = GridBagConstraints.BOTH;
        JPanel offersPanel = new RoundPanel(15, new Color(200, 200, 200));
        offersPanel.setLayout(new GridLayout(3, 3, 10, 10));
        offersPanel.setBorder(new CompoundBorder(offersPanel.getBorder(), new EmptyBorder(10, 10, 5, 5)));

        HostOffer[] offers = psql.GetPopularOffers(9);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                offersPanel.add(offers[i*3+j].GetPanel(window));
            }
        }

        hostOffersPanel.add(offersPanel, cb);

        cb.gridy = 2;
        cb.weighty = 0;
        cb.fill = GridBagConstraints.VERTICAL;

        JButton allOffersButton = new JButton("Show All Offers");
        allOffersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffersList();
            }
        });

        hostOffersPanel.add(allOffersButton, cb);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;

        homePanel.add(hostOffersPanel, gbc);

        offerDiscountPanel = new JPanel();
        offerDiscountPanel.setLayout(new GridBagLayout());
        cb = new GridBagConstraints();
        cb.fill = GridBagConstraints.HORIZONTAL;
        cb.insets = new Insets(5, 5, 5, 5);

        offerDiscountPanel.add(new JLabel("discounts"), cb);
        RoundPanel offerDiscounts = new RoundPanel(15, new Color(200, 200, 200));
        Discount[] discounts = psql.GetOfferDiscounts(9);
        if(discounts == null || discounts.length == 0)
        {
            offerDiscounts.add(new JLabel("no discounts"));
        }
        else
        {
            offerDiscounts.setLayout(new GridLayout(3, 3, 5, 5));
            for(int i = 0; i < discounts.length; i++)
            {
                offerDiscounts.add(discounts[i].getSimplePanel(window));
            }
        }
        offerDiscounts.setBorder(new CompoundBorder(offerDiscounts.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        cb.weighty = 1; cb.gridy = 1;
        offerDiscountPanel.add(offerDiscounts, cb);

        JButton discountsButton = new JButton("all discounts");
        discountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.DiscountList();
            }
        });
        cb.weighty = 0;  cb.gridy = 2;
        offerDiscountPanel.add(discountsButton, cb);

        gbc.gridx = 2; gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        homePanel.add(offerDiscountPanel, gbc);

        homePanel.setSize(1280, 720);

        this.add(homePanel);
        this.setSize(1280, 720);
    }

    private void OpenProfile()
    {
        window.OpenProfile(user);
    }

    private void Logout()
    {
        window.MainMenu();
    }
}
