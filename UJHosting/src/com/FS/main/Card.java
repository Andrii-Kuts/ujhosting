package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card
{
    public String number, expirationDate, cvc;
    public Integer card_id;

    public JPanel getSimplePanel()
    {
        RoundPanel panel = new RoundPanel();
        String str = "*";
        for(int i = 12; i < 16; i++)
        {
            str += number.charAt(i);
        }
        panel.add(new JLabel(str));
        return panel;
    }

    @Override
    public String toString() {
        String str = "*";
        for(int i = 12; i < 16; i++)
        {
            str += number.charAt(i);
        }
        return str;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridLayout(1, 4, 5, 5));
        panel.add(new JLabel(number));
        panel.add(new JLabel("cvc: " + cvc));
        panel.add(new JLabel(expirationDate));
        JButton edt = new JButton("edit");
        edt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.EditCard(card_id);
            }
        });
        panel.add(edt);
        return panel;
    }
}
