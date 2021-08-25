package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window extends Canvas
{

    public JFrame frame;

    private Component comp;

    private int currentUser = -1;
    public String port = "5432", username = "postgres", pass = "12345678";

    public Window(int width, int height, String title)
    {
        frame = new JFrame(title);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       // MainMenu menu = new MainMenu(this, width, height);
       // comp = menu;
        //frame.add(menu);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

       // menu.start();
        PostgresSetting();
    }


    public void PostgresSetting()
    {
        if(comp != null)
            frame.remove(comp);
        currentUser = -1;
        comp = new PostgresWindow(this);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void MainMenu()
    {
        if(comp != null)
            frame.remove(comp);
        currentUser = -1;
        comp = new MainMenuWindow(this);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void HomeWindow(int user)
    {
        if(comp != null)
            frame.remove(comp);
        currentUser = user;
        comp = new HomeWindow(this, user);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenProfile(int user)
    {
            if(comp != null)
                frame.remove(comp);
            comp = new ProfileWindow(this, currentUser, user);
            frame.add(comp);
            frame.pack();
            frame.repaint();
    }

    public void OpenServersList(int user)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new ServersList(this, currentUser, user, -1);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenComputerServersList(int computer)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new ServersList(this, currentUser, -1, computer);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenComputersList(int user)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new ComputersList(this, currentUser, user);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenServer(int server)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new ServerWindow(this, currentUser, server);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenComputer(int computer)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new ComputersWindow(this, currentUser, computer);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenOffer(int offer)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new OfferWindow(this, currentUser, offer);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenOfferBuy(int offer)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new BuyOfferWindow(this, currentUser, offer);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenCardsList(int owner)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new CardsList(this, currentUser, owner);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void EditCard(int card)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new EditCard(this, currentUser, card, false);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void AddCard()
    {
        if(comp != null)
            frame.remove(comp);
        comp = new EditCard(this, currentUser, -1, true);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenPay()
    {
        if(comp != null)
            frame.remove(comp);
        comp = new PayWindow(this, currentUser);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenOffersList()
    {
        if(comp != null)
            frame.remove(comp);
        comp = new OffersList(this, currentUser);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void SellServer(int server)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new SellServerWindow(this, currentUser, server);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void TransactionsList(int owner)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new TransactionsList(this, currentUser, owner);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void EditUser(int owner)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new EditUserWindow(this, currentUser, owner);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void DiscountList()
    {
        if(comp != null)
            frame.remove(comp);
        comp = new DiscountsList(this, currentUser);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }

    public void OpenDiscount(int discount)
    {
        if(comp != null)
            frame.remove(comp);
        comp = new DiscountWindow(this, currentUser, discount);
        frame.add(comp);
        frame.pack();
        frame.repaint();
    }


    public void Pack()
    {
        frame.pack();
    }


    public void Quit()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
