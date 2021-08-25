package com.FS.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileWindow extends JPanel
{
     private Window window;
     private PostgresManager psql;
     private int user, owner;

     private JPanel serversFrame, computersFrame, transactionsFrame, userDataFrame, balanceFrame, cardsFrame;

     private JButton homeButton;

     public ProfileWindow(Window window, int user, int owner)
     {
          this.window = window;
          this.user = user;
          this.owner = owner;
          psql = new PostgresManager(window);
          psql.Connect();

          this.setLayout(new GridBagLayout());
          GridBagConstraints gb = new GridBagConstraints();

          serversFrame = new JPanel();
          serversFrame.setLayout(new GridBagLayout());
          GridBagConstraints gbc = new GridBagConstraints();

          JLabel serversLabel = new JLabel("your servers");
          gbc.fill = GridBagConstraints.VERTICAL;
          gbc.insets = new Insets(5, 5, 5, 5);

          serversFrame.add(serversLabel, gbc);

          JPanel serversPanel = new RoundPanel(15, new Color(200, 200, 200));
          serversPanel.setLayout(new GridLayout(1, 3, 5, 5));

          Server[] servers = psql.GetUserServers(3, owner);
          if(servers.length == 0)
          {
               JLabel noServers = new JLabel("you have no servers");
               serversPanel.setLayout(new GridLayout(1, 1, 5, 5));
               serversPanel.add(noServers);
          }
          else
          {
               for(int i = 0; i < servers.length; i++)
               {
                    serversPanel.add(servers[i].getSimplePanel(window));
               }
          }
          serversPanel.setBorder(new CompoundBorder(serversPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

          gbc.gridy = 1; gbc.weighty = 1;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          serversFrame.add(serversPanel, gbc);

          JButton showAllServersButton = new JButton("show all");
          showAllServersButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenServersList(owner);
               }
          });

          gbc.gridy = 2; gbc.weighty = 0;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          serversFrame.add(showAllServersButton, gbc);

          gb.fill = GridBagConstraints.HORIZONTAL;
          gb.gridx = 1; gb.gridy = 1;
          gb.weighty = 0.8; gb.weightx = 3;
          this.add(serversFrame, gb);

          homeButton = new JButton("back home");
          homeButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    window.HomeWindow(user);
               }
          });

          gb.fill = GridBagConstraints.NONE;
          gb.gridx = 0; gb.gridy = 0; gb.weighty = 0.1; gb.weightx = 2;
          this.add(homeButton, gb);

          computersFrame = new JPanel();
          computersFrame.setLayout(new GridBagLayout());
          gbc = new GridBagConstraints();
          gbc.insets = new Insets(5, 5, 5, 5);

          JLabel computersLabel = new JLabel("your computers");
          gbc.fill = GridBagConstraints.VERTICAL;

          computersFrame.add(computersLabel, gbc);

          JPanel computersPanel = new RoundPanel(15, new Color(200, 200, 200));
          computersPanel.setLayout(new GridLayout(1, 3, 5, 5));

          Computer[] computers = psql.GetUserComputers(3, owner);
          if(computers.length == 0)
          {
               JLabel noComputers = new JLabel("you have no computers");
               computersPanel.setLayout(new GridLayout(1, 1, 5, 5));
               computersPanel.add(noComputers);
          }
          else
          {
               for(int i = 0; i < computers.length; i++)
               {
                    computersPanel.add(computers[i].getSimplePanel(window));
               }
          }
          computersPanel.setBorder(new CompoundBorder(computersPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

          gbc.gridy = 1; gbc.weighty = 1;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          computersFrame.add(computersPanel, gbc);

          JButton showAllComputersButton = new JButton("show all");
          showAllComputersButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenComputersList(owner);
               }
          });

          gbc.gridy = 2; gbc.weighty = 0;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          computersFrame.add(showAllComputersButton, gbc);

          gb.fill = GridBagConstraints.HORIZONTAL;
          gb.gridx = 2; gb.gridy = 1;
          gb.weighty = 0.8; gb.weightx = 3;
          this.add(computersFrame, gb);

          userDataFrame = new RoundPanel();
          userDataFrame.setLayout(new GridBagLayout());
          gbc = new GridBagConstraints();
          gbc.fill = GridBagConstraints.HORIZONTAL;
          gbc.insets = new Insets(5, 5, 5, 5);
          User us = psql.GetUserInfo(owner);

          int ind = 0;
          for(String s : us.tags)
          {
               RoundPanel pn = new RoundPanel(10, new Color(51, 174, 212));
               JLabel lb = new JLabel(s);
               pn.add(lb);

               gbc.gridy = ind++;
               userDataFrame.add(pn, gbc);
          }

          JLabel l1 = new JLabel("nickname: " + us.nickname);
          JLabel l2 = new JLabel("first name: " + us.firstName);
          JLabel l3 = new JLabel("second name: " + us.secondName);
          JLabel l4 = new JLabel("e-mail: " + us.email);
          JLabel l5 = new JLabel("gender: " + us.gender);
          JLabel l6 = new JLabel("country: " + us.country + " (" + us.country_full + ")");
          JLabel l7 = new JLabel("city: " + us.city);
          JLabel l8 = new JLabel("organisation: " + us.organisation);

          gbc.gridy = ind++; userDataFrame.add(l1, gbc);
          gbc.gridy = ind++; userDataFrame.add(l2, gbc);
          gbc.gridy = ind++; userDataFrame.add(l3, gbc);
          gbc.gridy = ind++; userDataFrame.add(l4, gbc);
          gbc.gridy = ind++; userDataFrame.add(l5, gbc);
          gbc.gridy = ind++; userDataFrame.add(l6, gbc);
          gbc.gridy = ind++; userDataFrame.add(l7, gbc);
          gbc.gridy = ind++; userDataFrame.add(l8, gbc);

          boolean edt = psql.EditUserRights(user);
          edt |= (owner == user);

          if(edt)
          {
               JButton editButton = new JButton("edit");
               editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                         window.EditUser(owner);
                    }
               });
               gbc.gridy = ind++; userDataFrame.add(editButton, gbc);
          }

          gb.fill = GridBagConstraints.HORIZONTAL;
          gb.gridx = 0; gb.gridy = 1;
          gb.weighty = 0.8; gb.weightx = 2;
          this.add(userDataFrame, gb);

          balanceFrame = new JPanel();
          balanceFrame.setLayout(new GridBagLayout());
          gbc = new GridBagConstraints();
          gbc.fill = GridBagConstraints.HORIZONTAL;
          gbc.insets = new Insets(5, 5, 5, 5);
          JLabel balanceLabel = new JLabel("your balance");
          RoundPanel balance = new RoundPanel();
          JLabel balanceValue = new JLabel(((Double)psql.UserBalance(owner)).toString() + "$");
          balance.add(balanceValue);
          JButton addMoneyButton = new JButton("add money");
          addMoneyButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    window.OpenPay();
               }
          });

          balanceFrame.add(balanceLabel, gbc);
          gbc.gridy = 1;
          balanceFrame.add(balance, gbc);
          gbc.gridy = 2;
          balanceFrame.add(addMoneyButton, gbc);

          gb.fill = GridBagConstraints.HORIZONTAL;
          gb.gridx = 0; gb.gridy = 2;
          gb.weighty = 0.4; gb.weightx = 2;
          if(user == owner)
          this.add(balanceFrame, gb);

          if(owner == user) {
               cardsFrame = new JPanel();
               cardsFrame.setLayout(new GridBagLayout());
               gbc = new GridBagConstraints();
               gbc.insets = new Insets(5, 5, 5, 5);
               gbc.fill = GridBagConstraints.HORIZONTAL;
               cardsFrame.add(new JLabel("your cards"), gbc);
               Card[] cards = psql.GetUserCards(5, owner);
               ind = 1;
               if (cards == null || cards.length == 0) {
                    gbc.gridy = ind++;
                    cardsFrame.add(new JLabel("you have no cards"), gbc);
               } else {
                    for (int i = 0; i < cards.length; i++) {
                         gbc.gridy = ind++;
                         cardsFrame.add(cards[i].getSimplePanel(), gbc);
                    }
               }

               JButton moreCardsButton = new JButton("more info");
               moreCardsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                         window.OpenCardsList(owner);
                    }
               });
               gbc.gridy = ind++;
               cardsFrame.add(moreCardsButton, gbc);

               gb.fill = GridBagConstraints.HORIZONTAL;
               gb.gridx = 1;
               gb.gridy = 2;
               gb.weighty = 0.4;
               gb.weightx = 3;
               this.add(cardsFrame, gb);
          }

          if(edt)
          {
               transactionsFrame = new JPanel();
               transactionsFrame.setLayout(new GridBagLayout());
               gbc = new GridBagConstraints();
               gbc.fill = GridBagConstraints.HORIZONTAL;
               gbc.insets = new Insets(5, 5, 5, 5);

               ind = 0;
               gbc.gridy = ind++; transactionsFrame.add(new JLabel("transactions"), gbc);
               Transaction[] trs = psql.GetUserTransactions(5, owner);
               if(trs == null || trs.length == 0)
               {
                    gbc.gridy = ind++; transactionsFrame.add(new JLabel("no transactions"), gbc);
               }
               else
               {
                    for(int i = 0; i < trs.length; i++)
                    {
                         gbc.gridy = ind++; transactionsFrame.add(trs[i].getSimplePanel(window), gbc);
                    }
               }
               JButton transactionsButton = new JButton("all transactions");
               transactionsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                         window.TransactionsList(owner);
                    }
               });
               gbc.gridy = ind++; transactionsFrame.add(transactionsButton, gbc);
               gb.fill = GridBagConstraints.HORIZONTAL;
               gb.gridx = 2;
               gb.gridy = 2;
               gb.weighty = 0.4;
               gb.weightx = 3;
               this.add(transactionsFrame, gb);

          }
     }
}
