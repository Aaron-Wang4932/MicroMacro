package org.aaronwang.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsPanel extends JPanel implements FocusListener{
    public SettingsPanel(ActionListener gui) {
        JLabel label = new JLabel("placeholder", JLabel.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 48));
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(new Color(0xD9D9D9));
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

// focuslistener for keybinds
