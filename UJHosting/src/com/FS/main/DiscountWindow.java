package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscountWindow extends JPanel
{
    private Window window;
    private int user, discount;
    private PostgresManager psql;

    public DiscountWindow(Window window, int user, int discount)
    {
        this.window = window;
        this.user = user;
        this.discount = discount;
        psql = new PostgresManager(window);
        psql.Connect();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        Discount dsc = psql.GetDiscountInfo(discount);
        JPanel dscPanel = dsc.getFullPanel(window);

        this.add(backButton, gbc);
        gbc.gridy = 1;
        this.add(dscPanel, gbc);
    }

}
