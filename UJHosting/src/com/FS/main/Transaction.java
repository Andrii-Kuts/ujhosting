package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Transaction
{
    public Integer user_id, offer_id, server_id, transaction_id;
    public String nickname, date, card_number;
    public Double value;

    public JPanel getSimplePanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridLayout(1, 2, 5, 5));
        panel.add(new JLabel(value.toString() + "$"));
        panel.add(new JLabel(date));
        panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        return panel;
    }

    private String cardNum()
    {
        if(card_number == "null" || card_number == "" || card_number == null)
            return "";
        String s = "*";
        for(int i = 12; i < 16; i++)
        {
            s += card_number.charAt(i);
        }
        return s;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        RoundPanel pn = new RoundPanel(15, Color.white);
        pn.setLayout(new GridBagLayout());

        int ind = 0;
        gbc.weightx = 1;
        gbc.gridx = ind++;
        pn.add(new JLabel("transaction number: " + transaction_id), gbc);
        gbc.gridx = ind++;
        pn.add(new JLabel(value.toString() + "$"), gbc);
        gbc.gridx = ind++;
        pn.add(new JLabel(date), gbc);

        gbc.gridx = ind++;
        pn.add(new JLabel(cardNum()), gbc);


        ind = 0; gbc.gridx = ind++;
        gbc.weightx = 1;
        panel.add(pn, gbc);
        gbc.weightx = 0;

        gbc.gridx = ind++;
        JButton userButton = new JButton(nickname);
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(user_id);
            }
        });
        panel.add(userButton, gbc);
        if(offer_id != 0)
        {
            JButton offerButton = new JButton("offer info");
            offerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenOffer(offer_id);
                }
            });
            gbc.gridx = ind++;
            panel.add(offerButton, gbc);
            JButton serverButton = new JButton("server info");
            serverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenServer(server_id);
                }
            });
            gbc.gridx = ind++;
            panel.add(serverButton, gbc);
        }

        return panel;
    }
}
