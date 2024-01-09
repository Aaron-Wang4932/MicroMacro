package org.aaronwang.UI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HoverButton extends JButton implements MouseListener {
    private final Color fillColourEnter;
    private final Color fillColorExit;
    private final Color borderHighlight = new Color(0x00B2FF);
    private final Color borderShadow = new Color(0x004A83);
    public HoverButton(String name, Color fillColorEnter, Color fillColorExit) {
        super(name);

        this.fillColourEnter = fillColorEnter;
        this.fillColorExit = fillColorExit;

        this.addMouseListener(this);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setBackground(fillColorEnter);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(fillColourEnter);
            this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, borderHighlight, borderShadow));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == this) {
            this.setBackground(fillColorExit);
            this.setBorder(BorderFactory.createEmptyBorder());
        }
    }
}
