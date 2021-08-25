package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserDiscount
{
    public Integer discount_id, user_id;
    public Double percent;
    public String user_nickname, time_from, time_to;

    public JPanel getSimplePanel()
    {
        RoundPanel panel = new RoundPanel();

        panel.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel userLabel = new JLabel(user_nickname);
        JLabel percLabel = new JLabel("-" + percent.toString() + "%");
        JButton moreButton = new JButton("more info");

        panel.add(userLabel);
        panel.add(percLabel);
        panel.add(moreButton);

        panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(10, 10, 10, 10)));

        return panel;
    }
}
