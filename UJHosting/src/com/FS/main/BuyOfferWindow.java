package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyOfferWindow extends JPanel
{
    private Window window;
    private int user, offer;
    private PostgresManager psql;

    private double totalPrice;
    private JLabel errorLabel;

    public BuyOfferWindow(Window window, int user, int offer)
    {
        this.window = window;
        this.user = user;
        this.offer = offer;
        psql = new PostgresManager(window);
        psql.Connect();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 5; gbc.ipady = 5;

        JButton home = new JButton("home");
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.HomeWindow(user);
            }
        });
        this.add(home, gbc);

        Object[] data = psql.GetOfferInfo(offer);
        JLabel l1 = new JLabel("offer id: " + data[0]); gbc.gridy = 1; this.add(l1, gbc);
        JLabel l2 = new JLabel("name: " + data[1]); gbc.gridy = 2; this.add(l2, gbc);
        JButton offerButton = new JButton("offer info");
        offerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenOffer(offer);
            }
        });
        gbc.gridy = 3; this.add(offerButton, gbc);

        errorLabel = new JLabel(""); errorLabel.setForeground(Color.red);
        gbc.gridy = 4; gbc.gridwidth = 2; this.add(errorLabel, gbc);

        gbc.gridwidth = 1;
        double offerDiscount = psql.OfferDiscount(offer);
        double userDiscount = psql.UserDiscount(user);
        totalPrice = psql.OfferPrice(user, offer);

        gbc.gridx = 1;
        JLabel l3 = new JLabel("offer discount -" + offerDiscount + "%"); gbc.gridy = 0; this.add(l3, gbc);
        JLabel l4 = new JLabel("your personal discount -" + userDiscount + "%"); gbc.gridy = 1; this.add(l4, gbc);
        JLabel l5 = new JLabel("total price: " + totalPrice + "$"); gbc.gridy = 2; this.add(l5, gbc);
        JButton buyButton = new JButton("buy"); gbc.gridy = 3; this.add(buyButton, gbc);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Buy();
            }
        });
        JButton payButton = new JButton("money menu");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenPay();
            }
        });
        gbc.gridy = 4; this.add(payButton, gbc);
        window.Pack();
        window.repaint();
    }

    private void Buy()
    {
        double balanc = psql.UserBalance(user);
        if(balanc < totalPrice)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("not enough money on your account");
            window.Pack();
            window.repaint();
            return;
        }
        boolean res = psql.BuyOffer(user, offer);
        if(!res)
        {
            errorLabel.setForeground(Color.red);
            errorLabel.setText("something went wrong while buying");
            window.Pack();
            window.repaint();
            return;
        }
        errorLabel.setForeground(Color.green);
        errorLabel.setText("successfully bought this offer");
        window.Pack();
        window.repaint();
        return;
    }


}
