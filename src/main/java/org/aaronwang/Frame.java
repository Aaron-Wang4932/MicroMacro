package org.aaronwang;

import org.aaronwang.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Frame extends JFrame implements ActionListener {
    JPanel homePanel = new HomePanel(this);
    JPanel mainPanel = new MainPanel(this);
    JPanel settingsPanel = new SettingsPanel(this);

    public Frame() {
        this.setTitle("MicroMacro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("resources/title.png").getImage());

        this.add(homePanel);
        //this.add(settingsPanel);

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Main Panel buttons:
        if(e.getSource() == homePanel.getComponent(3)) setPanel(homePanel, settingsPanel);
    }

    public void setPanel(JPanel oldPanel, JPanel newPanel) {
        this.remove(oldPanel);
        this.add(newPanel);
        this.pack();
    }
}
