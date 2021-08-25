package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardsList extends JPanel
{
    private Window window;
    private int user, owner;
    private PostgresManager psql;

    private JPanel cards_panel;
    private JPanel[] cardss;

    JButton addCard;

    private int curPage = 0;
    private static final int pageSize = 10;

    public CardsList(Window window, int user, int owner)
    {
        this.window = window;
        this.user = user;
        this.owner = owner;
        psql = new PostgresManager(window);
        psql.Connect();

        String ownerName = psql.GetUserNickname(owner);
        JLabel title = new JLabel(ownerName + "\'s cards");

        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipady = 5;
        gbc.ipadx = 5;

        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);

        if(owner != -1) {
            gbc.gridx = 1;
            this.add(title, gbc);
        }

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

        Card[] cards = new Card[1];
        cards = psql.GetUserCards(-1, owner);
        cardss = new JPanel[cards.length];
        System.out.println(cards.length);
        for(int i = 0; i < cards.length; i++)
        {
            cardss[i] = cards[i].getFullPanel(window);
        }

        cards_panel = new JPanel();
        cards_panel.setLayout(new GridLayout(pageSize+1, 1, 5, 5));


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1; gbc.weighty = 1;

        this.add(cards_panel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1; gbc.weighty = 01;

        addCard = new JButton("add a card");
        addCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.AddCard();
            }
        });

        loadPage();
    }

    private void loadPage()
    {
        cards_panel.removeAll();
        for(int i = curPage*pageSize; i < curPage*pageSize+pageSize; i++)
        {
            if(i >= cardss.length)
            {
                JLabel lab = new JLabel("no more cards");
                cards_panel.add(lab);
            }
            else
                cards_panel.add(cardss[i]);
        }
        cards_panel.add(addCard);
        this.validate();
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
        if(curPage*pageSize+pageSize >= cardss.length)
            return;
        curPage++;
        loadPage();
    }
}
