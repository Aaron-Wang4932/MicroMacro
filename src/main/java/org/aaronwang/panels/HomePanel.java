package org.aaronwang.panels;

import org.aaronwang.UI.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class HomePanel extends GradientPanel {
    public HomePanel(ActionListener gui) {
        super(new Color(0x1c1b1f), new Color(0x543097)); // Base background, on primary
        this.setPreferredSize(new Dimension(1000, 624));
        this.setLayout(null);

        JLabel title = new JLabel("MicroMacro", JLabel.CENTER);
        title.setForeground(new Color(0xd3bbff)); // Primary
        title.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 60));
        title.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x543097), 8, true), // Primary container
                "Aaron's",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24),
                new Color(0xebddff))); // On primary container

        JButton macroRecorder = new JButton("Macro Recorder");
        macroRecorder.setContentAreaFilled(false);
        macroRecorder.setForeground(new Color(0xcdc2db));
        macroRecorder.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        macroRecorder.setFocusable(false);

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
        title.setBounds(284, 20, 432, 132); // 80 px. padding between components
        this.add(macroRecorder);
        macroRecorder.setBounds(300, 232, 400, 64);
        this.add(autoClicker);
        autoClicker.setBounds(300, 356, 400, 64);
        this.add(settings);
        settings.setBounds(300, 480, 400, 64);
    }

}
