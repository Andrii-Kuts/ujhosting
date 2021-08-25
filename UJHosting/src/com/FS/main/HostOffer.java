package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostOffer
{
    public String name, time_from, time_to, country, city;
    public Integer offer_id, maximum_cpu, maximum_disk, location_id;
    public Double price, discount;

    public JPanel GetPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        RoundPanel image = new RoundPanel(15, Color.white);
        image.setLayout(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();
        gb.fill = GridBagConstraints.BOTH;


        JLabel nameLabel = new JLabel(name);
        JLabel cpuLabel = new JLabel(((Integer)(maximum_cpu/1024)).toString() + " GB Ram");
        JLabel diskLabel = new JLabel(((Integer)(maximum_disk/1024)).toString() + " GB Disk");
        JLabel priceLabel = new JLabel(price.toString() + "$" + ((discount <= 0)? "" : " -" + discount + "%"));

    //    gb.insets = new Insets(10, 10, 10, 10);
        gb.gridx = 0; gb.gridy = 0;
        gb.weighty = 0;
        image.add(nameLabel, gb);
        gb.gridy = 1;
        gb.weighty = 0.5;
        image.add(cpuLabel, gb);
        gb.gridy = 2;
        gb.weighty = 0.5;
        image.add(diskLabel, gb);
        gb.gridy = 3;
        gb.weighty = 0;
        image.add(priceLabel, gb);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 1;

        panel.add(image, gbc);

        String loc = country;
        int ind = 1;
        if(country != null)
        {
            if(city != null)
                loc += ", " + city;
            JLabel locationLabel = new JLabel(loc);
            gbc.gridy = 1;
            gbc.weighty = 0;
            panel.add(locationLabel, gbc);
            ind++;
        }

        JButton moreButton = new JButton("more");
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffer(offer_id);
            }
        });
        gbc.gridy = ind;
        gbc.weighty = 0;
        panel.add(moreButton, gbc);

        return panel;
    }

    public JPanel getFullPanel(Window window)
    {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        RoundPanel image = new RoundPanel(15, Color.white);
        image.setLayout(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();
        gb.fill = GridBagConstraints.BOTH;


        JLabel nameLabel = new JLabel(name);
        JLabel cpuLabel = new JLabel(((Integer)(maximum_cpu/1024)).toString() + " GB Ram");
        JLabel diskLabel = new JLabel(((Integer)(maximum_disk/1024)).toString() + " GB Disk");
        JLabel priceLabel = new JLabel(price.toString() + "$" + ((discount <= 0)? "" : " -" + discount + "%"));

        //    gb.insets = new Insets(10, 10, 10, 10);
        gb.gridx = 0; gb.gridy = 0;
        gb.weightx = 0.1;
        image.add(nameLabel, gb);
        gb.gridx = 1;
        gb.weightx = 0.5;
        image.add(cpuLabel, gb);
        gb.gridx = 2;
        gb.weightx = 0.5;
        image.add(diskLabel, gb);
        gb.gridx = 3;
        gb.weightx = 0.1;
        image.add(priceLabel, gb);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1;

        panel.add(image, gbc);

        String loc = country;
        int ind = 1;
        if(country != null)
        {
            if(city != null)
                loc += ", " + city;
            JLabel locationLabel = new JLabel(loc);
            gbc.gridx = 1;
            gbc.weightx = 0;
            panel.add(locationLabel, gbc);
            ind++;
        }

        JButton moreButton = new JButton("more");
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffer(offer_id);
            }
        });
        gbc.gridx = ind;
        gbc.weightx = 0;
        panel.add(moreButton, gbc);

        return panel;
    }
}
