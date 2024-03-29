package org.aaronwang.panels;

import org.aaronwang.UI.GradientPanel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
public class HomePanel extends GradientPanel {
    public HomePanel(ActionListener gui) {
        // All below code is in regard to settings and appearances.
        super(new Color(0x1d1b1e), new Color(0x3d137f), GradientPanel.VERTICAL_FILL); // Base background, on primary
        this.setPreferredSize(new Dimension(700, 500));
        this.setLayout(null);

        JLabel title = new JLabel("MicroMacro", JLabel.CENTER);
        title.setForeground(new Color(0xD8C6FA)); // Primary
        title.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 60));
        title.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xB093E0), 6, true), // Primary container
                "Aaron's",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24),
                new Color(0xebddff))); // On primary container

        JButton macroRecorder = new JButton("Macro Recorder");
        macroRecorder.setContentAreaFilled(false);
        macroRecorder.setForeground(new Color(0xcdc2db)); // Secondary
        macroRecorder.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        macroRecorder.setFocusable(false);
        macroRecorder.addActionListener(gui);

        JButton autoClicker = new JButton("Auto-Clicker");
        autoClicker.setContentAreaFilled(false);
        autoClicker.setForeground(new Color(0xcdc2db));
        autoClicker.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        autoClicker.setFocusable(false);

        JButton settings = new JButton("Settings");
        settings.setContentAreaFilled(false);
        settings.setForeground(new Color(0xcdc2db));
        settings.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        settings.setFocusable(false);
        settings.addActionListener(gui);

        this.add(title);
        title.setBounds(134, 20, 432, 132);
        this.add(macroRecorder);
        macroRecorder.setBounds(150, 232, 400, 64);
        this.add(settings);
        settings.setBounds(150, 356, 400, 64);
    }

}
