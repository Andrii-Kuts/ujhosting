package com.FS.main;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel
{
    public int roundness;
    public Color color;

    public RoundPanel()
    {
        roundness = 15;
        color = new Color(220, 220, 220);
    }

    public RoundPanel(int rd)
    {
        roundness = rd;
        color = new Color(220, 220, 220);
    }

    public RoundPanel(int rd, Color col)
    {
        roundness = rd;
        color = col;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int width = getWidth(), height = getHeight();

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillRoundRect(0, 0, width, height, roundness, roundness);
    }
}
