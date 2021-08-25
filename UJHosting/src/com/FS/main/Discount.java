package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Discount
{
    public Integer discount_id, user_id, offer_id;
    public String time_from, time_to, offer_name, nickname;
    public Double percent;

    public JPanel getSimplePanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridLayout(3, 1, 5,5));
        RoundPanel infoPanel = new RoundPanel(15, Color.white);
        infoPanel.setLayout(new GridLayout(2, 1, 5, 5));
        infoPanel.add(new JLabel("-" + percent.toString() + "%"));
        if(offer_id != null) {
            infoPanel.add(new JLabel(offer_name));
            JButton offerButton = new JButton("offer info");
            offerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenOffer(offer_id);
                }
            });
            panel.add(infoPanel);
            panel.add(offerButton);
        }
        else {
            infoPanel.add(new JLabel("for " + nickname));
            panel.add(infoPanel);
        }

        JButton discountButton = new JButton("Discounts info");
        discountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenDiscount(discount_id);
            }
        });
        panel.add(discountButton);
        panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        return panel;
    }

    public JPanel getLongPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridLayout(1, 3, 5,5));
        RoundPanel infoPanel = new RoundPanel(15, Color.white);
        infoPanel.setLayout(new GridLayout(1, 2, 5, 5));
        infoPanel.add(new JLabel("-" + percent.toString() + "%"));
        if(offer_id != null) {
            infoPanel.add(new JLabel(offer_name));
            JButton offerButton = new JButton("offer info");
            offerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenOffer(offer_id);
                }
            });
            panel.add(infoPanel);
            panel.add(offerButton);
        }
        else {
            infoPanel.add(new JLabel("for " + nickname));
            panel.add(infoPanel);
        }

        JButton discountButton = new JButton("Discounts info");
        discountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenDiscount(discount_id);
            }
        });
        panel.add(discountButton);
        panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        return panel;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridLayout(3, 1, 5,5));
        RoundPanel infoPanel = new RoundPanel(15, Color.white);
        infoPanel.setLayout(new GridLayout(4, 1, 5, 5));
        infoPanel.add(new JLabel("-" + percent.toString() + "%"));
        infoPanel.add(new JLabel("since " + time_from));
        infoPanel.add(new JLabel("till " + time_to));
        if(offer_id != null) {
            infoPanel.add(new JLabel(offer_name));
            JButton offerButton = new JButton("offer info");
            offerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenOffer(offer_id);
                }
            });
            panel.add(infoPanel);
            panel.add(offerButton);
        }
        else {
            infoPanel.add(new JLabel("for " + nickname));
            JButton userButton = new JButton("user info");
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenProfile(user_id);
                }
            });
            panel.add(infoPanel);
            panel.add(userButton);
        }

        panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        return panel;
    }
}
