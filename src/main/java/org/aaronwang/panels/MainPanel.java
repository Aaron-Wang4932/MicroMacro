package org.aaronwang.panels;

import org.aaronwang.UI.HoverButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    public HoverButton playBtn;
    public HoverButton recBtn;
    public HoverButton stopRecBtn;
    public HoverButton settingsBtn;
    public MainPanel(ActionListener gui) {
        Dimension defaultBtnDim = new Dimension(115, 115);

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        this.setBackground(new Color(0xD9D9D9));

        playBtn = new HoverButton("Play", new Color(0xe5f1fb), null);
        playBtn.setPreferredSize(defaultBtnDim);
        playBtn.setIcon(new ImageIcon("resources/play.png"));
        playBtn.setIconTextGap(-7);

        recBtn = new HoverButton("Record", new Color(0xe5f1fb), null);
        recBtn.setPreferredSize(defaultBtnDim);
        recBtn.setIcon(new ImageIcon("resources/record.png"));

        stopRecBtn = new HoverButton("Stop", new Color(0xe5f1fb), null);
        stopRecBtn.setPreferredSize(defaultBtnDim);
        stopRecBtn.setIcon(new ImageIcon("resources/stop.png"));

        settingsBtn = new HoverButton("Settings", new Color(0xe5f1fb), null);
        settingsBtn.setPreferredSize(defaultBtnDim);
        settingsBtn.setIcon(new ImageIcon("resources/settings.png"));
        settingsBtn.addActionListener(gui);

        this.add(recBtn);
        this.add(playBtn);
        this.add(stopRecBtn);
        this.add(settingsBtn);
    }
}
