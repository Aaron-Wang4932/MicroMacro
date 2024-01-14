package org.aaronwang;

import org.aaronwang.panels.*;
import org.aaronwang.macro.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    JPanel homePanel = new HomePanel(this);
    JPanel macroPanel = new MacroPanel(this);
    JPanel settingsPanel = new SettingsPanel(this);

    public Frame() {
        this.setTitle("MicroMacro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("resources/title.png").getImage());

        this.add(homePanel);

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Main Panel buttons:
        if(e.getSource() == homePanel.getComponent(1)) setPanel(homePanel, macroPanel);
        else if(e.getSource() == homePanel.getComponent(3)) setPanel(homePanel, settingsPanel);
        // Settings Panel buttons:
        else if(e.getSource() == settingsPanel.getComponent(1)) setPanel(settingsPanel, homePanel);
        // Macro Panel buttons:
        else if (e.getSource() == macroPanel.getComponent(5)) setPanel(macroPanel, homePanel);
    }

    public void setPanel(JPanel oldPanel, JPanel newPanel) {
        this.remove(oldPanel);
        this.add(newPanel);
        this.repaint();
        this.pack();
    }
}
