package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscountsList extends JPanel
{
    private Window window;
    private int user;
    private PostgresManager psql;

    private JPanel discounts_panel;
    private JPanel[] discounts;

    private int curPage = 0;
    private static final int pageSize = 17;

    public DiscountsList(Window window, int user)
    {
        this.window = window;
        this.user = user;

        psql = new PostgresManager(window);
        psql.Connect();

        JLabel title = new JLabel("All discounts");

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

        Discount[] dscs = new Discount[1];
        dscs = psql.GetAllDiscounts();
        discounts = new JPanel[dscs.length];
        System.out.println(discounts.length);
        for(int i = 0; i < discounts.length; i++)
        {
            discounts[i] = dscs[i].getLongPanel(window);
        }

        discounts_panel = new JPanel();
        discounts_panel.setLayout(new GridLayout(pageSize, 1, 5, 5));
        loadPage();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1; gbc.weighty = 1;

        this.add(discounts_panel, gbc);

    }

    private void loadPage()
    {
        discounts_panel.removeAll();
        for(int i = curPage*pageSize; i < curPage*pageSize+pageSize; i++)
        {
            if(i >= discounts.length)
            {
                JLabel lab = new JLabel("no more discounts");
                discounts_panel.add(lab);
            }
            else
                discounts_panel.add(discounts[i]);
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
        if(curPage*pageSize+pageSize >= discounts.length)
            return;
        curPage++;
        loadPage();
    }
}
