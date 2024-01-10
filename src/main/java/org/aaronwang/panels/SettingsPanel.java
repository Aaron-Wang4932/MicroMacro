package org.aaronwang.panels;

import org.aaronwang.UI.GradientPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class SettingsPanel extends GradientPanel implements FocusListener {
    public SettingsPanel(ActionListener gui) {
        super(new Color(0x3d137f), new Color(0x342d40), GradientPanel.VERTICAL_FILL);
        this.setPreferredSize(new Dimension(1000, 624));
        this.setLayout(null);

        JLabel title = new JLabel("Settings", JLabel.CENTER);
        title.setForeground(new Color(0xcdc2db)); // Secondary
        title.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 48));
        title.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x4b4358), 8, true), // Secondary container
                "MicroMacro",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD | Font.ITALIC, 26),
                new Color(0xe9def8))); // On secondary container

        JLabel delayLabel = new JLabel("<html><u>Recording delay (ms)</u></html>", JLabel.CENTER);
        delayLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        delayLabel.setBackground(new Color(0x643b46)); // Tertiary container
        delayLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        JTextField recordDelay = new JTextField();
        recordDelay.setFont(new Font("Century Gothic", Font.BOLD, 14));
        recordDelay.setText("CTRL + V");


        JLabel keybindLabel = new JLabel("<html><u>Preferred Recording Keybind</u></html>", JLabel.CENTER);
        keybindLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        keybindLabel.setBackground(new Color(0x643b46)); // Tertiary container
        keybindLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        JLabel themeLabel = new JLabel("<html><u>Preferred Theme</u></html>", JLabel.CENTER);
        themeLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        themeLabel.setBackground(new Color(0x643b46)); // Tertiary container
        themeLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        JLabel hintsLabel = new JLabel("<html><u>Show Startup Hints</u></html>", JLabel.CENTER);
        hintsLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        hintsLabel.setBackground(new Color(0x643b46)); // Tertiary container
        hintsLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));



        this.add(title);
        title.setBounds(370, 20, 260, 110);

        this.add(delayLabel);
        delayLabel.setBounds(110, 189, 212, 50);
        this.add(recordDelay);
        recordDelay.setBounds(790, 189, 100, 25);

        this.add(keybindLabel);
        keybindLabel.setBounds(110, 298, 282, 50);

        this.add(themeLabel);
        themeLabel.setBounds(110, 407, 160, 50);

        this.add(hintsLabel);
        hintsLabel.setBounds(110, 516, 177, 50);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

// focuslistener for keybinds
