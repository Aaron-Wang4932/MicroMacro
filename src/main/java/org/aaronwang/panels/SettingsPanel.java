package org.aaronwang.panels;

import org.aaronwang.UI.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsPanel extends GradientPanel implements FocusListener {
    public SettingsPanel(ActionListener gui) {
        super(new Color(0x3d137f), new Color(0x342d40), GradientPanel.VERTICAL_FILL);
        this.setPreferredSize(new Dimension(1000, 624));
        JLabel label = new JLabel("placeholder", JLabel.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 48));
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
